package com.github.blockcertify.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

/**
 * 存证结果
 *
 * @author wangwenpeng
 * @since 2025-09-04
 */
@Data
@Builder
public class CertifyResult {

    /*
     * 交易索引
     * */
    private int txIndex;

    /*
     * 交易哈希
     * */
    private String txHash;

    /*
     * 存证状态；true-成功提交，false-提交失败
     * */
    private boolean success;

    /*
     * 区块编号
     * */
    private BigInteger blockNumber;

    /*
     * 区块高度
     * */
    private BigInteger blockHeight;
}