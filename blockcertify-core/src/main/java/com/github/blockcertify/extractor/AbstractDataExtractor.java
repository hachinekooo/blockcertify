package com.github.blockcertify.extractor;


import com.github.blockcertify.model.FileInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 数据提取器抽象基类，提供通用的处理方法，减少样板代码
 *
 * @author wangwenpeng
 * @date 2025/09/18
 */
@Slf4j
public abstract class AbstractDataExtractor implements DataExtractor {


    /**
     * 构建非结构化数据方法
     *
     * @return {@link List }<{@link FileInfo }> 非结构化数据列表
     */
    public List<FileInfo> buildUnStructData() {

        return null;
    }

}