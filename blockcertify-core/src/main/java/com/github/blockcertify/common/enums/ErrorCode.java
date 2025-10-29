package com.github.blockcertify.common.enums;

import com.google.common.annotations.VisibleForTesting;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 错误码枚举
 *
 * @author wangwenpeng
 * @date 2025/10/29
 */
@Getter
@Slf4j
public enum ErrorCode {
    // 业务错误码 40xxxx)
    TX_HASH_EXISTS(400001, "交易哈希已存在: {}"),
    CERTIFY_NOT_FOUND(400002, "认证记录不存在: {}"),
    
    // 系统错误码 (50xxxx)
    SYSTEM_ERROR(500001, "系统异常"),
    DB_OPERATION_ERROR(500002, "数据库操作失败");

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @VisibleForTesting
    public static String doFormat(ErrorCode errorCode, Object... params) {
        try {
            // 将 {} 转换为 %s 格式
            String formatPattern = errorCode.getMessage().replace("{}", "%s");
            return String.format(formatPattern, params);
        } catch (Exception e) {
            log.error("[格式化失败：错误码({})|错误内容({})|参数({})", errorCode.getCode(), errorCode.getMessage(), params);
            return errorCode.getMessage(); // 降级返回原始模板
        }
    }

}