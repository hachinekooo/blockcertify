package com.github.blockcertify.infra;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.blockcertify.model.infra.CertifyRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 区块链存证记录Mapper接口
 * 
 * @author wangwenpeng
 * @date 2025-09-11
 */
@Mapper
public interface CertifyMapper extends BaseMapper<CertifyRecord> {
    // 基础的增删改查方法由BaseMapper提供，无需额外定义
}