package com.github.blockcertify.config;

import com.github.blockcertify.interceptor.CertifyContextInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
  public class WebConfig implements WebMvcConfigurer {

      @Resource
      private CertifyContextInterceptor contextInterceptor;

      @Override
      public void addInterceptors(InterceptorRegistry registry) {
          registry.addInterceptor(contextInterceptor)
              .addPathPatterns("/certify/**");
      }
  }
