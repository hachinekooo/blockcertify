package com.github.blockcertify.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * 区块链配置类，用于配置区块链相关的参数
 *
 * @author wangwenpeng
 * @date 2025/09/18
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "blockchain")
public class BlockchainConfig {
    private boolean enabled;
    private Testing testing;

    @Data
    public static class Testing {
        private boolean mockEnabled;
        private MockConfig mockConfig;
    }

    @Data
    public static class MockConfig {
        private double successRate;
        private int avgResponseTime;
        private boolean simulateReorg;
        private boolean simulateRandomNetworkDelay;
    }
}
