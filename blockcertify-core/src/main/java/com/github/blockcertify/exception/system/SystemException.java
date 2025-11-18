package com.github.blockcertify.exception.system;

import com.github.blockcertify.common.enums.ErrorCode;
import com.github.blockcertify.exception.CertifyException;
import com.github.blockcertify.exception.model.ExceptionSeverity;
import com.github.blockcertify.exception.model.RetryPolicy;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

public class SystemException extends CertifyException {

    private static final ExceptionSeverity DEFAULT_SEVERITY = ExceptionSeverity.HIGH;
    private static final RetryPolicy DEFAULT_RETRY = RetryPolicy.of(3, Duration.ofSeconds(60));

    public SystemException(ErrorCode errorCode) {
        this(errorCode, errorCode.getMessage(), null, Collections.emptyMap());
    }

    public SystemException(ErrorCode errorCode, Object... params) {
        this(errorCode, ErrorCode.doFormat(errorCode, params), null, Collections.emptyMap());
    }

    public SystemException(ErrorCode errorCode, Throwable cause) {
        this(errorCode, errorCode.getMessage(), cause, Collections.emptyMap());
    }

    public SystemException(ErrorCode errorCode, String message, Throwable cause) {
        this(errorCode, message, cause, Collections.emptyMap());
    }

    public SystemException(ErrorCode errorCode, String message, Throwable cause, Map<String, Object> details) {
        super(errorCode,
                message,
                cause,
                DEFAULT_SEVERITY,
                DEFAULT_RETRY,
                null,
                details);
    }
}
