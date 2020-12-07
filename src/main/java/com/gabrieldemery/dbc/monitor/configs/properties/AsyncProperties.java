package com.gabrieldemery.dbc.monitor.configs.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;

@ConfigurationProperties
@Getter
public class AsyncProperties {
	
	@Value("${async.corePoolSize}")
	int corePoolSize;
	
	@Value("${async.maxPoolSize}")
	int maxPoolSize;
	
	@Value("${async.queueCapacity}")
	int queueCapacity;
	
	@Value("${async.threadName}")
	String threadName;
	
}
