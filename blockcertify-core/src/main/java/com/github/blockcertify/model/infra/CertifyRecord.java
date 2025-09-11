package com.github.blockcertify.model.infra;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 区块链存证记录主表
 *
 * @author wangwenpeng
 * @date 2025-09-11
 */
@Data // 自动生成getter、setter、equals、hashCode、toString方法
@EqualsAndHashCode(callSuper = false) // 不调用父类的equals和hashCode方法
@Accessors(chain = true) // 支持链式调用
@TableName("certify_record") // 表名
public class CertifyRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键（逻辑主键）
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作业务的类型，如 stockIn/stockOut/stockTransfer
     */
    @TableField("biz_type")
    private String bizType;

    /**
     * 操作业务的主键
     */
    @TableField("biz_id")
    private String bizId;

    /**
     * 业务操作时间
     */
    @TableField("biz_opt_time")
    private LocalDateTime bizOptTime;

    /**
     * 存证时间
     */
    @TableField("certify_time")
    private LocalDateTime certifyTime;

    /**
     * 存证状态：INIT/SUBMITTING/PENDING/PACKED/CONFIRMED/REORGED/SUBMIT_FAILED/PACK_FAILED/FINALIZED
     */
    @TableField("status")
    private String status;

    /**
     * 我们自己的统一错误码
     */
    @TableField("error_code")
    private String errorCode;

    /**
     * 我们自己的统一错误描述
     */
    @TableField("error_message")
    private String errorMessage;

    /**
     * 区块链的交易哈希
     */
    @TableField("tx_hash")
    private String txHash;

    /**
     * 区块链的区块高度
     */
    @TableField("block_height")
    private Long blockHeight;

    /**
     * 区块链的区块哈希
     */
    @TableField("block_hash")
    private String blockHash;

    /**
     * 当前确认数
     */
    @TableField("confirmation_count")
    private Integer confirmationCount;

    /**
     * 要求的确认数（默认6）
     */
    @TableField("required_confirmation")
    private Integer requiredConfirmation;

    /**
     * 是否在主链上
     */
    @TableField("is_in_main_chain")
    private Boolean isInMainChain;

    /**
     * 最后检查时间
     */
    @TableField("last_check_time")
    private LocalDateTime lastCheckTime;

    /**
     * 重组次数
     */
    @TableField("reorg_count")
    private Integer reorgCount;

    /**
     * 原始交易哈希（重新提交时记录）
     */
    @TableField("original_tx_hash")
    private String originalTxHash;

    /**
     * SDK 存证凭证ID
     */
    @TableField("sdk_certificate_id")
    private String sdkCertificateId;

    /**
     * SDK 错误码
     */
    @TableField("sdk_code")
    private String sdkCode;

    /**
     * SDK 返回消息
     */
    @TableField("sdk_message")
    private String sdkMessage;

    /**
     * 重试次数
     */
    @TableField("retry_count")
    private Integer retryCount;

    /**
     * 最大重试次数
     */
    @TableField("max_retry_count")
    private Integer maxRetryCount;

    /**
     * 下次重试时间
     */
    @TableField("next_retry_time")
    private LocalDateTime nextRetryTime;

    /**
     * SDK 厂商标识
     */
    @TableField("sdk_vendor")
    private String sdkVendor;

    /**
     * SDK 版本号
     */
    @TableField("sdk_version")
    private String sdkVersion;

    /**
     * 租户编号
     */
    @TableField("tenant_id")
    private Long tenantId;

    /**
     * 创建人 ID
     */
    @TableField(value = "creator", fill = FieldFill.INSERT)
    private String creator;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人 ID
     */
    @TableField(value = "updater", fill = FieldFill.INSERT_UPDATE)
    private String updater;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标记，0:未删除 1:已删除
     */
    @TableLogic
    @TableField("deleted")
    private Boolean deleted;

}