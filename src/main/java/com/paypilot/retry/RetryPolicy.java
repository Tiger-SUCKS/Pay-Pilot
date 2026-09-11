package com.paypilot.retry;

public class RetryPolicy {

    private final boolean retryable;
    private final int maxAttempts;
    private final long initialDelayMillis;

    public RetryPolicy(
            boolean retryable,
            int maxAttempts,
            long initialDelayMillis) {

        this.retryable = retryable;
        this.maxAttempts = maxAttempts;
        this.initialDelayMillis = initialDelayMillis;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public long getInitialDelayMillis() {
        return initialDelayMillis;
    }
}