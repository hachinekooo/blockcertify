package com.github.blockcertify.blockchain;

import com.github.blockcertify.config.BlockchainConfig;
import com.github.blockcertify.model.CertifyData;
import com.github.blockcertify.model.CertifyQueryResult;
import com.github.blockcertify.model.CertifyResult;
import com.github.blockcertify.support.enums.ClientStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * 模拟区块链客户端
 *
 * @author wangwenpeng
 * @date 2025/09/10
 */
@Slf4j
@Component
public class MockBlockchainClient implements BlockchainClient {

    @Resource
    private BlockchainConfig blockchainConfig;

    private double failureRate = 0.0;
    private long delayMillis = 1000;
    private boolean reorgEnabled = false;
    private Random random = new Random();

    @Override
    public void init(Map<String, String> config) {
        log.info("初始化模拟区块链客户端...");

        // 优先解析配置 Map
        if (config != null && !config.isEmpty()) {
            try {
                if (config.containsKey("mock.failure-rate")) {
                    failureRate = Double.parseDouble(config.get("mock.failure-rate"));
                }
                if (config.containsKey("mock.delay-millis")) {
                    delayMillis = Long.parseLong(config.get("mock.delay-millis"));
                }
                if (config.containsKey("mock.reorg-enabled")) {
                    reorgEnabled = Boolean.parseBoolean(config.get("mock.reorg-enabled"));
                }
                if (config.containsKey("mock.seed")) {
                    long seed = Long.parseLong(config.get("mock.seed"));
                    random = new Random(seed);
                }
            } catch (Exception e) {
                log.warn("解析 map mock 配置失败，使用其他来源值", e);
            }

        } else {
            // 回退到注入的配置类
            refreshFromConfiguration();
        }
        log.info("Mock config => failureRate={}, delayMs={}, reorg={}", failureRate, delayMillis, reorgEnabled);
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

    // 从 BlockchainConfig 读取
    private void refreshFromConfiguration() {

        try {
             double cfgFailureRate = blockchainConfig.getTesting().getMockConfig().getFailureRate();
             long cfgDelayMillis  = blockchainConfig.getTesting().getMockConfig().getDelayMillis();
             boolean cfgReorgEnabled = blockchainConfig.getTesting().getMockConfig().isReorgEnabled();
             long cfgSeed = blockchainConfig.getTesting().getMockConfig().getSeed();

            if (cfgFailureRate >= 0.0 && cfgFailureRate <= 1.0) {
                failureRate = cfgFailureRate;
            }
            if (cfgDelayMillis > 0) {
                delayMillis = cfgDelayMillis;
            }
            reorgEnabled = cfgReorgEnabled;
            random = new Random(cfgSeed);

        } catch (Exception e) {
            log.warn("从 BlockchainConfig 读取配置失败，使用默认值", e);
        }
    }
}
