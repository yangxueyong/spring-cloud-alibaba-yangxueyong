package com.alibaba.cloud.examples.config;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "market.feign")
public class MarketFeignConfig {

	private Map<String,Object> timeout;

	private List<Map<String,Object>> urlreplace;

	@Override
	public String toString() {
		return "timeout:"+ JSON.toJSONString(timeout);
	}
}
