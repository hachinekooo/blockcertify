package com.github.blockcertify.exception.business;

import com.github.blockcertify.common.enums.ErrorCode;

/**
 * 数据异常
 *
 * @author wangwenpeng
 * @date 2025/10/29
 */
public class DataException extends BusinessException {
    /**
     * 直接使用枚举的 Code 和 msg
     *
     * @param errorCode 错误码枚举
     */
    public DataException(ErrorCode errorCode) {
        super(errorCode);
    }


    /**
     * 使用格式化后的消息
     *
     * @param errorCode 错误码枚举
     * @param params 被填入的消息
     */
    public DataException(ErrorCode errorCode, Object... params) {
        super(errorCode, params);
    }
}
