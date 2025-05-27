package com.train_ticketing.infrastructure.persistence.repository;

import com.train_ticketing.domain.repository.HiDomainRepository;
import org.springframework.stereotype.Service;

@Service
public class HiInfrastructureRepositoryImp implements HiDomainRepository {
    @Override
    public String sayHi(String who) {
        return "Hi Infrastructure";
    }
}
