package com.github.blockcertify.infra;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.blockcertify.engine.CertifyContext;
import com.github.blockcertify.engine.CertifyContextHolder;
import com.github.blockcertify.model.CertifyData;
import com.github.blockcertify.model.infra.CertifyRecord;
import com.github.blockcertify.common.enums.CertifyRecordStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class CertifyServiceImpl implements CertifyService {
    @Resource
    private CertifyMapper certifyMapper;

    public CertifyRecord saveCertifyData(CertifyData certifyData, CertifyRecordStatusEnum certifyRecordStatus) {
        CertifyContext ctx = CertifyContextHolder.getContext();

        CertifyRecord certifyRecord = new CertifyRecord()
                .setBizType(certifyData.getBizType()) // 业务类型
                .setBizId(certifyData.getBizId()) // 业务ID
                .setBizOptTime(ctx.getBizOptTime()) // 从上下文中获取业务操作的时间
                .setCreator(ctx.getCreator()) // 从上下文中获取创建人
                .setCreateTime(ctx.getCreateTime()) // 从上下文中获取创建时间
                .setSdkVendor("test")
                .setSdkVersion("1")
                .setStatus(certifyRecordStatus.getStatus()); // 存证记录状态

        certifyMapper.insert(certifyRecord);

        return certifyRecord;
    }

    /**
     * 更新存证记录状态
     *
     * @param id
     * @param certifyRecordStatus
     */
    public void updateRecordStatus(Long id, CertifyRecordStatusEnum certifyRecordStatus) {
        LambdaUpdateWrapper<CertifyRecord> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(CertifyRecord::getStatus, certifyRecordStatus.getStatus())
                .eq(CertifyRecord::getId, id);
        certifyMapper.update(null, updateWrapper);
    }
}
