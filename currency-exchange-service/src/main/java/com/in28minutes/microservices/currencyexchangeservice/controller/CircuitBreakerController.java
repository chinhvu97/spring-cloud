package com.in28minutes.microservices.currencyexchangeservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
//    to configure max attempt in application, default is 3; fallback method when all attempts failed
//    @Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
    @CircuitBreaker(name = "sample-api", fallbackMethod = "hardcodedResponse")
    //10s => allow 10000 calls to the sample api
//    @RateLimiter(name = "default")
//    @Bulkhead(name = "default")
    public String sampleApi() {
        logger.info("Sample API call received...");
        ResponseEntity<String> entity = new RestTemplate().getForEntity("http://localhost.9999/some-dummy-url",
                String.class);

        return entity.getBody();
//        return "sample api";
    }

   public String hardcodedResponse(Exception e) {
        return "fallback-response";
   }
}
