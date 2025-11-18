package com.github.blockcertify.interceptor;

import cn.hutool.core.lang.UUID;
import com.github.blockcertify.engine.CertifyContext;
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
        CertifyContext context = CertifyContextHolder.getContext();
        context.setStartTime(LocalDateTime.now());
        context.setBizOptTime(LocalDateTime.now());
        context.setPath(request.getRequestURI());

        // 设置traceId
        String traceId = request.getHeader("X-Trace-Id");
        if (traceId == null) {
            traceId = UUID.randomUUID().toString().replace("-", "");
        }
        context.setTraceId(traceId);
        response.setHeader("X-Trace-Id", traceId); // 放到response header里，前端可以获取
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

        CertifyContextHolder.clean();
        log.info("请求结束");
    }

}
