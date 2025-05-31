package com.train_ticketing.application.service.ticket.impl;

import com.train_ticketing.application.mapper.TicketDetailMapper;
import com.train_ticketing.application.model.TicketDetailDTO;
import com.train_ticketing.application.model.cache.TicketDetailCache;
import com.train_ticketing.application.service.ticket.TicketDetailAppService;
import com.train_ticketing.application.service.ticket.cache.TicketDetailCacheService;
import com.train_ticketing.application.service.ticket.cache.TicketDetailCacheServiceRefactor;
import com.train_ticketing.domain.service.TicketDetailDomainService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketDetailAppServiceImpl implements TicketDetailAppService {

    @Resource
    private TicketDetailDomainService ticketDetailDomainService;

    @Resource
    private TicketDetailCacheService ticketDetailCacheService;

    @Resource
    private TicketDetailCacheServiceRefactor ticketDetailCacheServiceRefactor;

    @Override
    public TicketDetailDTO getTicketDetailById(Long ticketId, Long version) {
        log.info("Implement Application : {}, {}: ", ticketId, version);
        TicketDetailCache ticketDetailCache = ticketDetailCacheServiceRefactor.getTicketDetail(ticketId, version);
        TicketDetailDTO ticketDetailDTO = TicketDetailMapper.mapperToTicketDetailDTO(ticketDetailCache.getTicketDetail());
        ticketDetailDTO.setVersion(ticketDetailCache.getVersion());
        return ticketDetailDTO;
    }

    @Override
    public boolean orderTicketByUser(Long ticketId) {
        return ticketDetailCacheServiceRefactor.orderTicketByUser(ticketId);
    }
}
