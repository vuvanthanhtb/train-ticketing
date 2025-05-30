package com.train_ticketing.controller.http;

import com.train_ticketing.application.service.event.EventAppService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HiController {

    private static final SecureRandom secureRandom = new SecureRandom();
    private final EventAppService eventAppService;
    private final RestTemplate restTemplate;

    @GetMapping("hi")
    @RateLimiter(name = "backendA", fallbackMethod = "fallbackHi")
    public String hi() {
        return this.eventAppService.sayHi("Application");
    }

    public String fallbackHi(Throwable throwable) {
        return "Too many requests";
    }

    @GetMapping("hello")
    @RateLimiter(name = "backendB", fallbackMethod = "fallbackHi")
    public String hello() {
        return this.eventAppService.sayHi("Application");
    }

    @GetMapping("circuitbreaker")
    @CircuitBreaker(name = "checkRandom", fallbackMethod = "fallbackCircuitbreaker")
    public String circuitbreaker() {
        int productId = secureRandom.nextInt(20) + 1;
        String url = "https://fakestoreapi.com/products/" + productId;
        return this.restTemplate.getForObject(url, String.class);
    }

    public String fallbackCircuitbreaker(Throwable throwable) {
        return "fakestoreapi failed";
    }
}
