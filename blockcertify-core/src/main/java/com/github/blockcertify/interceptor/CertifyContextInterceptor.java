package com.github.blockcertify.interceptor;

import com.github.blockcertify.engine.CertifyContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Slf4j
@Component
public class CertifyContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("开始处理请求");
        CertifyContextHolder.setContext();
        CertifyContextHolder.getContext().setStartTime(LocalDateTime.now());
        CertifyContextHolder.getContext().setBizOptTime(LocalDateTime.now());
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

        CertifyContextHolder.clean();
        log.info("请求结束");
    }

}
