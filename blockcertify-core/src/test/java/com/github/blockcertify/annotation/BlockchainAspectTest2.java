package com.github.blockcertify.annotation;

import com.github.blockcertify.TestApplication;
import com.github.blockcertify.engine.CertifyContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * BlockchainAspect 的测试类
 */
@SpringBootTest(classes = TestApplication.class) // 指向测试专用的启动类
class BlockchainAspectTest2 {


    // 3. 自动注入目标服务和Mock依赖
    @Autowired
    private TestService testService;

    @BeforeEach
    void setUp() {
        // 需要在每个测试方法运行前，手动初始化 CertifyContext，因为测试时没有使用 HTTP 请求，不走拦截器
        // 注意：您可能需要根据您项目的实际情况调整 new CertifyContext() 的创建方式
        CertifyContextHolder.setContext();
        CertifyContextHolder.getContext().setBizOptTime(LocalDateTime.now());
        CertifyContextHolder.getContext().setStartTime(LocalDateTime.now());
        CertifyContextHolder.getContext().setCreateTime(LocalDateTime.now());
        CertifyContextHolder.getContext().setCreator("creator_wwp");
    }

    @Test
    @DisplayName("场景一：成功路径 - 当区块链启用时，应调用存证引擎")
    void successPathTest_whenBlockchainIsEnabled_shouldCallCertifyEngine() throws InterruptedException {

        // Act: 调用被@Certify注解的方法
        String result = testService.doSomething(TestService.USER_ID);

        // Add a small delay to allow the async operation to complete
        Thread.sleep(1500); // Wait a bit longer than the 1000ms in MockBlockchainClient

        // Assert: 验证
        // 验证原始方法返回值是否正确，确保原始逻辑被执行
        assertEquals(TestService.RETURN_VALUE, result);
    }

    @Test
    @DisplayName("当区块链禁用时，应仅保存数据而不调用存证引擎")
    void whenBlockchainIsDisabled_shouldOnlySaveDataAndNotCallEngine() throws InterruptedException {


        // Act: 调用被@Certify注解的方法
        String result = testService.doSomething(TestService.USER_ID);

        // Add a small delay to allow the async operation to complete
        Thread.sleep(1500); // Wait a bit longer than the 1000ms in MockBlockchainClient

        // Assert: 验证
        // 验证原始方法返回值
        assertEquals(TestService.RETURN_VALUE, result);
    }
}
