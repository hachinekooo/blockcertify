package com.github.blockcertify.exception.system;

import com.github.blockcertify.common.enums.ErrorCode;
import com.github.blockcertify.exception.model.ExceptionSeverity;
import com.github.blockcertify.exception.model.RetryPolicy;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

/**
 * 配置异常
 * 当系统配置错误或不完整时抛出此异常
 *
 * @author wangwenpeng
 * @date 2025/10/29
 */
public class ConfigException extends SystemException {

    private static final ExceptionSeverity DEFAULT_SEVERITY = ExceptionSeverity.HIGH;

    /**
     * 直接使用枚举的 Code 和 msg
     *
     * @param errorCode 错误码枚举
     */
    public ConfigException(ErrorCode errorCode) {
        this(errorCode, errorCode.getMessage(), null, Collections.emptyMap());
    }

    /**
     * 使用格式化后的消息
     *
     * @param errorCode 错误码枚举
     * @param params 被填入的消息
     */
    public ConfigException(ErrorCode errorCode, Object... params) {
        this(errorCode, ErrorCode.doFormat(errorCode, params), null, Collections.emptyMap());
    }

    public ConfigException(ErrorCode errorCode, Throwable cause) {
        this(errorCode, errorCode.getMessage(), cause, Collections.emptyMap());
    }

    public ConfigException(ErrorCode errorCode, String message, Throwable cause, Map<String, Object> details) {
        super(errorCode,
                message,
                cause,
                ExceptionSeverity.HIGH, // 使用HIGH严重程度
                RetryPolicy.disabled(), // 配置错误不可重试
                "系统配置异常，请联系管理员",
                details);
    }

    // 添加一些常用的配置异常工厂方法
    public static ConfigException missingConfig(String configKey) {
        return new ConfigException(ErrorCode.INVALID_PARAMETER,
                                 "缺少必要配置项: " + configKey);
    }

    public static ConfigException invalidConfig(String configKey, Object configValue) {
        Map<String, Object> details = Collections.singletonMap("configValue", configValue);
        return new ConfigException(ErrorCode.INVALID_PARAMETER,
                                 "配置项值无效: " + configKey,
                                 null,
                                 details);
    }
}
