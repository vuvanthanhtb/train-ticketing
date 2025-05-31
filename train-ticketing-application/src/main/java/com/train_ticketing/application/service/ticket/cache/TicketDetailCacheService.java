package com.train_ticketing.application.service.ticket.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.train_ticketing.domain.model.entity.TicketDetail;
import com.train_ticketing.domain.service.TicketDetailDomainService;
import com.train_ticketing.infrastructure.cache.redis.RedisInfrastructureService;
import com.train_ticketing.infrastructure.distributed.redisson.RedisDistributedLocker;
import com.train_ticketing.infrastructure.distributed.redisson.RedisDistributedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TicketDetailCacheService {
    // use guava
    private final static Cache<Long, TicketDetail> ticketDetailCacheLocal = CacheBuilder.newBuilder()
            .initialCapacity(10)
            .concurrencyLevel(8)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build();
    @Autowired
    private RedisDistributedService redisDistributedService;
    @Autowired // Khai bao cache
    private RedisInfrastructureService redisInfrasService;
    @Autowired
    private TicketDetailDomainService ticketDetailDomainService;

    public TicketDetail getTicketDefaultCacheNormal(Long id, Long version) {
        // 1. get ticket item by redis
        TicketDetail ticketDetail = redisInfrasService.getObject(genEventItemKey(id), TicketDetail.class);
        // 2. YES -> Hit cache
        if (ticketDetail != null) {
            log.info("FROM CACHE {}, {}, {}", id, version, ticketDetail);
            return ticketDetail;
        }
        // 3. If NO --> Missing cache

        // 4. Get data from DBS
        ticketDetail = ticketDetailDomainService.getTicketDetailById(id);
        log.info("FROM DBS {}, {}, {}", id, version, ticketDetail);

        // 5. check ticketitem
        if (ticketDetail != null) { // issue: Gia su ticketItem lay ra tu dbs null thi sao, query mãi
            // 6. set cache
            redisInfrasService.setObject(genEventItemKey(id), ticketDetail);
        }
        return ticketDetail;
    }

    public TicketDetail getTicketDefaultCacheVip(Long id, Long version) {
        TicketDetail ticketDetail = redisInfrasService.getObject(genEventItemKey(id), TicketDetail.class);
        log.info("CACHE {}, {}, {}", id, version, ticketDetail);

        // 2. YES
        if (ticketDetail != null) {
            log.info("FROM CACHE EXIST {}", ticketDetail);
            return ticketDetail;
        }
        log.info("CACHE NO EXIST, START GET DB AND SET CACHE->, {}, {} ", id, version);
        // Tao lock process voi KEY
        RedisDistributedLocker locker = redisDistributedService.getDistributedLock("PRO_LOCK_KEY_ITEM" + id);
        try {
            // 1 - Tao lock
            boolean isLock = locker.tryLock(1, 5, TimeUnit.SECONDS);
            if (!isLock) {
                log.info("LOCK WAIT ITEM PLEASE....{}", version);
                return ticketDetail;
            }
            // stub...
            // Get cache
            ticketDetail = redisInfrasService.getObject(genEventItemKey(id), TicketDetail.class);
            // 2. YES
            if (ticketDetail != null) {
                log.info("FROM CACHE NGON A {}, {}, {}", id, version, ticketDetail);
                return ticketDetail;
            }
            // 3 -> van khong co thi truy van DB

            ticketDetail = ticketDetailDomainService.getTicketDetailById(id);
            log.info("FROM DBS ->>>> {}, {}", ticketDetail, version);
            if (ticketDetail == null) { // Neu trong dbs van khong co thi return ve not exists;
                // set
                redisInfrasService.setObject(genEventItemKey(id), ticketDetail);
                return ticketDetail;
            }

            // neu co thi set redis
            redisInfrasService.setObject(genEventItemKey(id), ticketDetail); // TTL
            // set luon local
            return ticketDetail;

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            locker.unlock();
        }
    }

    private TicketDetail getTicketDetailLocalCache(Long id) {
        try {
            return ticketDetailCacheLocal.getIfPresent(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Cache local
    public TicketDetail getTicketDefaultCacheLocal(Long id, Long version) {
        // 1. Get item local cache
        TicketDetail ticketDetail = getTicketDetailLocalCache(id);

        // 2. YES
        if (ticketDetail != null) {
            log.info("FROM LOCAL CACHE EXIST {}", ticketDetail);
            return ticketDetail;
        }

        // 2. Get distributed cache
        ticketDetail = redisInfrasService.getObject(genEventItemKey(id), TicketDetail.class);
        if (ticketDetail != null) {
            log.info("FROM DISTRIBUTED CACHE EXIST {}", ticketDetail);

            // set item to local cache
            ticketDetailCacheLocal.put(id, ticketDetail);
            return ticketDetail;
        }

        // Create lock process with KEY
        RedisDistributedLocker locker = redisDistributedService.getDistributedLock("PRO_LOCK_KEY_ITEM" + id);
        try {
            // 1 - Create lock
            boolean isLock = locker.tryLock(1, 5, TimeUnit.SECONDS);
            if (!isLock) {
                log.info("LOCK WAIT ITEM PLEASE....{}", version);
                return ticketDetail;
            }
            // stub...
            // Get cache
            ticketDetail = redisInfrasService.getObject(genEventItemKey(id), TicketDetail.class);
            // 2. YES
            if (ticketDetail != null) {
                log.info("FROM CACHE NGON A {}, {}, {}", id, version, ticketDetail);

                // set item to local cache
                ticketDetailCacheLocal.put(id, ticketDetail);

                return ticketDetail;
            }
            // 3 -> van khong co thi truy van DB

            ticketDetail = ticketDetailDomainService.getTicketDetailById(id);
            log.info("FROM DBS ->>>> {}, {}", ticketDetail, version);


            if (ticketDetail == null) { // Neu trong dbs van khong co thi return ve not exists;
                log.info("TICKET NOT EXITS....{}", version);
                ticketDetailCacheLocal.put(id, null);
                redisInfrasService.setObject(genEventItemKey(id), ticketDetail);
                return ticketDetail;
            }

            // neu co thi set redis
            redisInfrasService.setObject(genEventItemKey(id), ticketDetail); // TTL

            // set item to local cache
            ticketDetailCacheLocal.put(id, ticketDetail);
            return ticketDetail;

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            locker.unlock();
        }
    }

    private String genEventItemKey(Long itemId) {
        return "PRO_TICKET:ITEM:" + itemId;
    }
}
