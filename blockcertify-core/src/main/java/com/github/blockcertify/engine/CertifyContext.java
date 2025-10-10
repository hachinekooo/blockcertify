package com.github.blockcertify.engine;

import com.github.blockcertify.model.CertifyData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 存证上下文
 *
 * @author wangwenpeng
 * @date 2025/09/11
 */
@Data //自动生成getter、setter、equals、hashCode、toString方法
@Builder // 构建者模式
@NoArgsConstructor // 无参构造方法
@AllArgsConstructor // 全参构造方法
public class CertifyContext {

    /*
     * 业务类型
     * */
    private String bizType;


    /*
     * 业务 ID
     * */
    private String bizId;

    /*
     * HTTP请求参数
     * */
    private Object[] httpArgs;

    /*
    * 被存证业务方法参数
    * */
    private Object[] bizArgs;

    /*
     * 提取到的存证数据
     * */
    private CertifyData data;

    /*
     * 认证状态
     * */
    private String status;

    /*
     * 交易哈希
     * */
    private String txHash;

    /*
     * 创建人
     * */
    private String creator;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 业务操作时间
     */
    private LocalDateTime bizOptTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
