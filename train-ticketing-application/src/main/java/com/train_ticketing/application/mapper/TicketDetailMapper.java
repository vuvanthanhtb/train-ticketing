package com.train_ticketing.application.mapper;

import com.train_ticketing.application.model.TicketDetailDTO;
import com.train_ticketing.domain.model.entity.TicketDetail;
import org.springframework.beans.BeanUtils;

public class TicketDetailMapper {
    public static TicketDetailDTO mapperToTicketDetailDTO(TicketDetail ticketDetail) {
        if(ticketDetail == null) return null;

        TicketDetailDTO ticketDetailDTO = new TicketDetailDTO();
        BeanUtils.copyProperties(ticketDetail, ticketDetailDTO);

        return ticketDetailDTO;
    }
}
