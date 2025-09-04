package com.github.blockcertify.model;

import lombok.Builder;
import lombok.Data;

/**
 * 文件信息
 *
 * @author wangwenpeng
 * @since 2025-09-04
 */
@Data
@Builder
public class FileInfo {

    /*
    * 文件名
    * */
    private String fileName;

    /*
     * 文件的相对路径
     * */
    private String relativePath ;

    /*
    * 文件的绝对路径
    * */
    private String absolutePath;

    /*
    * 文件的URL
    * */
    private String url;

    /*
    * 文件的 SHA256校验值
    * 区块链标准，安全性高，与链上哈希算法一致
    * */
    private String sha256;

    /*
     * 文件的 MD5 校验值
     * 兼容性好，很多老系统还在用，便于对接
     * */
    private String md5;
}