package com.github.blockcertify.exception.business;

import com.github.blockcertify.common.enums.ErrorCode;

public class StatusException extends BusinessException {
    /**
     * 直接使用枚举的 Code 和 msg
     *
     * @param errorCode 错误码枚举
     */
    public StatusException(ErrorCode errorCode) {
        super(errorCode);
    }


    /**
     * 使用格式化后的消息
     *
     * @param errorCode 错误码枚举
     * @param params 被填入的消息
     */
    public StatusException(ErrorCode errorCode, Object... params) {
        super(errorCode, params);
    }

}
