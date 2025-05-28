package com.train_ticketing.application.service.ticket;

import com.train_ticketing.domain.model.entity.TicketDetail;

public interface TicketDetailAppService {
    TicketDetail getTicketDetailById(Long ticketId);
}
