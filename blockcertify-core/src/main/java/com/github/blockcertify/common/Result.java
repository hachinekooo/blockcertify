package com.github.blockcertify.common;

import com.github.blockcertify.engine.CertifyContext;
import com.github.blockcertify.engine.CertifyContextHolder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一返回结果封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer code;    // 状态码
    private String message; // 返回信息
    private T data;         // 返回数据

    private Long timestamp; // 响应时间戳
    private String traceId;  // 链路追踪ID
    private String path;     // 请求路径


    // 成功静态方法
    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return success(200, "操作成功", data);
    }

    public static <T> Result<T> success(String message, T data) {
        return success(200, message, data);
    }

    public static <T> Result<T> success(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        // 自动设置拓展信息，不用手动传
        result.setTimestamp(System.currentTimeMillis());
        CertifyContext context = CertifyContextHolder.getContext();
        result.setTraceId(CertifyContextHolder.getContext().getTraceId());
        result.setPath(context.getPath());
        return result;
    }

    // 失败静态方法
    public static <T> Result<T> error(String message) {
        return error(500, message);
    }

    public static <T> Result<T> error(Integer code, String message) {
        return error(code, message, null);
    }

    public static <T> Result<T> error(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }

    // 便捷方法
    public boolean isSuccess() {
        return code != null && code == 200;
    }
}