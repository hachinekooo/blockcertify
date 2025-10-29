package com.github.blockcertify.infra;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.blockcertify.model.infra.CertifyRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 区块链存证记录Mapper接口
 * 
 * @author wangwenpeng
 * @date 2025-09-11
 */
@Mapper
public interface CertifyMapper extends BaseMapper<CertifyRecord> {
    // 基础的增删改查方法由BaseMapper提供，无需额外定义

    @Select("SELECT * FROM certify_record WHERE tx_hash = #{txHash}")
    CertifyRecord selectByTxHash(String txHash);

}