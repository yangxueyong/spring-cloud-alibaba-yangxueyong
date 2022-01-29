package com.alibaba.cloud.examples.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class MyInterceptor implements RequestInterceptor {
    private static Map<String, String> routerMap = new HashMap<String, String>();

    @Autowired
    private MarketFeignUtil marketFeignUtil;

    @Override
    public void apply(RequestTemplate template) {
        String newUrl = marketFeignUtil.getNewUrlReplace(template.url());

//        template.header("Content-Type", "application/json");
//        String serviceName = template.feignTarget().name();
//        Map<String, Collection<String>> map = template.queries();
//        //根据请求参数进行路由获取，这个配置可以自己决定放在什么地方
//        String target = routerMap.get(map.get("projectId").toArray()[0] + ":" + serviceName);
//        if (target != null) {
//            template.target(target);
//        }
//        System.out.println("这是自定义请求拦截器," + template.path());
//        log.info("这是一个什么东西呢->{}",target);
        template.uri(newUrl);
        log.info("这是一个什么东西呢->{}",template.url());
//        template.uri("/echo/99");
        log.info("这是一个什么东西呢->{}",template.url());
        log.info("这是一个什么东西呢->{}",template.path());
        log.info("这是一个什么东西呢->{}",template.feignTarget());
    }
 
}