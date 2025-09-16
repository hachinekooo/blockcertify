package com.github.blockcertify.infra;

import com.github.blockcertify.engine.CertifyContext;
import com.github.blockcertify.engine.CertifyContextHolder;
import com.github.blockcertify.model.CertifyData;
import com.github.blockcertify.model.infra.CertifyRecord;
import com.github.blockcertify.support.enums.CertifyRecordStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class CertifyService {
    @Resource
    private CertifyMapper certifyMapper;

    public CertifyRecord saveCertifyData(CertifyData certifyData, CertifyRecordStatusEnum certifyRecordStatus) {
        CertifyContext ctx = CertifyContextHolder.getContext();

        CertifyRecord certifyRecord = new CertifyRecord()
                .setBizType(certifyData.getBizType()) // 业务类型
                .setBizId(certifyData.getBizId()) // 业务ID
                .setBizOptTime(ctx.getBizOptTime()) // 从上下文中获取业务操作的时间
                .setStatus(certifyRecordStatus.getStatus()); // 存证记录状态

        certifyMapper.insert(certifyRecord);

        return certifyRecord;
    }

    public void updateRecordStatus(CertifyRecord certifyRecord, CertifyRecordStatusEnum certifyRecordStatus) {
        certifyRecord.setStatus(certifyRecordStatus.getStatus());
        certifyMapper.updateById(certifyRecord);
    }
}
