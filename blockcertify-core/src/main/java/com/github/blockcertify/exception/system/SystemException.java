package com.github.blockcertify.exception.system;

import com.github.blockcertify.common.enums.ErrorCode;
import com.github.blockcertify.exception.CertifyException;
import lombok.Getter;

/**
 * 系统异常
 *
 * @author wangwenpeng
 * @date 2025/10/29
 */
@Getter
public class SystemException extends CertifyException {

    /**
     * 直接使用枚举的 Code 和 msg
     *
     * @param errorCode 错误码枚举
     */
    public SystemException(ErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 使用格式化后的消息
     *
     * @param errorCode 错误码枚举
     * @param params 被填入的消息
     */
    public SystemException(ErrorCode errorCode, Object... params) {
        super(errorCode.getCode(), ErrorCode.doFormat(errorCode, params));
    }

    @Override
    public String toString() {
        return String.format("SystemException{code=%d, message='%s'}", getCode(), getMessage());
    }
}
