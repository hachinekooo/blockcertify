package com.github.blockcertify.annotation;

import com.github.blockcertify.config.BlockchainConfig;
import com.github.blockcertify.engine.CertifyEngine;
import com.github.blockcertify.infra.CertifyService;
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

    @AfterReturning("@annotation(certify)")
    public void afterMethod(JoinPoint joinPoint, Certify certify) {
        // 使用 try catch 包裹所有通知逻辑，以捕获一场避免增强逻辑影响正常业务代码
        try {

            // 检查区块链功能是否已启用
            if (!blockchainConfig.isEnabled()) { log.info("区块链功能未启用，不处理存证"); return; }

            log.info("开始处理存证，业务类型为: {}", certify.bizType());

            // 提取存证数据

            // 保存存证
            certifyService.saveCertifyData(null, null);

            // 调用SDK


        } catch (Exception e) {

        }
    }

}