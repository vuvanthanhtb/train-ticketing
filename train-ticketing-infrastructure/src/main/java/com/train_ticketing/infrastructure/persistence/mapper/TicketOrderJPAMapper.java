package com.train_ticketing.infrastructure.persistence.mapper;

import com.train_ticketing.domain.model.entity.TicketDetail;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TicketOrderJPAMapper extends JpaRepository<TicketDetail, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE TicketDetail t SET t.updatedAt = CURRENT_TIMESTAMP, t.stockAvailable = t.stockAvailable - :quantity WHERE t.id = :ticketId")
    int decreaseStockLevel0(@Param("ticketId") Long ticketId, @Param("quantity") int quantity);

    @Modifying
    @Transactional
    @Query("UPDATE TicketDetail t SET t.updatedAt = CURRENT_TIMESTAMP, t.stockAvailable = t.stockAvailable - :quantity WHERE t.id = :ticketId AND t.stockAvailable >= :quantity")
    int decreaseStockLevel1(@Param("ticketId") Long ticketId, @Param("quantity") int quantity);

    @Modifying
    @Transactional
    @Query("UPDATE TicketDetail t SET t.updatedAt = CURRENT_TIMESTAMP, t.stockAvailable = :oldStockAvailable - :quantity WHERE t.id = :ticketId AND t.stockAvailable = :oldStockAvailable")
    int decreaseStockLevel3CAS(@Param("ticketId") Long ticketId, @Param("oldStockAvailable") int oldStockAvailable, @Param("quantity") int quantity);
}
