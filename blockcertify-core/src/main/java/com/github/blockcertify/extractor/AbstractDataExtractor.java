package com.github.blockcertify.extractor;


import com.github.blockcertify.model.FileInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 数据提取器抽象基类，提供通用的处理方法，减少样板代码
 *
 * @author wangwenpeng
 * @date 2025/09/18
 */
@Slf4j
public abstract class AbstractDataExtractor implements DataExtractor {


    /**
     * 根据业务类型和业务ID构建非结构化数据
     * <p>
     * 这里提供一个默认的实现，子类可以根据需要覆盖此方法
     *
     * @param bizType 业务类型
     * @param bizId   业务ID
     * @return {@link List }<{@link FileInfo }> 非结构化数据列表
     */
    public List<FileInfo> buildUnStructData(String bizType, String bizId) {
        // 模拟构建非结构化数据
        List<FileInfo> fileInfos = Stream.of(
                FileInfo.builder().fileName(bizType + "_" + bizId + ".pdf").relativePath("/data/" + bizId + ".pdf").absolutePath("/users/wangwenpeng/data/" + bizId + ".pdf").url("http://example.com/" + bizId + ".pdf").build(),
                FileInfo.builder().fileName(bizType + "_" + bizId + ".docx").relativePath("/data/" + bizId + ".docx").absolutePath("/users/wangwenpeng/data/" + bizId + ".docx").url("http://example.com/" + bizId + ".docx").build()
        ).collect(Collectors.toList());

        return fileInfos;
    }

}