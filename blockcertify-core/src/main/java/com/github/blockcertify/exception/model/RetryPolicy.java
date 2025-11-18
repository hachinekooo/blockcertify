package com.github.blockcertify.exception.model;

import java.time.Duration;
import java.util.Objects;

public final class RetryPolicy {

    private static final RetryPolicy DISABLED = new RetryPolicy(false, 0, Duration.ZERO);

    private final boolean retryable;
    private final int maxAttempts;
    private final Duration backoff;

    private RetryPolicy(boolean retryable, int maxAttempts, Duration backoff) {
        this.retryable = retryable;
        this.maxAttempts = maxAttempts;
        this.backoff = backoff == null ? Duration.ZERO : backoff;
    }

    public static RetryPolicy disabled() {
        return DISABLED;
    }

    public static RetryPolicy of(int maxAttempts, Duration backoff) {
        if (maxAttempts <= 0) {
            throw new IllegalArgumentException("maxAttempts must be positive when retryable");
        }
        Objects.requireNonNull(backoff, "backoff");
        return new RetryPolicy(true, maxAttempts, backoff);
    }

    public boolean retryable() {
        return retryable;
    }

    public int maxAttempts() {
        return maxAttempts;
    }

    public Duration backoff() {
        return backoff;
    }
}
