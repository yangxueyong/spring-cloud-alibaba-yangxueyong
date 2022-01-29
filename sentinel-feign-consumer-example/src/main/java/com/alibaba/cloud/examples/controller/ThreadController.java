/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.cloud.examples.controller;

import com.alibaba.cloud.examples.config.MarketFeignConfig;
import com.alibaba.cloud.examples.config.MarketFeignUtil;
import com.alibaba.cloud.examples.config.UserConfig;
import com.alibaba.cloud.examples.service.EchoService;
import com.alibaba.cloud.examples.service.EchoService2;
import com.alibaba.cloud.examples.service.ThreadService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * @author lengleng
 */
@Slf4j
@RestController
@RefreshScope
@DefaultProperties(
		defaultFallback = "paymentCircuitBreaker_Thread",
		// 属性设置参考：HystrixCommandProperties
		commandProperties = {
				// 隔离策略，有THREAD和SEMAPHORE
				@HystrixProperty(name = "execution.isolation.strategy", value="THREAD"),
				@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "60000" )
		},
		threadPoolKey="bcd",
		threadPoolProperties = {
				// 线程池核心线程数
				@HystrixProperty(name = "coreSize", value = "3"),
				// 队列最大长度
				@HystrixProperty(name = "maxQueueSize", value = "5"),
				// 排队线程数量阈值，默认为5，达到时拒绝，如果配置了该选项，队列的大小是该队列
				@HystrixProperty(name = "queueSizeRejectionThreshold", value = "7")
		})
public class ThreadController {

	@Autowired
	@Qualifier("threadService2Impl")
	private ThreadService threadService2Impl;


	@GetMapping("/getNum/{n}")
	@HystrixCommand
	public String getNum(@PathVariable int n) {
		return threadService2Impl.core(n);
	}

	public String paymentCircuitBreaker_Thread(){
		return " Hystrix限流成功 服务限流 " ;
	}

}
