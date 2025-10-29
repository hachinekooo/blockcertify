package com.github.blockcertify.blockchain;

import com.github.blockcertify.model.CertifyData;
import com.github.blockcertify.model.CertifyQueryResult;
import com.github.blockcertify.model.CertifyResult;
import com.github.blockcertify.common.enums.ClientStatusEnum;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/*
 * 规范不同厂家的SDK调用。
 *
 * AutoCloseable 接口支持 try-with-resources
 * */
public interface BlockchainClient extends AutoCloseable{

    /**
     * 初始化配置
     *
     * @param config 配置源
     */
    void init(Map<String, String> config);

    /*
     * 获取客户端状态
     * */
    ClientStatusEnum getStatus();


    /**
     * 区块链技术类型
     *
     * @return {@link String } 返回技术类型，如 ANTCHAIN、TENCENTCHAIN
     */
    String getTechType();


    /**
     * 进行存证（同步）
     *
     * @param certifyData 存证数据
     * @return {@link CertifyResult } 存证结果
     */
    CertifyResult certifySync(CertifyData certifyData);

    /**
     * 进行存证（异步）
     *
     * @param certifyData 存证数据
     * @return {@link CertifyResult } 存证结果
     */
    CompletableFuture<CertifyResult> certifyAsync(CertifyData certifyData);

    /**
     * 查询存证
     *
     * @param txHash 交易哈希
     * @return {@link CertifyQueryResult } 存证查询结果
     */
    CertifyQueryResult queryCertify(String txHash);


    /**
     * 验证存证内容
     *
     * @param txHash 交易哈希
     * @return boolean 是否被篡改
     */
    boolean validateCertify(String txHash);

}
