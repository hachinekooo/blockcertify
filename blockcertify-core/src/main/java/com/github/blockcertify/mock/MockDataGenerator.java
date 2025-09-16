package com.github.blockcertify.mock;


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
public class MockDataGenerator {
    
    private final Random random = new Random();
    
    /**
     * 生成测试用的库存推送存证数据
     */
    public CertifyData generateStockInboundData() {
        Map<String, Object> data = new HashMap<>();
        
        // 基础信息
        data.put("stockInId", "stockInId" + System.currentTimeMillis());
        data.put("stockNumber", "RK" + System.currentTimeMillis());
        data.put("stockInDate", LocalDateTime.now());
        
        // 仓库信息
        data.put("warehouseId", random.nextInt(100) + 1);
        data.put("warehouseName", "青岛仓库" + random.nextInt(10));
        
        // 货主信息
        data.put("shipperId", random.nextInt(50) + 1);
        data.put("shipperName", "王文鹏" + random.nextInt(5));

        
        // 推送详情
        data.put("stockInQuantity", random.nextDouble() * 1000 + 100);
        data.put("stockInWeight", random.nextDouble() * 500 + 50);
        data.put("stockInVolume", random.nextDouble() * 200 + 20);
        
        // 操作者信息
        data.put("operator", "admin" + random.nextInt(100));
        data.put("operateTime", LocalDateTime.now());

        
        return CertifyData.builder()
                .bizType("stock_in")
                .bizId(data.get("stockInId").toString())
                .structData(data)
                .operateTime(LocalDateTime.now())
                .createTime(LocalDateTime.now())
                .build();
    }
    
    /**
     * 生成测试用的出库审核存证数据
     */
    public CertifyData generateStockOutboundData() {
        Map<String, Object> data = new HashMap<>();
        
        // 基础信息
        data.put("stockOutId", "stockOutId" + System.currentTimeMillis());
        data.put("stockOutNumber", "CK" + System.currentTimeMillis());
        data.put("stockOutDate", LocalDateTime.now());


        // 仓库信息
        data.put("warehouseId", random.nextInt(100) + 1);
        data.put("warehouseName", "青岛仓库" + random.nextInt(10));

        // 货主信息
        data.put("shipperId", random.nextInt(50) + 1);
        data.put("shipperName", "王文鹏" + random.nextInt(5));

        // 出库详情
        data.put("stockOutQuantity", random.nextDouble() * 500 + 50);
        data.put("stockOutWeight", random.nextDouble() * 200 + 20);
        data.put("stockOutVolume", random.nextDouble() * 100 + 10);

        // 审核者信息
        data.put("auditorId", "AUDITOR_" + random.nextInt(50));
        data.put("auditComment", "测试审核通过");

        return CertifyData.builder()
                .bizType("stock_outbound")
                .bizId(data.get("stockOutId").toString())
                .structData(data)
                .operateTime(LocalDateTime.now())
                .createTime(LocalDateTime.now())
                .build();
    }

    /**
     * 根据业务类型生成对应的测试数据
     */
    public CertifyData generateDataByBusinessType(String businessType) {
        switch (businessType) {
            case "stockIn":
                return generateStockInboundData();
            case "stockOut":
                return generateStockOutboundData();
            default:
                log.warn("未知的业务类型: {}, 返回默认的库存推送数据", businessType);
                return null;
        }
    }

}