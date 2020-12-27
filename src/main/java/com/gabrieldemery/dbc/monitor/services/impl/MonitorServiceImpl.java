package com.gabrieldemery.dbc.monitor.services.impl;

import java.io.IOException;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gabrieldemery.dbc.monitor.models.OutputFileModel;
import com.gabrieldemery.dbc.monitor.services.MonitorService;
import com.gabrieldemery.dbc.monitor.services.ReaderService;
import com.gabrieldemery.dbc.monitor.services.WriterService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class MonitorServiceImpl implements MonitorService {
	
	@Autowired
	private WatchService watchService;
	
	@Autowired
	private ReaderService readerService;
	
	@Autowired
	private WriterService writerService;

	@Async
	@PostConstruct
	public void launchMonitoring() {
		
		log.info("Starting monitoring of the input folder.");
		
		try {
			WatchKey key;
			while ((key = this.watchService.take()) != null) {
				for (WatchEvent<?> event : key.pollEvents()) {
					this.processFile(event.context().toString());
				}
				key.reset();
			}
		} catch (InterruptedException e) {
			log.warn("Stopping input folder monitoring.");
		}
	}

	@PreDestroy
	public void stopMonitoring() {
		
		log.info("Stop monitoring the input folder.");

		if (this.watchService != null) {
			try {
				this.watchService.close();
			} catch (IOException e) {
				log.error("Failed to end monitoring of the input folder.");
			}
		}
	}
	
	/**
	 * Process created file.
	 * @param fileName (String) File name.
	 */
	private void processFile(String fileName) {
		OutputFileModel outputFile = this.readerService.processFile(fileName);
		if (Objects.nonNull(outputFile)) {
			this.writerService.processFile(fileName, outputFile);
		}
	}

}
