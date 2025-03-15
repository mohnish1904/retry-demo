package com.example.spring_retry_demo.retryable;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RetryableDemo {

    @Retryable(
            retryFor = RuntimeException.class,
            maxAttempts = 4,
            backoff = @Backoff(delay = 1000, multiplier = 1.5, maxDelay = 10000))
    public void methodToRetry(String s) {

        if ("NormalFlow".equalsIgnoreCase(s))
            System.out.println("Normal Flow !!");

        if ("exceptionFlow".equalsIgnoreCase(s)) {
            System.out.println("++++ In Exception +++++ : " +
                    Objects.requireNonNull(RetrySynchronizationManager.getContext()).getRetryCount());
            throw new RuntimeException("Exception Flow !!");
        }
    }

    @Recover
    public void recoverMethod(){
        System.out.println("Recovery Flow !!");
    }
}
