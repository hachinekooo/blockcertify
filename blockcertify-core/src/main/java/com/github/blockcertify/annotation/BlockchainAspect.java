package com.github.blockcertify.annotation;

import com.github.blockcertify.engine.CertifyEngine;
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

    @AfterReturning("@annotation(certify)")
    public void afterMethod(JoinPoint joinPoint, Certify certify) {
        try {
            log.info("开始处理存证，业务类型为: {}", certify.bizType());

        } catch (Exception e) {

        }
    }

}