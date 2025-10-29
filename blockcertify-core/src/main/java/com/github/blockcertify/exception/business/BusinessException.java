package com.github.blockcertify.exception.business;

import com.github.blockcertify.common.enums.ErrorCode;
import com.github.blockcertify.exception.CertifyException;

/**
 * 业务异常
 *
 * @author wangwenpeng
 * @date 2025/10/29
 */
public class BusinessException extends CertifyException {


    /**
     * 直接使用枚举的 Code 和 msg
     *
     * @param errorCode 错误码枚举
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 使用格式化后的消息
     *
     * @param errorCode 错误码枚举
     * @param params 被填入的消息
     */
    public BusinessException(ErrorCode errorCode, Object... params) {
        super(errorCode.getCode(), ErrorCode.doFormat(errorCode, params));
    }

     @Override
    public String toString() {
        return String.format("BusinessException{code=%d, message='%s'}", getCode(), getMessage());
    }

}
