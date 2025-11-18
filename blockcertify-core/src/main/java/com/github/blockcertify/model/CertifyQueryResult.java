package com.github.blockcertify.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 存证查询结果
 *
 * @author wangwenpeng
 * @since 2025-09-11
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CertifyQueryResult extends CertifyResult{

    /*
    * 查询时间
    * */
    private LocalDateTime queryTime;
}