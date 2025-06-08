package com.train_ticketing.application.service.order;

public interface TicketOrderAppService {
    boolean decreaseStockLevel1(Long ticketId, int quantity);

    boolean decreaseStockLevel2(Long ticketId, int quantity);

    boolean decreaseStockLevel3CAS(Long ticketId, int quantity);
//    int getStockAvailable(Long ticketId);
}
