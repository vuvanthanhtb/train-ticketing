package com.train_ticketing.domain.service;

import com.train_ticketing.domain.model.entity.TicketDetail;

public interface TicketDetailDomainService {
    TicketDetail getTicketDetailById(Long ticketId);
}
