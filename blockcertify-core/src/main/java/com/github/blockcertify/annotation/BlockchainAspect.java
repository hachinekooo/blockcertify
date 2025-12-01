package com.github.blockcertify.annotation;

import com.github.blockcertify.config.BlockchainConfig;
import com.github.blockcertify.engine.CertifyEngine;
import com.github.blockcertify.exception.business.BusinessException;
import com.github.blockcertify.exception.system.SystemException;
import com.github.blockcertify.extractor.DataExtractor;
import com.github.blockcertify.extractor.DataExtractorManager;
import com.github.blockcertify.infra.CertifyServiceImpl;
import com.github.blockcertify.model.CertifyData;
import com.github.blockcertify.model.CertifyResult;
import com.github.blockcertify.model.infra.CertifyRecord;
import com.github.blockcertify.common.enums.CertifyRecordStatusEnum;
import com.github.blockcertify.common.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Aspect // 标识这是一个切面类，用于处理横切逻辑
@Component
public class BlockchainAspect {

    @Resource
    private CertifyEngine certifyEngine;

    @Resource
    private BlockchainConfig blockchainConfig;

    @Resource
    private CertifyServiceImpl certifyService;

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
            CertifyRecord certifyRecord = certifyService.saveCertifyData(data, CertifyRecordStatusEnum.INIT);
            // 调用SDK
            certifyEngine.certify(data)
                    // 3. 更新存证记录的状态为处理中
                    .thenApply(result -> {
                        certifyService.updateRecordStatus(certifyRecord.getId(), CertifyRecordStatusEnum.PROCESSING);
                        return result;
                    })
                    // 转换结果(同步执行，不会阻塞主线程)
                    .thenApply(result -> result.getTxHash())
                    // 消费结果
                    .thenAccept(txHash -> {
                        log.info("存证成功，交易哈希为: {}", txHash);
                        certifyService.updateRecordStatus(certifyRecord.getId(), CertifyRecordStatusEnum.SUBMITTED);
                    })
                    // 异常处理，当链路中任何一步出现异常时触发
                    .exceptionally(ex -> {
                        log.error("存证失败，异常信息为: {}", ex.getMessage());
                        certifyService.updateRecordStatus(certifyRecord.getId(), CertifyRecordStatusEnum.FAILED);
                        return null; // 返回null表示失败，实际结果通过createFailedResult方法处理
                    });

        } catch (BusinessException e) {
            log.error("存证处理失败，业务异常信息为: {}", e.getMessage());
            throw e; // 重新抛出业务异常，让全局异常处理器处理
        } catch (Exception e) {
            log.error("存证处理失败，系统异常信息为: {}", e.getMessage(), e);
            throw new SystemException(ErrorCode.SYSTEM_ERROR,
                                     "存证处理过程中发生系统异常", e);
        }
    }

    /**
     * 创建失败的存证结果
     * TODO(human): 请实现 createFailedResult 方法，根据异常类型返回适当的失败结果
     *
     * 你的任务: 实现这个方法，根据不同的异常类型创建合适的失败结果对象
     *
     * 指导原则:
     * 1. 对于 BusinessException，返回业务逻辑失败的 CertifyResult
     * 2. 对于 SystemException，返回系统错误的 CertifyResult
     * 3. 对于其他异常，返回通用错误结果的 CertifyResult
     * 4. 确保结果对象包含适当的错误信息和状态
     */
    private CertifyResult createFailedResult(Throwable ex) {
        // TODO(human): 在这里实现你的异常处理逻辑
        // 提示: 可以使用 instanceof 检查异常类型，然后根据类型创建不同的结果
        return null;
    }
}