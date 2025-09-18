package com.github.blockcertify.extractor;


import com.github.blockcertify.model.CertifyData;

/**
 * 数据提取器接口
 * <p>
 * 规范数据提取器的行为，以规范使用方式
 * 
 * @author wangwenpeng
 * @date 2025/09/18
 */
public interface DataExtractor {
    
    /**
     * 提取存证数据
     * 
     * @param args 方法的参数数组
     * @return 提取到的存证数据
     */
    CertifyData extract(Object[] args);
}