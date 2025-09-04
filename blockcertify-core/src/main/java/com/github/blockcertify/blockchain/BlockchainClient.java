package com.github.blockcertify.blockchain;

import com.github.blockcertify.model.CertifyData;
import com.github.blockcertify.model.CertifyQueryResult;
import com.github.blockcertify.support.enums.ClientStatusEnum;

import java.util.Map;

/*
 * 区块链客户端接口
 * 规范不同厂家的SDK调用
 * */
public interface BlockchainClient {

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
     * @return boolean 是否存证成功
     */
    boolean certifySync(CertifyData certifyData);

    /**
     * 进行存证（异步）
     *
     * @param certifyData 存证数据
     * @return boolean 是否存证成功
     */
    boolean certifyAsync(CertifyData certifyData);

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
