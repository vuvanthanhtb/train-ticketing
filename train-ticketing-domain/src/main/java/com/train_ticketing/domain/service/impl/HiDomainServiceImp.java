package com.train_ticketing.domain.service.impl;

import com.train_ticketing.domain.repository.HiDomainRepository;
import com.train_ticketing.domain.service.HiDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HiDomainServiceImp implements HiDomainService {
    @Autowired
    private HiDomainRepository hiDomainRepository;

    @Override
    public String sayHi(String who) {
        return this.hiDomainRepository.sayHi(who);
    }
}
