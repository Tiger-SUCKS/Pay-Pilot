package com.paypilot.retry;

import com.paypilot.enums.FailureCategory;
import org.springframework.stereotype.Component;

@Component
public class RetryPolicyEngine {

    public RetryPolicy determinePolicy(FailureCategory category) {

        if (category == null) {
            return new RetryPolicy(false, 0, 0);
        }

        switch (category) {

            case TIMEOUT:
                return new RetryPolicy(
                        true,
                        3,
                        1000
                );

            case NETWORK:
                return new RetryPolicy(
                        true,
                        3,
                        2000
                );

            case SYSTEM_ERROR:
                return new RetryPolicy(
                        true,
                        2,
                        5000
                );

            case CUSTOMER_ERROR:
            case PAYMENT_METHOD_ERROR:
            case UNKNOWN:
            default:
                return new RetryPolicy(
                        false,
                        0,
                        0
                );

        }

    }
    public long calculateDelay(
            RetryPolicy policy,
            int retryAttempt) {

        if (!policy.isRetryable()) {
            return 0;
        }

        if (retryAttempt <= 0) {
            return policy.getInitialDelayMillis();
        }

        return policy.getInitialDelayMillis()
                * (long) Math.pow(2, retryAttempt);
    }
}