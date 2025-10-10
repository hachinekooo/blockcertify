package com.github.blockcertify.infra;

import com.github.blockcertify.model.CertifyData;
import com.github.blockcertify.model.infra.CertifyRecord;
import com.github.blockcertify.support.enums.CertifyRecordStatusEnum;

public interface CertifyService {

    /**
     * 保存存证数据
     *
     * @param certifyData 存证数据
     * @param certifyRecordStatus 存证记录状态
     * @return {@link CertifyRecord }
     */
    CertifyRecord saveCertifyData(CertifyData certifyData, CertifyRecordStatusEnum certifyRecordStatus);

    /**
     * 更新存证记录状态
     *
     * @param id 存证记录ID
     * @param certifyRecordStatus 存证记录状态
     */
    void updateRecordStatus(Long id, CertifyRecordStatusEnum certifyRecordStatus);
}
