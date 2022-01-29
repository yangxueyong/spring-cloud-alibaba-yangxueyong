package com.alibaba.cloud.examples.controller;

import com.alibaba.cloud.examples.service.ThreadPoolService;
import com.alibaba.cloud.examples.service.ThreadTtlPoolService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
		threadPoolKey="kkk",
		threadPoolProperties = {
				// 线程池核心线程数
				@HystrixProperty(name = "coreSize", value = "3"),
				// 队列最大长度
				@HystrixProperty(name = "maxQueueSize", value = "5"),
				// 排队线程数量阈值，默认为5，达到时拒绝，如果配置了该选项，队列的大小是该队列
				@HystrixProperty(name = "queueSizeRejectionThreshold", value = "7")
		})
public class ThreadTtlPoolController {

	@Autowired
	private ThreadTtlPoolService threadTtlPoolService;


	@GetMapping("/ttl/{userNo}")
	@HystrixCommand
	public String pool(@PathVariable int userNo) {
		return threadTtlPoolService.core(userNo);
	}

	public String paymentCircuitBreaker_Thread(){
		return " Hystrix限流成功 服务限流 " ;
	}

}
