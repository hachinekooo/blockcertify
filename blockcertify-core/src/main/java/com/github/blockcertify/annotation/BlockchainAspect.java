package com.github.blockcertify.annotation;

import com.github.blockcertify.config.BlockchainConfig;
import com.github.blockcertify.engine.CertifyEngine;
import com.github.blockcertify.extractor.DataExtractor;
import com.github.blockcertify.extractor.DataExtractorManager;
import com.github.blockcertify.infra.CertifyService;
import com.github.blockcertify.model.CertifyData;
import com.github.blockcertify.support.enums.CertifyRecordStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import javax.annotation.Resource;

@Slf4j
@Aspect // 标识这是一个切面类，用于处理横切逻辑
public class BlockchainAspect {

    @Resource
    private CertifyEngine certifyEngine;

    @Resource
    private BlockchainConfig blockchainConfig;

    @Resource
    private CertifyService certifyService;

    @Resource
    private DataExtractorManager dataExtractorManager;

    @AfterReturning("@annotation(certify)")
    public void afterMethod(JoinPoint joinPoint, Certify certify) {
        // 使用 try catch 包裹所有通知逻辑，以捕获一场避免增强逻辑影响正常业务代码
        try {
            log.info("开始处理存证，业务类型为: {}", certify.bizType());

            // 提取存证数据
            DataExtractor dataExtractor = dataExtractorManager.getExtractorByBizType(certify.bizType());
            CertifyData data = dataExtractor.extract(joinPoint.getArgs());

            // 检查区块链功能是否已启用
            if (!blockchainConfig.isEnabled()) {
                certifyService.saveCertifyData(data, CertifyRecordStatusEnum.DISABLED);
                log.info("存证未启用，仅保存存证记录到表，不调用SDK");
                return; // 直接返回，不执行后续逻辑
            }

            // 保存存证
            certifyService.saveCertifyData(data, CertifyRecordStatusEnum.INIT);

            // 调用SDK
            certifyEngine.certify(data)
                    // 转换结果(同步执行，不会阻塞主线程)
                    .thenApply(result -> result.getTxHash())
                    // 消费结果
                    .thenAccept(txHash -> {
                        log.info("存证成功，交易哈希为: {}", txHash);
                    })
                    // 异常处理，当链路中任何一步出现异常时触发
                    .exceptionally(ex -> {
                        log.error("存证失败，异常信息为: {}", ex.getMessage());
                        return null;
                    });


        } catch (Exception e) {
            log.error("存证处理失败，异常信息为: {}", e.getMessage());
        }
    }
}