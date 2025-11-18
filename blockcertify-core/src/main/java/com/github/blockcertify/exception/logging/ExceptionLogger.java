package com.github.blockcertify.exception.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.blockcertify.exception.CertifyException;
import com.github.blockcertify.exception.model.ExceptionSeverity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 结构化异常日志记录器
 */
@Slf4j
@Component
public class ExceptionLogger {
    
    @Resource
    private ObjectMapper objectMapper;
    
    /**
     * 记录异常（结构化日志）
     */
    public void logException(CertifyException exception, Map<String, Object> context) {
        ExceptionSeverity severity = exception.getSeverity();
        
        Map<String, Object> logData = new HashMap<>();
        logData.put("errorCode", exception.getErrorCode().getCode());
        logData.put("severity", severity);
        logData.put("category", exception.getErrorCode().getCategory());
        logData.put("message", exception.getMessage());
        logData.put("retryable", exception.isRetryable());
        
        if (context != null) {
            logData.putAll(sanitizeContext(context));
        }
        
        String logMessage = formatLogMessage(logData);
        
        // 根据严重程度选择日志级别
        switch (severity) {
            case CRITICAL:
            case HIGH:
                log.error(logMessage, exception);
                break;
            case MEDIUM:
                log.warn(logMessage);
                break;
            case LOW:
            default:
                log.info(logMessage);
                break;
        }
    }
    
    /**
     * 记录通用异常
     */
    public void logException(Exception exception, Map<String, Object> context) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("exceptionClass", exception.getClass().getName());
        logData.put("message", exception.getMessage());
        
        if (context != null) {
            logData.putAll(sanitizeContext(context));
        }
        
        log.error(formatLogMessage(logData), exception);
    }
    
    /**
     * 脱敏上下文数据
     */
    private Map<String, Object> sanitizeContext(Map<String, Object> context) {
        Map<String, Object> sanitized = new HashMap<>();
        context.forEach((key, value) -> {
            if (isSensitiveKey(key)) {
                sanitized.put(key, "***");
            } else {
                sanitized.put(key, value);
            }
        });
        return sanitized;
    }
    
    private boolean isSensitiveKey(String key) {
        String lower = key.toLowerCase();
        return lower.contains("password") || 
               lower.contains("token") || 
               lower.contains("secret") ||
               lower.contains("authorization");
    }
    
    private String formatLogMessage(Map<String, Object> logData) {
        try {
            return objectMapper.writeValueAsString(logData);
        } catch (Exception e) {
            return logData.toString();
        }
    }
}
