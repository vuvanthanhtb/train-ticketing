package com.train_ticketing.application.service.event.impl;

import com.train_ticketing.application.service.event.EventAppService;
import com.train_ticketing.domain.service.HiDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventAppServiceImpl implements EventAppService {

    @Autowired
    private HiDomainService hiDomainService;

    @Override
    public String sayHi(String who) {
        return this.hiDomainService.sayHi(who);
    }
}
