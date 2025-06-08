package com.train_ticketing.domain.service.impl;

import com.train_ticketing.domain.repository.TicketOrderRepository;
import com.train_ticketing.domain.service.TicketOrderDomainService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketOrderDomainServiceImpl implements TicketOrderDomainService {
    @Resource
    private TicketOrderRepository ticketOrderRepository;

    @Override
    public boolean decreaseStockLevel1(Long ticketId, int quantity) {
        return this.ticketOrderRepository.decreaseStockLevel1(ticketId, quantity);
    }

    @Override
    public boolean decreaseStockLevel2(Long ticketId, int quantity) {
        return this.ticketOrderRepository.decreaseStockLevel2(ticketId, quantity);
    }

    @Override
    public boolean decreaseStockLevel3CAS(Long ticketId, int oldStockAvailable, int quantity) {
        return this.ticketOrderRepository.decreaseStockLevel3CAS(ticketId, oldStockAvailable, quantity);
    }

    @Override
    public int getStockAvailable(Long ticketId) {
        return this.ticketOrderRepository.getStockAvailable(ticketId);
    }
}
