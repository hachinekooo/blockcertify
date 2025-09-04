package com.github.blockcertify.model;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 区块链存证数据（用于存储数据提取器提取出的业务数据）
 * 
 * @author wangwenpeng
 * @since 2025-09-04
 */
@Data
@Builder
public class CertifyData {
    
    /**
     * 业务类型
     */
    private String bizType;
    
    /**
     * 业务ID
     */
    private String bizId;
    
    /**
     * 结构化数据
     */
    private Map<String, Object> structData;

    /**
     * 非结构化数据
     */
    private List<FileInfo> unStructData;

    /*
    * 状态
    * */
    private Integer status;

    /**
     * 业务操作时间
     */
    private LocalDateTime operateTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}