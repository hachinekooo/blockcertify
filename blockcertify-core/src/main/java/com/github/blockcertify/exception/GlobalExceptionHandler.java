package com.github.blockcertify.exception;

import com.github.blockcertify.common.enums.ErrorCode;
import com.github.blockcertify.exception.business.BusinessException;
import com.github.blockcertify.exception.handler.ExceptionMapper;
import com.github.blockcertify.exception.logging.ExceptionLogger;
import com.github.blockcertify.exception.metrics.ExceptionMetrics;
import com.github.blockcertify.exception.response.ErrorResponse;
import com.github.blockcertify.exception.system.SystemException;
import com.github.blockcertify.exception.thirdparty.ThirdPartyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全局异常处理器 - 企业级异常体系
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Resource
    private ExceptionMapper exceptionMapper;
    
    @Resource
    private ExceptionLogger exceptionLogger;
    
    @Autowired(required = false)
    private ExceptionMetrics exceptionMetrics;

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        long startTime = System.currentTimeMillis();
        
        log.warn("业务异常[{}][{}]: {}", e.getErrorCode().getCode(), e.getSeverity(), e.getMessage());
        exceptionLogger.logException(e, null);
        
        ErrorResponse response = exceptionMapper.mapToResponse(e);
        
        if (exceptionMetrics != null) {
            exceptionMetrics.recordException(e, response.getPath());
            exceptionMetrics.recordExceptionHandlingTime(e, System.currentTimeMillis() - startTime);
            exceptionMetrics.recordSeverityDistribution(e.getSeverity());
        }
        
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(response);
    }

    /**
     * 处理第三方异常
     */
    @ExceptionHandler(ThirdPartyException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleThirdPartyException(ThirdPartyException e) {
        long startTime = System.currentTimeMillis();
        
        log.error("第三方服务异常[{}][{}]: {}", e.getErrorCode().getCode(), e.getSeverity(), e.getMessage(), e);
        exceptionLogger.logException(e, null);
        
        ErrorResponse response = exceptionMapper.mapToResponse(e);
        
        if (exceptionMetrics != null) {
            exceptionMetrics.recordException(e, response.getPath());
            exceptionMetrics.recordExceptionHandlingTime(e, System.currentTimeMillis() - startTime);
            exceptionMetrics.recordSeverityDistribution(e.getSeverity());
        }
        
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(response);
    }

    /**
     * 处理系统异常
     */
    @ExceptionHandler(SystemException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleSystemException(SystemException e) {
        long startTime = System.currentTimeMillis();
        
        log.error("系统异常[{}][{}]: {}", e.getErrorCode().getCode(), e.getSeverity(), e.getMessage(), e);
        exceptionLogger.logException(e, null);
        
        ErrorResponse response = exceptionMapper.mapToResponse(e);
        
        if (exceptionMetrics != null) {
            exceptionMetrics.recordException(e, response.getPath());
            exceptionMetrics.recordExceptionHandlingTime(e, System.currentTimeMillis() - startTime);
            exceptionMetrics.recordSeverityDistribution(e.getSeverity());
        }
        
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(response);
    }

    /**
     * 处理参数校验异常（@Valid）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        log.warn("参数校验失败: {}", e.getMessage());
        
        Map<String, String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "验证失败",
                        (v1, v2) -> v1
                ));
        
        ErrorResponse response = ErrorResponse.builder()
                .errorCode(ErrorCode.VALIDATION_FAILED.getCode())
                .errorMessage("参数校验失败")
                .userMessage("请求参数不合法")
                .traceId(exceptionMapper.mapToResponse(e).getTraceId())
                .category(ErrorCode.VALIDATION_FAILED.getCategory())
                .severity(ErrorCode.VALIDATION_FAILED.getSeverity())
                .details(new HashMap<String, Object>() {{ put("fieldErrors", errors); }})
                .build();
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    /**
     * 处理约束违反异常（@Validated）
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException e) {
        log.warn("约束违反: {}", e.getMessage());
        
        Map<String, String> errors = e.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage,
                        (v1, v2) -> v1
                ));
        
        ErrorResponse response = ErrorResponse.builder()
                .errorCode(ErrorCode.VALIDATION_FAILED.getCode())
                .errorMessage("约束违反")
                .userMessage("请求参数不合法")
                .category(ErrorCode.VALIDATION_FAILED.getCategory())
                .severity(ErrorCode.VALIDATION_FAILED.getSeverity())
                .details(new HashMap<String, Object>() {{ put("violations", errors); }})
                .build();
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    /**
     * 处理所有未捕获的异常（兜底）
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception e) {
        log.error("未预期的系统异常: {}", e.getMessage(), e);
        ErrorResponse response = exceptionMapper.mapToResponse(e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}