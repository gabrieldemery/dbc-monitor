package com.gabrieldemery.dbc.monitor.services.impl;

import java.io.IOException;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gabrieldemery.dbc.monitor.models.OutputFileModel;
import com.gabrieldemery.dbc.monitor.services.MonitorService;
import com.gabrieldemery.dbc.monitor.services.ReaderService;
import com.gabrieldemery.dbc.monitor.services.WriterService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MonitorServiceImpl implements MonitorService {
	
	private final Logger logger = LoggerFactory.getLogger(MonitorServiceImpl.class);
	
	@Autowired
	private WatchService watchService;
	
	@Autowired
	private ReaderService readerService;
	
	@Autowired
	private WriterService writerService;

	@Async
	@PostConstruct
	public void launchMonitoring() {
		
		this.logger.info("START_MONITORING");
		
		try {
			WatchKey key;
			while ((key = this.watchService.take()) != null) {
				for (WatchEvent<?> event : key.pollEvents()) {
					this.processFile(event.context().toString());
				}
				key.reset();
			}
		} catch (InterruptedException e) {
			this.logger.warn("interrupted exception for monitoring service");
		}
	}

	@PreDestroy
	public void stopMonitoring() {
		
		this.logger.info("STOP_MONITORING");

		if (this.watchService != null) {
			try {
				this.watchService.close();
			} catch (IOException e) {
				this.logger.error("exception while closing the monitoring service");
			}
		}
	}
	
	private void processFile(String fileName) {
		OutputFileModel outputFile = this.readerService.processFile(fileName);
		this.writerService.processFile(fileName, outputFile);
	}

}
