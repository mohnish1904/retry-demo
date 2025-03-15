package com.example.spring_retry_demo.retry_interface;

import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Objects;

@Service
public class RetryService {

    private final RetryTemplate retryTemplate;

    public RetryService(RetryTemplate retryTemplate) {
        this.retryTemplate = retryTemplate;
    }

    public void retryServiceHandler(String type){
        retryTemplate.execute(args1 -> {
            serviceLogic(type);
            return null;
        });
    }

    void serviceLogic(String type) {
        if ("Normal".equalsIgnoreCase(type))
            System.out.println("Normal Flow !!");

        if ("exception".equalsIgnoreCase(type)) {
            System.out.println("++++ In Exception +++++ : " +
                    Objects.requireNonNull(RetrySynchronizationManager.getContext()).getRetryCount() + " : "
            + LocalTime.now(ZoneId.of("GMT+05:30")));
            throw new RuntimeException("Exception Flow !!");
        }
    }
}
