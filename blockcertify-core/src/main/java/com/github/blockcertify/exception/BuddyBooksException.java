package com.github.blockcertify.exception;

import com.github.blockcertify.common.enums.ErrorCode;
import com.github.blockcertify.exception.model.ExceptionSeverity;
import com.github.blockcertify.exception.model.RetryPolicy;

import java.util.Collections;
import java.util.Map;

/**
 * Standardised contract for all domain exceptions.
 */
public interface BuddyBooksException {

    ErrorCode getErrorCode();

    ExceptionSeverity getSeverity();

    RetryPolicy getRetryPolicy();

    /**
     * Optional user facing message override.
     */
    default String getUserMessage() {
        return null;
    }

    /**
     * Additional diagnostic information for error response/logging.
     */
    default Map<String, Object> getDetails() {
        return Collections.emptyMap();
    }

    default boolean isRetryable() {
        return getRetryPolicy() != null && getRetryPolicy().retryable();
    }
}
