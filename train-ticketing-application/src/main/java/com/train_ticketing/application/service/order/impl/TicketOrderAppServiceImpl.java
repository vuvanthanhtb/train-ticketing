package com.train_ticketing.application.service.order.impl;

import com.train_ticketing.application.service.order.TicketOrderAppService;
import com.train_ticketing.domain.service.TicketOrderDomainService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PessimisticLockException;
import org.hibernate.exception.LockTimeoutException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketOrderAppServiceImpl implements TicketOrderAppService {
    @Resource
    private TicketOrderDomainService ticketOrderDomainService;

    @Override
    public boolean decreaseStockLevel1(Long ticketId, int quantity) {
        try {
            int stockAvailable = ticketOrderDomainService.getStockAvailable(ticketId);
            if (stockAvailable < quantity) {
                log.info("Case: stockAvailable < quantity | {}, {}", stockAvailable, quantity);
                return false;
            }

            return ticketOrderDomainService.decreaseStockLevel1(ticketId, quantity);
        } catch (PessimisticLockException e) {
            log.info("Pessimistic Locking failed for ticketId {}", ticketId, e);
            return false;
        } catch (LockTimeoutException e) {
            log.info("Lock timeout while processing ticketId={}", ticketId, e);
            return false;
        } catch (Exception e) {
            log.info("Unexpected error when decreasing stock for ticketId={}", ticketId, e);
            return false;
        }
    }

    @Override
    public boolean decreaseStockLevel2(Long ticketId, int quantity) {
        try {
            return ticketOrderDomainService.decreaseStockLevel2(ticketId, quantity);
        } catch (PessimisticLockException e) {
            log.info("Pessimistic Locking failed for ticketId {}", ticketId, e);
            return false;
        } catch (LockTimeoutException e) {
            log.info("Lock timeout while processing ticketId={}", ticketId, e);
            return false;
        } catch (Exception e) {
            log.info("Unexpected error when decreasing stock for ticketId={}", ticketId, e);
            return false;
        }
    }

    @Override
    public boolean decreaseStockLevel3CAS(Long ticketId, int quantity) {
        try {
            int stockAvailable = ticketOrderDomainService.getStockAvailable(ticketId);
            return ticketOrderDomainService.decreaseStockLevel3CAS(ticketId, stockAvailable, quantity);
        } catch (PessimisticLockException e) {
            log.info("Pessimistic Locking failed for ticketId {}", ticketId, e);
            return false;
        } catch (LockTimeoutException e) {
            log.info("Lock timeout while processing ticketId={}", ticketId, e);
            return false;
        } catch (Exception e) {
            log.info("Unexpected error when decreasing stock for ticketId={}", ticketId, e);
            return false;
        }
    }
}
