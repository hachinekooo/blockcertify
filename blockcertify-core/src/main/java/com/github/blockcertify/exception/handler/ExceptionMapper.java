package com.github.blockcertify.exception.handler;

import com.github.blockcertify.common.enums.ErrorCode;
import com.github.blockcertify.engine.CertifyContextHolder;
import com.github.blockcertify.exception.CertifyException;
import com.github.blockcertify.exception.response.DebugInfo;
import com.github.blockcertify.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 异常映射器 - 将异常转换为标准错误响应
 */
@Slf4j
@Component
public class ExceptionMapper {
    
    @Value("${spring.profiles.active:prod}")
    private String activeProfile;
    
    /**
     * 映射 CertifyException 到 ErrorResponse
     */
    public ErrorResponse mapToResponse(CertifyException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        HttpServletRequest request = getCurrentRequest();
        
        ErrorResponse.ErrorResponseBuilder builder = ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .errorMessage(exception.getMessage())
                .userMessage(exception.getUserMessage() != null ? 
                        exception.getUserMessage() : getUserFriendlyMessage(errorCode))
                .traceId(getTraceId())
                .path(request != null ? request.getRequestURI() : null)
                .method(request != null ? request.getMethod() : null)
                .retryable(exception.isRetryable())
                .category(errorCode.getCategory())
                .severity(exception.getSeverity())
                .details(exception.getDetails());
        
        // 重试策略
        if (exception.isRetryable()) {
            builder.retryAfter((int) exception.getRetryPolicy().backoff().getSeconds())
                   .maxRetryAttempts(exception.getRetryPolicy().maxAttempts());
        }
        
        // 开发环境附加调试信息
        if (isDevelopment()) {
            builder.debugInfo(buildDebugInfo(exception, request));
        }
        
        return builder.build();
    }
    
    /**
     * 映射通用异常到 ErrorResponse
     */
    public ErrorResponse mapToResponse(Exception exception) {
        HttpServletRequest request = getCurrentRequest();
        ErrorCode errorCode = ErrorCode.SYSTEM_ERROR;
        
        ErrorResponse.ErrorResponseBuilder builder = ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .errorMessage(errorCode.getMessage())
                .userMessage(isDevelopment() ? exception.getMessage() : "系统繁忙，请稍后再试")
                .traceId(getTraceId())
                .path(request != null ? request.getRequestURI() : null)
                .method(request != null ? request.getMethod() : null)
                .retryable(errorCode.isRetryable())
                .category(errorCode.getCategory())
                .severity(errorCode.getSeverity());
        
        if (isDevelopment()) {
            builder.debugInfo(buildDebugInfo(exception, request));
        }
        
        return builder.build();
    }
    
    private boolean isDevelopment() {
        return "dev".equalsIgnoreCase(activeProfile) || 
               "development".equalsIgnoreCase(activeProfile);
    }
    
    private String getUserFriendlyMessage(ErrorCode errorCode) {
        // 可根据需要自定义用户友好消息
        return errorCode.getMessage();
    }
    
    private String getTraceId() {
        try {
            return CertifyContextHolder.getContext().getTraceId();
        } catch (Exception e) {
            return null;
        }
    }
    
    private HttpServletRequest getCurrentRequest() {
        try {
            ServletRequestAttributes attributes = 
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return attributes != null ? attributes.getRequest() : null;
        } catch (Exception e) {
            return null;
        }
    }
    
    private DebugInfo buildDebugInfo(Throwable exception, HttpServletRequest request) {
        DebugInfo.DebugInfoBuilder builder = DebugInfo.builder()
                .exceptionClass(exception.getClass().getName())
                .stackTrace(Arrays.stream(exception.getStackTrace())
                        .limit(10)
                        .map(StackTraceElement::toString)
                        .collect(Collectors.toList()));
        
        if (exception.getCause() != null) {
            builder.cause(exception.getCause().toString());
        }
        
        if (request != null) {
            // 请求参数
            Map<String, Object> params = new HashMap<>();
            request.getParameterMap().forEach((key, values) -> 
                    params.put(key, values.length == 1 ? values[0] : Arrays.asList(values)));
            builder.requestParams(params);
            
            // 请求头（脱敏）
            Map<String, String> headers = new HashMap<>();
            java.util.Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames != null && headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                if (!isSensitiveHeader(name)) {
                    headers.put(name, request.getHeader(name));
                }
            }
            builder.requestHeaders(headers);
        }
        
        return builder.build();
    }
    
    private boolean isSensitiveHeader(String headerName) {
        String lower = headerName.toLowerCase();
        return lower.contains("authorization") || 
               lower.contains("token") || 
               lower.contains("password") ||
               lower.contains("secret");
    }
}
