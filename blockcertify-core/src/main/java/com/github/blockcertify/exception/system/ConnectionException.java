package com.github.blockcertify.exception.system;

import com.github.blockcertify.common.enums.ErrorCode;

public class ConnectionException extends SystemException {
    /**
     * 直接使用枚举的 Code 和 msg
     *
     * @param errorCode 错误码枚举
     */
    public ConnectionException(ErrorCode errorCode) {
        super(errorCode);
    }


    /**
     * 使用格式化后的消息
     *
     * @param errorCode 错误码枚举
     * @param params 被填入的消息
     */
    public ConnectionException(ErrorCode errorCode, Object... params) {
        super(errorCode, params);
    }
}
