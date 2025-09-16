package com.github.blockcertify.engine;


/**
 * 存证上下文持有者
 *
 * @author wangwenpeng
 * @date 2025/09/12
 */
public class CertifyContextHolder {

    private static final ThreadLocal<CertifyContext> contextHolder = new ThreadLocal<>();

    /**
     * 设置上下文
     */
    public static void setContext() {
        contextHolder.set(new CertifyContext());
    }

    /**
     * 获取上下文
     *
     * @return {@link CertifyContext } 上下文
     */
    public static CertifyContext getContext() {
        CertifyContext certifyContext = contextHolder.get();
        if (certifyContext == null) {
            // 抛这个异常是因为 IllegalStateException 表示对象处于不正确的状态
            throw new IllegalStateException("CertifyContext has not been initialized for current thread. Please call set() first.");
        }
        return certifyContext;
    }


    /**
     * 移除上下文
     */
    public static void clean() {
        contextHolder.remove();
    }
}
