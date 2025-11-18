package com.github.blockcertify.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 调试信息（仅开发环境）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DebugInfo {
    
    /**
     * 异常类名
     */
    private String exceptionClass;
    
    /**
     * 堆栈跟踪
     */
    private List<String> stackTrace;
    
    /**
     * 原因链
     */
    private String cause;
    
    /**
     * 请求参数
     */
    private Map<String, Object> requestParams;
    
    /**
     * 请求头
     */
    private Map<String, String> requestHeaders;
}
