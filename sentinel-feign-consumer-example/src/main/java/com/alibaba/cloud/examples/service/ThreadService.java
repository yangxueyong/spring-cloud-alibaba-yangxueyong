package com.alibaba.cloud.examples.service;

import com.alibaba.cloud.examples.fallback.EchoServiceFallbackFactory2;
import feign.Request;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author yangxy
 * <p>
 * example feign client
 */
public interface ThreadService {

	String core(int n);

}
