package com.github.blockcertify.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.blockcertify.common.enums.ErrorCategory;
import com.github.blockcertify.exception.model.ExceptionSeverity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 统一错误响应结构
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    
    /**
     * 错误码
     */
    private Integer errorCode;
    
    /**
     * 技术错误消息（用于日志/开发）
     */
    private String errorMessage;
    
    /**
     * 用户友好消息
     */
    private String userMessage;
    
    /**
     * 链路追踪ID
     */
    private String traceId;
    
    /**
     * 时间戳
     */
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    
    /**
     * 请求路径
     */
    private String path;
    
    /**
     * HTTP方法
     */
    private String method;
    
    /**
     * 是否可重试
     */
    private Boolean retryable;
    
    /**
     * 重试间隔（秒）
     */
    private Integer retryAfter;
    
    /**
     * 最大重试次数
     */
    private Integer maxRetryAttempts;
    
    /**
     * 错误分类
     */
    private ErrorCategory category;
    
    /**
     * 严重程度
     */
    private ExceptionSeverity severity;
    
    /**
     * 附加详情
     */
    private Map<String, Object> details;
    
    /**
     * 调试信息（仅开发环境）
     */
    private DebugInfo debugInfo;
}
