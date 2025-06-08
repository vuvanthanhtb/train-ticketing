package com.train_ticketing.domain.repository;

public interface TicketOrderRepository {
    boolean decreaseStockLevel1(Long ticketId, int quantity);
    boolean decreaseStockLevel2(Long ticketId, int quantity);
    boolean decreaseStockLevel3CAS(Long ticketId, int oldStockAvailable, int quantity);
    int getStockAvailable(Long ticketId);
}
