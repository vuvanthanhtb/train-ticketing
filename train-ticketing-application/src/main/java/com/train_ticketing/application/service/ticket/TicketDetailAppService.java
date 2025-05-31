package com.train_ticketing.application.service.ticket;

import com.train_ticketing.application.model.TicketDetailDTO;

public interface TicketDetailAppService {
    TicketDetailDTO getTicketDetailById(Long ticketId, Long version); // should convert to TickDetailDTO by Application Module

    // order ticket
    boolean orderTicketByUser(Long ticketId);
}
