package com.github.blockcertify.common.enums;

import lombok.Getter;

@Getter
public enum CertifyRecordStatusEnum {
      INIT("INIT", "初始化"),           // 刚保存到数据库
      PROCESSING("PROCESSING", "处理中"),      // 正在调用SDK
      SUBMITTED("SUBMITTED", "已提交"),      // 已提交到区块链，等待打包
      CONFIRMING("CONFIRMING", "确认中"),     // 区块链确认中
      SUCCESS("SUCCESS", "成功"),          // 存证成功
      FAILED("FAILED", "失败"),            // 存证失败
      DISABLED("DISABLED", "功能未启用");    // 禁用
    ;

    private final String status;
    private final String desc;

    CertifyRecordStatusEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
