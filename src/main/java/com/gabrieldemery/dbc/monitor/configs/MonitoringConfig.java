package com.gabrieldemery.dbc.monitor.configs;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MonitoringConfig {
	
	private final Logger logger = LoggerFactory.getLogger(MonitoringConfig.class);
	
	@Value("${folder.monitor}")
    private String folderMonitorPath;

    @Bean
    public WatchService watchService() {
        this.logger.debug("MONITORING_FOLDER: {}", this.folderMonitorPath);
        
        WatchService watchService = null;
        
        try {
            watchService = FileSystems.getDefault().newWatchService();

            File directory = new File(this.folderMonitorPath);
            if (!directory.exists()) {
                directory.mkdir();
            }
            
            Path path = Paths.get(this.folderMonitorPath);
            this.logger.debug("Path: {}", path.toString());
            
            path.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE
            );
            
        } catch (IOException e) {
        	this.logger.error("exception for watch service creation:", e);
        }
        return watchService;
    }
	
}
