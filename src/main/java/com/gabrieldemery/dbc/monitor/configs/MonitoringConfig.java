package com.gabrieldemery.dbc.monitor.configs;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MonitoringConfig {
	
	@Value("${folder.monitor}")
    private String folderMonitorPath;

    @Bean
    public WatchService watchService() {
        log.debug("MONITORING_FOLDER: {}", folderMonitorPath);
        
        WatchService watchService = null;
        
        try {
            watchService = FileSystems.getDefault().newWatchService();
            
            Path path = Paths.get(folderMonitorPath);
            log.debug("Path: {}", path.toString());
            
            /*
            if (!Files.isDirectory(path)) {
                throw new RuntimeException("incorrect monitoring folder: " + path);
            }
            */

            path.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_CREATE
            );
        } catch (IOException e) {
            log.error("exception for watch service creation:", e);
        }
        return watchService;
    }
	
}
