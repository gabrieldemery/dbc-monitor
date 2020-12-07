package com.gabrieldemery.dbc.monitor.services.impl;

import java.io.IOException;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class MonitoringServiceImpl {

	private final WatchService watchService;

	@Async
	@PostConstruct
	public void launchMonitoring() {
		
		log.info("START_MONITORING");
		
		/*
		try {
			WatchKey key;
			while ((key = watchService.take()) != null) {
				for (WatchEvent<?> event : key.pollEvents()) {
					log.debug("Event kind: {}; File affected: {}", event.kind(), event.context());
				}
				key.reset();
			}
		} catch (InterruptedException e) {
			log.warn("interrupted exception for monitoring service");
		}
		*/
		
		try {
			log.debug("Vamos tentar!");
			while (true) {
				WatchKey key = watchService.take();
				log.debug("Key: {}", key.toString());
				List<WatchEvent<?>> events = key.pollEvents();
				for (WatchEvent<?> event : events) {
					log.debug("Event kind: {}; File affected: {}", event.kind(), event.context());
				}
				key.reset();
			}
		} catch (InterruptedException e) {
			log.warn("interrupted exception for monitoring service");
		}
	}

	@PreDestroy
	public void stopMonitoring() {
		
		log.info("STOP_MONITORING");

		if (watchService != null) {
			try {
				watchService.close();
			} catch (IOException e) {
				log.error("exception while closing the monitoring service");
			}
		}
	}

}
