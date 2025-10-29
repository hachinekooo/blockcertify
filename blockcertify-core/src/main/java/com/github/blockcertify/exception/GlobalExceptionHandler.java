package com.github.blockcertify.exception;

import com.github.blockcertify.common.Result;
import com.github.blockcertify.exception.business.BusinessException;
import com.github.blockcertify.exception.system.ConfigException;
import com.github.blockcertify.exception.system.ConnectionException;
import com.github.blockcertify.exception.system.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 业务类异常
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<Result<?>> handleBusinessException(BusinessException e) {
        log.warn("业务异常[{}]: {}", e.getCode(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Result.error(e.getCode(), e.getMessage()));
    }


    // 系统类异常
    @ExceptionHandler({ConfigException.class, ConnectionException.class})
    public ResponseEntity<Result<?>> handleSystemException(SystemException e) {
        log.error("系统配置异常[{}]: {}", e.getCode(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.error(e.getCode(), e.getMessage()));
    }

    // 处理所有未捕获的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Result<?>> handleGlobalException(Exception e) {
        log.error("系统内部异常[{}]: {}", 500, e.getMessage(), e);  // 服务端记录完整堆栈

        String userMessage = "系统繁忙，请稍后再试";
        if (isDevelopmentEnvironment()) {
            userMessage = e.getMessage();  // 开发环境显示具体错误
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.error(500, userMessage));

    }

    private boolean isDevelopmentEnvironment() {
        return true;
    }
}