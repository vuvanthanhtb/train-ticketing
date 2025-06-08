package com.train_ticketing.infrastructure.persistence.repository;

import com.train_ticketing.domain.repository.TicketOrderRepository;
import com.train_ticketing.infrastructure.persistence.mapper.TicketOrderJPAMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketOrderRepositoryImpl implements TicketOrderRepository {
    @Resource
    private TicketOrderJPAMapper ticketOrderJPAMapper;

    @Override
    public boolean decreaseStockLevel1(Long ticketId, int quantity) {
        log.info("Run test: decreaseStockLevel1 with: | {}, {}", ticketId, quantity);
        return this.ticketOrderJPAMapper.decreaseStockLevel0(ticketId, quantity) > 0;
    }

    @Override
    public boolean decreaseStockLevel2(Long ticketId, int quantity) {
        log.info("Run test: decreaseStockLevel2 with: | {}, {}", ticketId, quantity);
        return this.ticketOrderJPAMapper.decreaseStockLevel1(ticketId, quantity) > 0;
    }

    // CAS: Compare and swap
    @Override
    public boolean decreaseStockLevel3CAS(Long ticketId, int oldStockAvailable, int quantity) {
        log.info("Run test: decreaseStockLevel3CAS with: | {}, {}, {}", ticketId, oldStockAvailable, quantity);
        return this.ticketOrderJPAMapper.decreaseStockLevel3CAS(ticketId, oldStockAvailable, quantity) > 0;
    }

    @Override
    public int getStockAvailable(Long ticketId) {
        return 0;
    }
}
