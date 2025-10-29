package com.github.blockcertify.extractor;


import cn.hutool.core.util.StrUtil;
import com.github.blockcertify.common.enums.BizExtractorEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 数据提取器管理器
 * <p>
 * 它的核心职责应该是“管理”和“协调”不同的 DataExtractor 实现，例如根据业务类型获取一个具体的 DataExtractor
 *
 * @author wangwenpeng
 * @date 2025/09/18
 */
@Component
public class DataExtractorManager {

    /*
    * key 是首字母小写点类名
    * value 是 DataExtractor 实例
    */
    @Resource
    private Map<String, DataExtractor> extractors;

    /**
     * 根据业务类型获取提取器
     *
     * @return {@link DataExtractor } 数据提取器
     */
    public DataExtractor getExtractorByBizType(String bizType) {
        if (StrUtil.isEmpty(bizType)) { return null; }

        BizExtractorEnum byBizType = BizExtractorEnum.getByBizType(bizType);
        if (byBizType == null) return null;

        return extractors.get(byBizType.getExtractorName());
    }
}
