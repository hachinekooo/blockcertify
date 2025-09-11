package com.github.blockcertify.engine;

import com.github.blockcertify.blockchain.BlockchainClient;
import com.github.blockcertify.model.*;
import com.github.blockcertify.infra.CertifyService;
import com.github.blockcertify.model.infra.CertifyRecord;
import com.github.blockcertify.support.enums.CertifyRecordStatusEnum;
import com.github.blockcertify.support.enums.ClientStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * 存证引擎
 *
 * @author wangwenpeng
 * @date 2025/09/10
 */
@Slf4j
public class CertifyEngine {

    @Resource
    private BlockchainClient blockchainClient;
    @Resource
    private CertifyService certifyService;

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

        // 1. 保存存证基本数据
        CertifyRecord certifyRecord = certifyService.saveCertifyData(certifyData, CertifyRecordStatusEnum.INIT);
        // 2. 调用SDK异步存证方法（返回一个future，这个future是一个"未来的承诺"）
        CompletableFuture<CertifyResult> certifyFuture = blockchainClient.certifyAsync(certifyData);
        // 3. 更新存证记录的状态为处理中
        certifyService.updateRecordStatus(certifyRecord, CertifyRecordStatusEnum.PROCESSING);
        // 4. 注册一个回调函数，这个回调函数会在某个时刻运行，执行这个回调的是另一个线程，所以到这里不会阻塞
        certifyFuture.thenAccept((result) -> {
            if (ObjectUtils.isEmpty(result)) {
                log.error("Certify result is empty, recordId: {}", certifyRecord.getId());
            }
            if (result.isSuccess()) {
                certifyService.updateRecordStatus(certifyRecord, CertifyRecordStatusEnum.SUBMITTED);
            } else {
                certifyService.updateRecordStatus(certifyRecord, CertifyRecordStatusEnum.FAILED);
            }
        });

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

        return null;
    }

    /**
     * 查询存证状态
     *
     * @param tx_hash 交易哈希
     * @return {@link CertifyStatus } 存证状态枚举值
     */
    public CertifyStatus queryCertifyStatus(String tx_hash) {

        return null;
    }
}
