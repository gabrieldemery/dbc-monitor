package com.gabrieldemery.dbc.monitor.configs;

import java.io.File;
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
	
	@Value("${folder.output}")
    private String folderOutputPath;

    @Bean
    public WatchService watchService() {
        log.debug("Monitoring the input folder: {}", this.getPathInput());
        
        WatchService watchService = null;
        
        try {
            watchService = FileSystems.getDefault().newWatchService();
            
            File directoryInput = new File(this.getPathInput());
            if (!directoryInput.exists()) {
            	if ( !directoryInput.mkdir() ) {
            		log.info("The input folder could not be created.");
            	}
            }
            
            File directoryOutput = new File(this.getPathOutput());
            if (!directoryOutput.exists()) {
            	if ( !directoryOutput.mkdir() ) {
            		log.info("The output folder could not be created.");
            	}
            }
            
            Path pathFolderMonitorPath = Paths.get(this.getPathInput());
            log.debug("Path: {}", pathFolderMonitorPath.toString());
            
            pathFolderMonitorPath.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE
            );
            
        } catch (IOException e) {
        	log.error("Observation of the input folder failed:", e);
        }
        return watchService;
    }
    
    /**
     * Full path of the input folder.
     * @return (String) Input folder path.
     */
	private String getPathInput() {
		return System.getProperty("user.home").concat("\\").concat(this.folderMonitorPath);
	}
    
    /**
     * Full path of the output folder.
     * @return (String) Output folder path.
     */
	private String getPathOutput() {
		return System.getProperty("user.home").concat("\\").concat(this.folderOutputPath);
	}
	
}
