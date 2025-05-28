package com.train_ticketing.domain.service.impl;

import com.train_ticketing.domain.model.entity.TicketDetail;
import com.train_ticketing.domain.repository.TicketDetailRepository;
import com.train_ticketing.domain.service.TicketDetailDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketDetailDomainServiceImpl implements TicketDetailDomainService {
    @Autowired
    private TicketDetailRepository ticketDetailRepository;

    @Override
    public TicketDetail getTicketDetailById(Long ticketId) {
        log.info("Implement Domain : {}", ticketId);
        return ticketDetailRepository.findById(ticketId).orElse(null);
    }
}
