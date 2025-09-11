package com.github.blockcertify.model;


/**
 * 存证状态枚举类
 *
 * @author wangwenpeng
 * @date 2025/09/11
 */
public enum CertifyStatus {

    PACKED("PACKED"), // 刚提交，等待打包
    SECURE("SECURE"), // 基本安全
    FINAL("FINAL"), // 非常安全，几乎不可逆
    ;

    private final String description;

    CertifyStatus(String description) {
        this.description = description;
    }

    private CertifyStatus getByConfirmations(int confirmations, int secureThreshold, int finalThreshold) {
        if (confirmations == 0) { return PACKED; }
        if (confirmations < secureThreshold) { return SECURE; }
        if (confirmations < finalThreshold) { return FINAL; }
        return FINAL;
    }
}
