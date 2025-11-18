package com.github.blockcertify.exception;

import com.github.blockcertify.common.enums.ErrorCode;
import com.github.blockcertify.exception.model.ExceptionSeverity;
import com.github.blockcertify.exception.model.RetryPolicy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Base class for all platform exceptions carrying structured metadata.
 */
public abstract class CertifyException extends RuntimeException implements BuddyBooksException {

    private final ErrorCode errorCode;
    private final ExceptionSeverity severity;
    private final RetryPolicy retryPolicy;
    private final String userMessage;
    private final Map<String, Object> details;

    protected CertifyException(ErrorCode errorCode,
                               String message,
                               ExceptionSeverity severity,
                               RetryPolicy retryPolicy) {
        this(errorCode, message, null, severity, retryPolicy, null, Collections.emptyMap());
    }

    protected CertifyException(ErrorCode errorCode,
                               String message,
                               Throwable cause,
                               ExceptionSeverity severity,
                               RetryPolicy retryPolicy,
                               String userMessage,
                               Map<String, Object> details) {
        super(message, cause);
        this.errorCode = Objects.requireNonNull(errorCode, "errorCode");
        this.severity = Objects.requireNonNull(severity, "severity");
        this.retryPolicy = retryPolicy == null ? RetryPolicy.disabled() : retryPolicy;
        this.userMessage = userMessage;
        if (details == null || details.isEmpty()) {
            this.details = Collections.emptyMap();
        } else {
            this.details = Collections.unmodifiableMap(new HashMap<>(details));
        }
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public ExceptionSeverity getSeverity() {
        return severity;
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    @Override
    public String getUserMessage() {
        return userMessage;
    }

    @Override
    public Map<String, Object> getDetails() {
        return details;
    }

    public int getCode() {
        return errorCode.getCode();
    }

    @Override
    public String toString() {
        return String.format(
                "CertifyException{code=%s, severity=%s, retryable=%s, message='%s'}",
                errorCode.getCode(),
                severity,
                retryPolicy.retryable(),
                getMessage()
        );
    }
}

