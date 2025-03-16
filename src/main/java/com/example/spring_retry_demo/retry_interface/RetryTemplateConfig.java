package com.example.spring_retry_demo.retry_interface;

import org.apache.el.util.ExceptionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.backoff.UniformRandomBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Configuration
public class RetryTemplateConfig {

    @Bean
    public RetryTemplate createRetryTemplate(){

        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(3);  // -> Can use this to update the maxAttempts

        /**
         *  Timeout Retry policy
         */
        TimeoutRetryPolicy timeoutRetryPolicy = new TimeoutRetryPolicy();
        timeoutRetryPolicy.setTimeout(5000);

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
         *  Uniform random backoff policy
         */
        UniformRandomBackOffPolicy backOffPolicy = new UniformRandomBackOffPolicy();
        backOffPolicy.setMinBackOffPeriod(5001);
        backOffPolicy.setMaxBackOffPeriod(10001);

        /**
         * Retry builder can also be used with builder.

            RetryTemplate retryTemplateNew = RetryTemplate.builder()
                    .maxAttempts(4)
                    .withTimeout(Duration.ofSeconds(5))
                    .retryOn(RuntimeException.class)
                    .notRetryOn(List.of(IllegalAccessException.class, IllegalStateException.class))
                    .traversingCauses()
                    .build();
         */

        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(simpleRetryPolicy); // set retry policy here
        retryTemplate.setBackOffPolicy(backOffPolicy); // set backoff policy
        return retryTemplate;
    }
}
