package com.train_ticketing.controller.http;

import com.train_ticketing.application.service.order.TicketOrderAppService;
import com.train_ticketing.domain.service.TicketOrderDomainService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class TicketOrderController {
    @Resource
    private TicketOrderAppService ticketOrderAppService;

    @GetMapping("/{ticketId}/{quantity}/order")
    public boolean orderTicketByLevel(@PathVariable("ticketId") Long ticketId, @PathVariable("quantity") int quantity) {
        log.info("Controller:-> orderTicketByLevel | {}, {}", ticketId, quantity);
        return this.ticketOrderAppService.decreaseStockLevel1(ticketId, quantity);
    }

    @GetMapping("/{ticketId}/{quantity}/cas")
    public boolean orderTicketByLevel3(@PathVariable("ticketId") Long ticketId, @PathVariable("quantity") int quantity) {
        log.info("Controller:-> orderTicketByLevel3 | {}, {}", ticketId, quantity);
        return this.ticketOrderAppService.decreaseStockLevel3CAS(ticketId, quantity);
    }
}
