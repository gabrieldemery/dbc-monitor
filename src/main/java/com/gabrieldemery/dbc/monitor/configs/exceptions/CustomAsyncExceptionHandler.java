package com.gabrieldemery.dbc.monitor.configs.exceptions;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
	
	private final Logger logger = LoggerFactory.getLogger(CustomAsyncExceptionHandler.class);

	@Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
		this.logger.error("Exception for Async execution: ", throwable);
		this.logger.error("Method name - {}", method.getName());
        for (Object param : objects) {
        	this.logger.error("Parameter value - {}", param);
        }
    }

}
