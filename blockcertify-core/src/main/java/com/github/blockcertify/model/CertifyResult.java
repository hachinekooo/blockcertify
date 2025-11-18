package com.github.blockcertify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

/**
 * 存证结果
 *
 * @author wangwenpeng
 * @since 2025-09-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
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