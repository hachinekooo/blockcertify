package com.github.blockcertify.engine;

import com.github.blockcertify.blockchain.BlockchainClient;
import com.github.blockcertify.common.enums.ErrorCode;
import com.github.blockcertify.exception.business.BusinessException;
import com.github.blockcertify.infra.CertifyMapper;
import com.github.blockcertify.infra.CertifyServiceImpl;
import com.github.blockcertify.model.CertifyData;
import com.github.blockcertify.model.CertifyQueryResult;
import com.github.blockcertify.model.CertifyResult;
import com.github.blockcertify.model.CertifyStatus;
import com.github.blockcertify.model.infra.CertifyRecord;
import com.github.blockcertify.common.enums.ClientStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * 存证引擎
 *
 * @author wangwenpeng
 * @date 2025/09/10
 */
@Slf4j
@Component
public class CertifyEngine {

    @Resource
    private BlockchainClient blockchainClient;
    @Resource
    private CertifyServiceImpl certifyService;
    @Resource
    private CertifyMapper certifyMapper;

    /**
     * 存证数据（默认异步）
     *
     * @param certifyData 存证数据
     * @return {@link String } 唯一交易哈希
     */
    public CompletableFuture<CertifyResult> certify(CertifyData  certifyData) {
        ClientStatusEnum clientStatus = blockchainClient.getStatus();
        if (clientStatus != ClientStatusEnum.CONNECTED) {
            log.error("certify status is not CONNECTED");
            return null;
        }

        // 调用SDK异步存证方法（返回一个future，这个future是一个"未来的承诺"）
        CompletableFuture<CertifyResult> certifyFuture = blockchainClient.certifyAsync(certifyData);
        return certifyFuture;
    }

    /**
     * 存证数据（同步异步）
     *
     * @param certifyData 存证数据
     * @return {@link String } 唯一交易哈希
     */
    public CertifyResult certifySync(CertifyData certifyData) {
        ClientStatusEnum clientStatus = blockchainClient.getStatus();
        if (clientStatus != ClientStatusEnum.CONNECTED) {
            log.error("certify status is not CONNECTED");
            return null;
        }
        return blockchainClient.certifySync(certifyData);
    }

    /**
     * 查询存证信息
     *
     * @param tx_hash 交易哈希
     * @return {@link CertifyQueryResult } 存证查询结果
     */
    public CertifyQueryResult queryCertify(String tx_hash) {
        CertifyQueryResult queryResult = new CertifyQueryResult();

        CertifyRecord certifyRecord = certifyMapper.selectByTxHash(tx_hash);

        if (certifyRecord == null) {
            throw new BusinessException(ErrorCode.CERTIFY_NOT_FOUND, tx_hash);
        }

        BeanUtils.copyProperties(certifyRecord, queryResult);
        queryResult.setQueryTime(LocalDateTime.now());

        return queryResult;
    }

    /**
     * 查询存证状态
     *
     * @param tx_hash 交易哈希
     * @return {@link CertifyStatus } 存证状态枚举值
     */
    public CertifyStatus queryCertifyStatus(String tx_hash) {

        CertifyRecord certifyRecord = certifyMapper.selectByTxHash(tx_hash);

        if (certifyRecord == null) {
            throw new BusinessException(ErrorCode.CERTIFY_NOT_FOUND, tx_hash);
        }
        String status = certifyRecord.getStatus();
        return CertifyStatus.valueOf(status);
    }
}
