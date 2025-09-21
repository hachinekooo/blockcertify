package com.github.blockcertify.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Certify {

    /*
    * 业务类型
    * */
    String bizType();

    /*
     * 描述信息
     **/
    String description() default "";

   /*
   * 是否开启异步存证
   * */
   boolean async() default true;

   /*
   * 是否支持重试
   * */
   boolean retryable() default true;

}
