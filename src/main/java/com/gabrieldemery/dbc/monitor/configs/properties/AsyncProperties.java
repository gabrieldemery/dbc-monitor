package com.gabrieldemery.dbc.monitor.configs.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class AsyncProperties {
	
	@Value("${async.corePoolSize}")
	int corePoolSize;
	
	@Value("${async.maxPoolSize}")
	int maxPoolSize;
	
	@Value("${async.queueCapacity}")
	int queueCapacity;
	
	@Value("${async.threadName}")
	String threadName;
	
    public int getCorePoolSize() {
    	return this.corePoolSize;
    }
    
    public int getMaxPoolSize() {
    	return this.maxPoolSize;
    }
    
    
    public int getQueueCapacity() {
    	return this.queueCapacity;
    }
    
    public String getThreadName() {
    	return this.threadName;
    }
	
}
