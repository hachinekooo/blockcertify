package com.github.blockcertify.blockchain;

import com.github.blockcertify.model.CertifyData;
import com.github.blockcertify.model.CertifyQueryResult;
import com.github.blockcertify.model.CertifyResult;
import com.github.blockcertify.support.enums.ClientStatusEnum;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 模拟区块链客户端
 *
 * @author wangwenpeng
 * @date 2025/09/10
 */
@Slf4j
public class MockBlockchainClient implements BlockchainClient {
    @Override
    public void init(Map<String, String> config) {
        log.info("初始化模拟区块链客户端...");
    }

    @Override
    public ClientStatusEnum getStatus() {
        return ClientStatusEnum.CONNECTED;
    }

    @Override
    public String getTechType() {
        return "MOCK";
    }

    @Override
    public CertifyResult certifySync(CertifyData certifyData) {
        log.info("模拟存证（同步）...");
        return CertifyResult.builder()
                .txIndex(1)
                .txHash("0x123...")
                .success(true)
                .blockNumber(BigInteger.valueOf(100))
                .blockHeight(BigInteger.valueOf(100))
                .build();
    }

    @Override
    public CompletableFuture<CertifyResult> certifyAsync(CertifyData certifyData) {
        log.info("模拟存证（异步）...");
        CompletableFuture<CertifyResult> asyncTask = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                return CertifyResult.builder()
                        .txIndex(1)
                        .txHash("0x123...")
                        .success(true)
                        .blockNumber(BigInteger.valueOf(100))
                        .blockHeight(BigInteger.valueOf(100))
                        .build();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return asyncTask;

    }

    @Override
    public CertifyQueryResult queryCertify(String txHash) {
        log.info("模拟查询存证...");
        return null;
    }

    @Override
    public boolean validateCertify(String txHash) {
        log.info("模拟验证存证...");
        return true;
    }

    @Override
    public void close() throws Exception {
        log.info("关闭模拟区块链客户端...");
    }
}
