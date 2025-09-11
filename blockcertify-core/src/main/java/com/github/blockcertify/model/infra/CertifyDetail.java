package com.github.blockcertify.model.infra;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 区块链存证详情表
 *
 * @author wangwenpeng
 * @date 2025-09-11
 */
@Data // 自动生成getter、setter、equals、hashCode、toString方法
@EqualsAndHashCode(callSuper = false) // 不调用父类的equals和hashCode方法
@Accessors(chain = true) // 支持链式调用
@TableName("certify_detail") // 表名
public class CertifyDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联主表ID
     */
    @TableField("certify_record_id")
    private Long certifyRecordId;

    /**
     * 存证结构化数据（JSON 格式）
     */
    @TableField("certify_struct_data")
    private String certifyStructData;

    /**
     * 结构化数据的 HASH 值
     */
    @TableField("certify_struct_data_hash")
    private String certifyStructDataHash;

    /**
     * 存证非结构化数据（JSON 格式）
     */
    @TableField("certify_un_struct_data")
    private String certifyUnStructData;

    /**
     * SDK 返回数据
     */
    @TableField("sdk_data")
    private String sdkData;

    /**
     * 完整响应数据
     */
    @TableField("sdk_response_data")
    private String sdkResponseData;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}