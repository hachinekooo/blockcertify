package com.github.blockcertify.exception;


import lombok.Getter;

/**
 * 异常根基类
 *
 * @author wangwenpeng
 * @date 2025/10/29
 */
@Getter
public class CertifyException extends RuntimeException {
    private final Integer code;

    public CertifyException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public CertifyException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    @Override
    public String toString() {
        return String.format("CertifyException{code=%d, message='%s'}", code, getMessage());
    }
}

