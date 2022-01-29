package com.alibaba.cloud.examples.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class MyFeignConfig {
 
    @Bean
    public RequestInterceptor myRequestInterceptor(){
 
        return new MyInterceptor();
 
    }
 
}