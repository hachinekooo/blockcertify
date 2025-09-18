package com.github.blockcertify.extractor;


import com.github.blockcertify.model.CertifyData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Mock数据生成器，用于生成测试用的存证数据
 * 
 * @author wangwenpeng
 * @since 2025-08-12
 */
@Slf4j
@Component
public class MockDataExtractor extends AbstractDataExtractor{
    
    private final Random random = new Random();
    
    /**
     * 生成测试用的支付存证数据
     */
    @Override
    public CertifyData extract(Object[] args) {

        // 由具体的数据提取器自行处理参数，自行获取合适的参数以便后续使用
        for (Object arg : args) {
            log.info("Acquired parameters: {}", arg.toString());
        }

        Map<String, Object> data = new HashMap<>();

        // 基础信息
        data.put("paymentId", "paymentId" + System.currentTimeMillis());
        data.put("orderId", "orderId" + System.currentTimeMillis());
        data.put("paymentTime", LocalDateTime.now());

        // 支付方信息
        data.put("payerId", random.nextInt(100) + 1);
        data.put("payerName", "付款方" + random.nextInt(10));

        // 收款方信息
        data.put("payeeId", random.nextInt(50) + 1);
        data.put("payeeName", "收款方" + random.nextInt(5));

        // 支付详情
        data.put("amount", random.nextDouble() * 1000 + 100);
        data.put("currency", "CNY");

        // 操作者信息
        data.put("operator", " wangwenpeng");
        data.put("operateTime", LocalDateTime.now());


        return CertifyData.builder()
                .bizType("payment")
                .bizId(data.get("paymentId").toString())
                .structData(data)
                .unStructData(buildUnStructData("mock","001"))
                .operateTime(LocalDateTime.now())
                .createTime(LocalDateTime.now())
                .build();
    }
}