package com.train_ticketing.domain.repository;

import com.train_ticketing.domain.model.entity.TicketDetail;

import java.util.Optional;

public interface TicketDetailRepository {
    Optional<TicketDetail> findById(Long id);
}
