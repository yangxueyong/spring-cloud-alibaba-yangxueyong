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
import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import feign.Request;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

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
		threadPoolKey="abc",
		threadPoolProperties = {
				// 线程池核心线程数
				@HystrixProperty(name = "coreSize", value = "3"),
				// 队列最大长度
				@HystrixProperty(name = "maxQueueSize", value = "5"),
				// 排队线程数量阈值，默认为5，达到时拒绝，如果配置了该选项，队列的大小是该队列
				@HystrixProperty(name = "queueSizeRejectionThreshold", value = "7")
		})
public class TestController {

	@Autowired
	private EchoService echoService;

	@Autowired
	private EchoService2 echoService2;

	@Autowired
	private MarketFeignConfig marketFeignConfig;

	@Autowired
	private UserConfig userConfig;

	@Autowired
	private MarketFeignUtil marketFeignUtil;

	@GetMapping("/echo-feign/{str}")
	@HystrixCommand
	public String feign(@PathVariable String str) throws InterruptedException {
		log.info("marketFeignConfig-->{}", marketFeignConfig);
		log.info("userConfig-->{}", userConfig);
		return echoService.echo("[" + marketFeignConfig + "]__[" + userConfig + "]",marketFeignUtil.getReqOption("e1"));
	}

	@GetMapping("/get-thread/{str}")
	@HystrixCommand
	public String getThread(@PathVariable String str) throws InterruptedException {
		String abc = MDC.get("abc");
		return echoService.echo("123",marketFeignUtil.getReqOption("e1"));
	}


	public String paymentCircuitBreaker_Thread(){
		return " Hystrix限流成功 服务限流 " ;
	}

	@PostMapping("/echoPost")
	@HystrixCommand
	public String echoPost(@RequestBody String str) throws InterruptedException {
		return echoService.echoPost(str,marketFeignUtil.getReqOption("e3"));
	}

	@GetMapping("/echo-feign2/{str}")
	public String feign2(@PathVariable String str) {
		return echoService2.echo(str,marketFeignUtil.getReqOption("e2"));
	}

}
