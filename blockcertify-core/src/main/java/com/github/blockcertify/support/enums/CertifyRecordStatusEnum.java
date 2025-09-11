package com.github.blockcertify.support.enums;

import lombok.Getter;

@Getter
public enum CertifyRecordStatusEnum {
      INIT("初始化"),           // 刚保存到数据库
      PROCESSING("处理中"),      // 正在调用SDK
      SUBMITTED("已提交"),      // 已提交到区块链，等待打包
      CONFIRMING("确认中"),     // 区块链确认中
      SUCCESS("成功"),          // 存证成功
      FAILED("失败")            // 存证失败
    ;

    private final String status;

    CertifyRecordStatusEnum(String status) {
        this.status = status;
    }
}
