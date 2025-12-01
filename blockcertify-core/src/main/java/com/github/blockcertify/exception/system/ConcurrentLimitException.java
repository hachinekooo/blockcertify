package com.github.blockcertify.exception.system;

import com.github.blockcertify.common.enums.ErrorCode;
import com.github.blockcertify.exception.CertifyException;
import com.github.blockcertify.exception.model.ExceptionSeverity;
import com.github.blockcertify.exception.model.RetryPolicy;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

/**
 * 并发限制异常
 * 当系统达到并发处理上限时抛出此异常
 */
public class ConcurrentLimitException extends CertifyException {

    private static final ExceptionSeverity DEFAULT_SEVERITY = ExceptionSeverity.MEDIUM;
    private static final RetryPolicy DEFAULT_RETRY = RetryPolicy.of(2, Duration.ofSeconds(5));

    public ConcurrentLimitException(ErrorCode errorCode) {
        this(errorCode, errorCode.getMessage(), null, Collections.emptyMap());
    }

    public ConcurrentLimitException(ErrorCode errorCode, Object... params) {
        this(errorCode, ErrorCode.doFormat(errorCode, params), null, Collections.emptyMap());
    }

    public ConcurrentLimitException(ErrorCode errorCode, Throwable cause) {
        this(errorCode, errorCode.getMessage(), cause, Collections.emptyMap());
    }

    public ConcurrentLimitException(ErrorCode errorCode, String message, Throwable cause) {
        this(errorCode, message, cause, Collections.emptyMap());
    }

    public ConcurrentLimitException(ErrorCode errorCode, String message, Throwable cause, Map<String, Object> details) {
        super(errorCode,
                message,
                cause,
                DEFAULT_SEVERITY,
                DEFAULT_RETRY,
                "系统当前处理任务过多，请稍后重试",
                details);
    }
}