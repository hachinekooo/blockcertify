package com.github.blockcertify.common.enums;

import com.github.blockcertify.exception.model.ExceptionSeverity;
import com.google.common.annotations.VisibleForTesting;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 错误码枚举 - 企业级异常体系
 *
 * @author wangwenpeng
 * @date 2025/10/29
 */
@Getter
@Slf4j
public enum ErrorCode {
    // ========== 通用错误 (1000-1999) ==========
    SUCCESS(1000, 200, "Success", ErrorCategory.GENERAL, ExceptionSeverity.LOW, false),
    SYSTEM_ERROR(1001, 500, "系统错误", ErrorCategory.GENERAL, ExceptionSeverity.HIGH, true),
    INVALID_PARAMETER(1002, 400, "参数错误", ErrorCategory.GENERAL, ExceptionSeverity.MEDIUM, false),
    
    // ========== 认证授权 (2000-2999) ==========
    AUTHENTICATION_FAILED(2001, 401, "认证失败", ErrorCategory.AUTH, ExceptionSeverity.MEDIUM, false),
    AUTHORIZATION_FAILED(2002, 403, "授权失败", ErrorCategory.AUTH, ExceptionSeverity.MEDIUM, false),
    TOKEN_EXPIRED(2003, 401, "令牌过期", ErrorCategory.AUTH, ExceptionSeverity.LOW, false),
    
    // ========== 数据库相关 (3000-3999) ==========
    DATABASE_ERROR(3001, 500, "数据库错误", ErrorCategory.DATABASE, ExceptionSeverity.HIGH, true),
    DATA_NOT_FOUND(3002, 404, "数据未找到", ErrorCategory.DATABASE, ExceptionSeverity.LOW, false),
    DUPLICATE_KEY_ERROR(3008, 409, "数据重复", ErrorCategory.DATABASE, ExceptionSeverity.MEDIUM, false),
    
    // ========== 业务逻辑 (4000-4999) ==========
    BUSINESS_LOGIC_ERROR(4001, 400, "业务逻辑错误", ErrorCategory.BUSINESS, ExceptionSeverity.MEDIUM, false),
    VALIDATION_FAILED(4002, 400, "验证失败", ErrorCategory.BUSINESS, ExceptionSeverity.MEDIUM, false),
    TX_HASH_EXISTS(4003, 409, "交易哈希已存在: {}", ErrorCategory.BUSINESS, ExceptionSeverity.MEDIUM, false),
    CERTIFY_NOT_FOUND(4004, 404, "认证记录不存在: {}", ErrorCategory.BUSINESS, ExceptionSeverity.LOW, false),
    
    // ========== 外部服务 (5000-5999) ==========
    EXTERNAL_SERVICE_ERROR(5001, 502, "外部服务错误", ErrorCategory.EXTERNAL, ExceptionSeverity.HIGH, true),
    API_CALL_FAILED(5002, 502, "API调用失败", ErrorCategory.EXTERNAL, ExceptionSeverity.HIGH, true),
    
    // ========== 区块链相关 (6000-6999) ==========
    BLOCKCHAIN_ERROR(6001, 500, "区块链错误", ErrorCategory.BLOCKCHAIN, ExceptionSeverity.HIGH, true),
    BLOCKCHAIN_TIMEOUT(6002, 504, "区块链超时", ErrorCategory.BLOCKCHAIN, ExceptionSeverity.HIGH, true),
    BLOCKCHAIN_CONNECTION_ERROR(6003, 503, "区块链连接失败", ErrorCategory.BLOCKCHAIN, ExceptionSeverity.HIGH, true),
    
    // ========== 限流相关 (8000-8999) ==========
    RATE_LIMIT_EXCEEDED(8001, 429, "请求过于频繁", ErrorCategory.RATE_LIMIT, ExceptionSeverity.MEDIUM, false),
    CONCURRENT_LIMIT_EXCEEDED(8002, 429, "并发限制", ErrorCategory.RATE_LIMIT, ExceptionSeverity.MEDIUM, false),
    
    // ========== 系统错误 (50xxxx - 兼容旧码) ==========
    DB_OPERATION_ERROR(500002, 500, "数据库操作失败", ErrorCategory.DATABASE, ExceptionSeverity.HIGH, true);

    private final Integer code;
    private final int httpStatus;
    private final String message;
    private final ErrorCategory category;
    private final ExceptionSeverity severity;
    private final boolean retryable;

    ErrorCode(Integer code, int httpStatus, String message, ErrorCategory category, 
              ExceptionSeverity severity, boolean retryable) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
        this.category = category;
        this.severity = severity;
        this.retryable = retryable;
    }
    
    public boolean isBusinessError() {
        return category == ErrorCategory.BUSINESS;
    }
    
    public boolean isSystemError() {
        return category == ErrorCategory.SYSTEM || category == ErrorCategory.DATABASE;
    }

    @VisibleForTesting
    public static String doFormat(ErrorCode errorCode, Object... params) {
        try {
            // 将 {} 转换为 %s 格式
            String formatPattern = errorCode.getMessage().replace("{}", "%s");
            return String.format(formatPattern, params);
        } catch (Exception e) {
            log.error("[格式化失败：错误码({})|错误内容({})|参数({})]", errorCode.getCode(), errorCode.getMessage(), params);
            return errorCode.getMessage(); // 降级返回原始模板
        }
    }

}