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

package com.alibaba.cloud.examples;

import com.alibaba.cloud.examples.annotation.MyTestAnnotation;
import com.alibaba.cloud.examples.config.MarketFeignConfig;
import com.alibaba.cloud.examples.config.MarketFeignUtil;
import com.alibaba.cloud.examples.config.SpringContextLoader;
import com.alibaba.cloud.examples.config.UserConfig;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author lengleng
 */
@EnableFeignClients
@SpringCloudApplication
@EnableHystrix
@EnableHystrixDashboard
@EnableDiscoveryClient
public class ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

	@Bean
	public MarketFeignConfig marketFeignConfig() {
		return new MarketFeignConfig();
	}

	@Bean
	public UserConfig userConfig() {
		return new UserConfig();
	}

	@PostConstruct
	public void init(){
		new Thread(){
			@SneakyThrows
			@Override
			public void run() {
				Thread.sleep(5000);
				Map<String, Object> beansWithAnnotation = SpringContextLoader.getBeansWithAnnotation(MyTestAnnotation.class);
				System.out.println("-->" + JSON.toJSONString(beansWithAnnotation));
			}
		}.start();

	}
}


@Component
class SampleRunner implements ApplicationRunner {

	@Autowired
	private NacosConfigManager nacosConfigManager;

	@Autowired
	private MarketFeignUtil marketFeignUtil;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		nacosConfigManager.getConfigService().addListener(
				"service-consumer.properties", "DEFAULT_GROUP", new Listener() {
					@Override
					public void receiveConfigInfo(String configInfo) {
						Properties properties = new Properties();
						try {
							properties.load(new StringReader(configInfo));
						}
						catch (IOException e) {
							e.printStackTrace();
						}
						System.out.println("1config changed: " + properties);
						marketFeignUtil.clearMap();
					}

					@Override
					public Executor getExecutor() {
						return null;
					}
				});
	}

}


