package com.github.blockcertify.exception.business;

import com.github.blockcertify.common.enums.ErrorCode;
import com.github.blockcertify.exception.CertifyException;
import com.github.blockcertify.exception.model.ExceptionSeverity;
import com.github.blockcertify.exception.model.RetryPolicy;

import java.util.Collections;
import java.util.Map;

public class BusinessException extends CertifyException {

    private static final ExceptionSeverity DEFAULT_SEVERITY = ExceptionSeverity.MEDIUM;

    public BusinessException(ErrorCode errorCode) {
        this(errorCode, errorCode.getMessage(), null, Collections.emptyMap());
    }

    public BusinessException(ErrorCode errorCode, Object... params) {
        this(errorCode, ErrorCode.doFormat(errorCode, params), null, Collections.emptyMap());
    }

    public BusinessException(ErrorCode errorCode, Throwable cause) {
        this(errorCode, errorCode.getMessage(), cause, Collections.emptyMap());
    }

    public BusinessException(ErrorCode errorCode, String message, Throwable cause) {
        this(errorCode, message, cause, Collections.emptyMap());
    }

    public BusinessException(ErrorCode errorCode, String message, Throwable cause, Map<String, Object> details) {
        super(errorCode,
                message,
                cause,
                DEFAULT_SEVERITY,
                RetryPolicy.disabled(),
                null,
                details);
    }
}
