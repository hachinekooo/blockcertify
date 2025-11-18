package com.github.blockcertify.exception.metrics;

import com.github.blockcertify.exception.CertifyException;
import com.github.blockcertify.exception.model.ExceptionSeverity;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 异常指标收集器（Micrometer 集成）
 */
@Slf4j
@Component
@ConditionalOnClass(MeterRegistry.class)
@ConditionalOnBean(MeterRegistry.class)
public class ExceptionMetrics {
    
    @Resource
    private MeterRegistry meterRegistry;
    
    private static final String METRIC_PREFIX = "blockcertify.exception";
    
    @PostConstruct
    public void init() {
        if (meterRegistry == null) {
            log.warn("MeterRegistry 未配置，异常指标收集已禁用");
        }
    }
    
    /**
     * 记录异常发生
     */
    public void recordException(CertifyException exception, String path) {
        if (meterRegistry == null) {
            return;
        }
        
        try {
            Counter.builder(METRIC_PREFIX + ".count")
                    .tag("error_code", String.valueOf(exception.getErrorCode().getCode()))
                    .tag("category", exception.getErrorCode().getCategory().name())
                    .tag("severity", exception.getSeverity().name())
                    .tag("retryable", String.valueOf(exception.isRetryable()))
                    .tag("path", sanitizePath(path))
                    .description("Exception occurrence count")
                    .register(meterRegistry)
                    .increment();
        } catch (Exception e) {
            log.warn("记录异常指标失败", e);
        }
    }
    
    /**
     * 记录异常处理时间
     */
    public void recordExceptionHandlingTime(CertifyException exception, long durationMs) {
        if (meterRegistry == null) {
            return;
        }
        
        try {
            Timer.builder(METRIC_PREFIX + ".handling.time")
                    .tag("severity", exception.getSeverity().name())
                    .tag("category", exception.getErrorCode().getCategory().name())
                    .description("Exception handling duration")
                    .register(meterRegistry)
                    .record(durationMs, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.warn("记录处理时间失败", e);
        }
    }
    
    /**
     * 记录严重程度分布
     */
    public void recordSeverityDistribution(ExceptionSeverity severity) {
        if (meterRegistry == null) {
            return;
        }
        
        try {
            Counter.builder(METRIC_PREFIX + ".severity")
                    .tag("level", severity.name())
                    .description("Exception severity distribution")
                    .register(meterRegistry)
                    .increment();
        } catch (Exception e) {
            log.warn("记录严重程度分布失败", e);
        }
    }
    
    /**
     * 清理路径（避免高基数标签）
     */
    private String sanitizePath(String path) {
        if (path == null) {
            return "unknown";
        }
        // 替换路径中的 ID 等动态部分
        return path.replaceAll("/\\d+", "/{id}")
                   .replaceAll("/[a-f0-9]{8,}", "/{uuid}");
    }
}
