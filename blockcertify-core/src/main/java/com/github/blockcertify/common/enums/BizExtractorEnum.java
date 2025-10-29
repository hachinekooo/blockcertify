package com.github.blockcertify.common.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * 业务类型-提取器枚举
 * 
 * @author wangwenpeng
 * @date 2025/09/18
 */
@Getter
public enum BizExtractorEnum {

    /*
     * 模拟
     * */
    MOCK("mock", "mockDataExtractor"),

    /**
     * 支付
     */
    PAYMENT("payment", "paymentDataExtractor"),

    /**
     * 转账
     */
    TRANSFER("transfer", "transferDataExtractor");


    /**
     * 业务类型
     */
    private final String bizType;

    /**
     * 提取器名称
     */
    private final String extractorName;

    BizExtractorEnum(String businessType, String extractorName) {
        this.bizType = businessType;
        this.extractorName = extractorName;
    }

    /**
     * 根据业务类型获取对应的枚举值
     * <p>
     * 该方法放在枚举类中，此功能与 BizExtractorEnum 紧密相关
     * 唯一职责就是根据一个字符串查找并返回一个对应的枚举示例，放在内部使得枚举本身是自包含，完整的
     *
     * @param bizType 业务类型
     * @return 业务类型对应的枚举值
     */
    public static BizExtractorEnum getByBizType(String bizType) {
        if (StrUtil.isEmpty(bizType)) { return null; }
        for (BizExtractorEnum bizExtractorEnum : values()) {
            if (bizExtractorEnum.getBizType().equals(bizType)) {
                return bizExtractorEnum;
            }
        }
        return null;
    }
}