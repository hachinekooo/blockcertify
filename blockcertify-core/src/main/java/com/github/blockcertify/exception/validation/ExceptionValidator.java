package com.github.blockcertify.exception.validation;

import com.github.blockcertify.common.enums.ErrorCode;
import com.github.blockcertify.exception.business.BusinessException;

/**
 * 异常验证工具类
 * 提供常用的参数验证和异常抛出方法
 *
 * @author wangwenpeng
 */
public class ExceptionValidator {

    /**
     * 验证参数非空
     *
     * @param value 待验证值
     * @param paramName 参数名称
     * @throws BusinessException 如果参数为空
     */
    public static void notNull(Object value, String paramName) {
        if (value == null) {
            throw new BusinessException(ErrorCode.INVALID_PARAMETER,
                                      paramName + "不能为空");
        }
    }

    /**
     * 验证字符串非空
     *
     * @param value 待验证字符串
     * @param paramName 参数名称
     * @throws BusinessException 如果字符串为空或空白
     */
    public static void notEmpty(String value, String paramName) {
        if (value == null || value.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_PARAMETER,
                                      paramName + "不能为空");
        }
    }

    /**
     * 验证字符串长度
     *
     * @param value 待验证字符串
     * @param paramName 参数名称
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @throws BusinessException 如果长度不符合要求
     */
    public static void length(String value, String paramName, int minLength, int maxLength) {
        notEmpty(value, paramName);

        if (value.length() < minLength || value.length() > maxLength) {
            throw new BusinessException(ErrorCode.VALIDATION_FAILED,
                                      paramName + "长度必须在" + minLength + "-" + maxLength + "之间");
        }
    }

    /**
     * 验证数值范围
     *
     * @param value 待验证数值
     * @param paramName 参数名称
     * @param min 最小值
     * @param max 最大值
     * @throws BusinessException 如果数值超出范围
     */
    public static void range(Number value, String paramName, Number min, Number max) {
        notNull(value, paramName);

        if (value.doubleValue() < min.doubleValue() || value.doubleValue() > max.doubleValue()) {
            throw new BusinessException(ErrorCode.VALIDATION_FAILED,
                                      paramName + "必须在" + min + "-" + max + "之间");
        }
    }

    /**
     * 验证状态匹配
     *
     * @param current 当前状态
     * @param expected 期望状态
     * @param context 上下文描述
     * @throws BusinessException 如果状态不匹配
     */
    public static void stateEquals(Object current, Object expected, String context) {
        if (!java.util.Objects.equals(current, expected)) {
            throw new BusinessException(ErrorCode.BUSINESS_LOGIC_ERROR,
                                      context + "状态不匹配，当前: " + current + "，期望: " + expected);
        }
    }

    /**
     * 验证条件为真
     *
     * @param condition 验证条件
     * @param errorMessage 错误消息
     * @throws BusinessException 如果条件为假
     */
    public static void isTrue(boolean condition, String errorMessage) {
        if (!condition) {
            throw new BusinessException(ErrorCode.BUSINESS_LOGIC_ERROR, errorMessage);
        }
    }

    /**
     * 验证条件为假
     *
     * @param condition 验证条件
     * @param errorMessage 错误消息
     * @throws BusinessException 如果条件为真
     */
    public static void isFalse(boolean condition, String errorMessage) {
        if (condition) {
            throw new BusinessException(ErrorCode.BUSINESS_LOGIC_ERROR, errorMessage);
        }
    }
}