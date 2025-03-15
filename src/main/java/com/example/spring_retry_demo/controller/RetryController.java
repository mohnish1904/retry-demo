package com.example.spring_retry_demo.controller;

import com.example.spring_retry_demo.retry_interface.RetryService;
import com.example.spring_retry_demo.retryable.RetryableDemo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetryController {

    private final RetryableDemo retryableDemo;
    private final RetryService retryService;

    public RetryController(RetryableDemo retryableDemo,
                           RetryService retryService) {
        this.retryableDemo = retryableDemo;
        this.retryService = retryService;
    }

    @GetMapping("/retryable/{type}")
    public void callRetryable(@PathVariable String type){
        retryableDemo.methodToRetry(type);
    }

    @GetMapping("/retrytemplate/{type}")
    public void callRetryTemplate(@PathVariable String type){
        retryService.retryServiceHandler(type);
    }
}
