package com.github.blockcertify.model;

import java.math.BigInteger;

/**
 * 存证查询结果
 *
 * @author wangwenpeng
 * @since 2025-09-04
 */
public class CertifyQueryResult {

    /*
     * 交易索引
     * */
    private int txIndex;

    /*
     * 交易哈希
     * */
    private String txHash;

    /*
     * 存证状态；true-成功，false-失败
     * */
    private boolean status;

    /*
     * 区块编号
     * */
    private BigInteger blockNumber;

    /*
     * 区块高度
     * */
    private BigInteger blockHeight;
}