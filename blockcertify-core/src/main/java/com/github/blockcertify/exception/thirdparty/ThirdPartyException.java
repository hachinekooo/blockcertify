package com.github.blockcertify.exception.thirdparty;

import com.github.blockcertify.common.enums.ErrorCode;
import com.github.blockcertify.exception.CertifyException;
import lombok.Getter;

/**
 * 第三方异常
 *
 * @author wangwenpeng
 * @date 2025/10/29
 */
@Getter
public class ThirdPartyException extends CertifyException {
    /**
     * 直接使用枚举的 Code 和 msg
     *
     * @param errorCode 错误码枚举
     */
    public ThirdPartyException(ErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 使用格式化后的消息
     *
     * @param errorCode 错误码枚举
     * @param params 被填入的消息
     */
    public ThirdPartyException(ErrorCode errorCode, Object... params) {
        super(errorCode.getCode(), ErrorCode.doFormat(errorCode, params));
    }

    @Override
    public String toString() {
        return String.format("ThirdPartyException{code=%d, message='%s'}", getCode(), getMessage());
    }
}
