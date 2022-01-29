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

package com.alibaba.cloud.examples.fallback;

import com.alibaba.cloud.examples.service.EchoService2;
import feign.Request;

/**
 * @author lengleng
 * <p>
 * sentinel 降级处理
 */
public class EchoServiceFallback2 implements EchoService2 {

	private Throwable throwable;

	EchoServiceFallback2(Throwable throwable) {
		this.throwable = throwable;
	}

	/**
	 * 调用服务提供方的输出接口.
	 * @param str 用户输入
	 * @return String
	 */
	@Override
	public String echo(String str, Request.Options options) {
		return "consumer-fallback-default-str2 " + throwable.getMessage();
	}

}
