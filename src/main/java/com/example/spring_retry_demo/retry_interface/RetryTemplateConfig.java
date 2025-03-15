package com.example.spring_retry_demo.retry_interface;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.backoff.UniformRandomBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RetryTemplateConfig {

    @Bean
    public RetryTemplate createRetryTemplate(){
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(3);

        /**
          * Set fixed backoff policy of 5000ms
          */
         FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
         fixedBackOffPolicy.setBackOffPeriod(5000);

        /**
         *  Exponential backoff policy
         */
        ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
        exponentialBackOffPolicy.setInitialInterval(3000);
        exponentialBackOffPolicy.setMultiplier(1.5);
        exponentialBackOffPolicy.setMaxInterval(10000);

        /**
         *  UniformRandomBackOffPolicy
         */
        UniformRandomBackOffPolicy backOffPolicy = new UniformRandomBackOffPolicy();
        backOffPolicy.setMinBackOffPeriod(5001);
        backOffPolicy.setMaxBackOffPeriod(10001);

        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(simpleRetryPolicy); // set retry policy here
        retryTemplate.setBackOffPolicy(backOffPolicy); // set backoff policy
        return retryTemplate;
    }
}
