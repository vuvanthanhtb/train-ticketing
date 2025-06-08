package com.train_ticketing.domain.service;

public interface TicketOrderDomainService {
    boolean decreaseStockLevel1(Long ticketId, int quantity);
    boolean decreaseStockLevel2(Long ticketId, int quantity);
    boolean decreaseStockLevel3CAS(Long ticketId, int oldStockAvailable, int quantity);
    int getStockAvailable(Long ticketId);
}
