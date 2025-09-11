package com.github.blockcertify.engine;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 存证上下文
 *
 * @author wangwenpeng
 * @date 2025/09/11
 */
@Data //自动生成getter、setter、equals、hashCode、toString方法
@Builder // 构建者模式
public class CertifyContext {


    /**
     * 业务操作时间
     */
    private LocalDateTime bizOptTime;


    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

}
