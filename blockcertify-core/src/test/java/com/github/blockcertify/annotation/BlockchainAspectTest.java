package com.github.blockcertify.annotation;

import com.github.blockcertify.config.BlockchainConfig;
import com.github.blockcertify.engine.CertifyEngine;
import com.github.blockcertify.extractor.DataExtractor;
import com.github.blockcertify.extractor.DataExtractorManager;
import com.github.blockcertify.infra.CertifyServiceImpl;
import com.github.blockcertify.model.CertifyData;
import com.github.blockcertify.model.CertifyResult;
import com.github.blockcertify.support.enums.CertifyRecordStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * BlockchainAspect 的测试类
 */
@SpringBootTest
class BlockchainAspectTest {

    // 1. 定义测试所需的配置类
    @Configuration
    @EnableAspectJAutoProxy // 开启AOP
    static class TestConfig {

        // 将切面本身作为一个Bean注入
        @Bean
        public BlockchainAspect blockchainAspect() {
            return new BlockchainAspect();
        }

        // 将被AOP环绕的目标服务也作为Bean注入
        @Bean
        public TestService testService() {
            return new TestService();
        }
    }

    // 2. 定义一个被AOP环绕的目标服务
    @Service
    static class TestService {
        public static final String BIZ_TYPE = "testBiz";
        public static final String RETURN_VALUE = "Success";
        public static final String USER_ID = "user-123";

        @Certify(bizType = BIZ_TYPE)
        public String doSomething(String userId) {
            System.out.println("Executing original method logic for user: " + userId);
            return RETURN_VALUE;
        }
    }

    // 3. 自动注入目标服务和Mock依赖
    @Autowired
    private TestService testService;

    @MockBean
    private CertifyEngine certifyEngine;

    @MockBean
    private BlockchainConfig blockchainConfig;

    @MockBean
    private CertifyServiceImpl certifyService;

    @MockBean
    private DataExtractorManager dataExtractorManager;

    // 4. 准备通用的Mock逻辑
    @BeforeEach
    void setUp() {
        // 当调用dataExtractorManager.getExtractorByBizType时返回模拟数据提取器
        DataExtractor mockExtractor = mock(DataExtractor.class);
        when(dataExtractorManager.getExtractorByBizType(TestService.BIZ_TYPE)).thenReturn(mockExtractor);
        // 当调用mockExtractor.extract时返回模拟的存证数据
        CertifyData mockCertifyData = new CertifyData(); // 准备一个假的存证数据
        when(mockExtractor.extract(any())).thenReturn(mockCertifyData);

        // 当调用certifyEngine.certify时返回模拟的存证结果
        CertifyResult mockResult = new CertifyResult();
        mockResult.setTxHash("mock-tx-hash");
        when(certifyEngine.certify(any(CertifyData.class)))
                .thenReturn(CompletableFuture.completedFuture(mockResult));
    }


    @Test
    @DisplayName("场景一：成功路径 - 当区块链启用时，应调用存证引擎")
    void successPathTest_whenBlockchainIsEnabled_shouldCallCertifyEngine() throws InterruptedException {
        // Arrange: 模拟区块链配置为“启用”
        when(blockchainConfig.isEnabled()).thenReturn(true);

        // Act: 调用被@Certify注解的方法
        String result = testService.doSomething(TestService.USER_ID);

        // Add a small delay to allow the async operation to complete
        Thread.sleep(1500); // Wait a bit longer than the 1000ms in MockBlockchainClient

        // Assert: 验证
        // 验证原始方法返回值是否正确，确保原始逻辑被执行
        assertEquals(TestService.RETURN_VALUE, result);

        // 验证存证数据是否以 INIT 状态保存
        ArgumentCaptor<CertifyRecordStatusEnum> statusCaptor = ArgumentCaptor.forClass(CertifyRecordStatusEnum.class);
        verify(certifyService, times(1)).saveCertifyData(any(CertifyData.class), statusCaptor.capture());
        assertEquals(CertifyRecordStatusEnum.INIT, statusCaptor.getValue());

        // 验证 certifyEngine.certify 是否被调用了1次
        verify(certifyEngine, times(1)).certify(any(CertifyData.class));
    }

    @Test
    @DisplayName("当区块链禁用时，应仅保存数据而不调用存证引擎")
    void whenBlockchainIsDisabled_shouldOnlySaveDataAndNotCallEngine() {
        // Arrange: 模拟区块链配置为“禁用”
        when(blockchainConfig.isEnabled()).thenReturn(false);

        // Act: 调用被@Certify注解的方法
        String result = testService.doSomething(TestService.USER_ID);

        // Assert: 验证
        // 验证原始方法返回值
        assertEquals(TestService.RETURN_VALUE, result);

        // 验证存证数据是否以 DISABLED 状态保存
        ArgumentCaptor<CertifyRecordStatusEnum> statusCaptor = ArgumentCaptor.forClass(CertifyRecordStatusEnum.class);
        verify(certifyService, times(1)).saveCertifyData(any(CertifyData.class), statusCaptor.capture());
        assertEquals(CertifyRecordStatusEnum.DISABLED, statusCaptor.getValue());

        // 验证 certifyEngine.certify 一次都未被调用
        verify(certifyEngine, never()).certify(any(CertifyData.class));
    }
}
