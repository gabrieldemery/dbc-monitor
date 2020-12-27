package com.gabrieldemery.dbc.monitor.services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gabrieldemery.dbc.monitor.models.OutputFileModel;
import com.gabrieldemery.dbc.monitor.services.ReaderService;
import com.gabrieldemery.dbc.monitor.utils.InputFileUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReaderServiceImpl implements ReaderService {
	
	@Value("${file.input.extension}")
    private String extensionAllowed;
	
	@Value("${folder.monitor}")
    private String folderMonitorPath;
	
	@Autowired
	InputFileUtils inputFileUtils;
	
	@Override
	public OutputFileModel processFile(String fileName) {
		
		log.info("Starting to read file: {}", fileName);
		
		OutputFileModel outputFileModel = null;

        try {
            this.checkFile(fileName);
            this.checkExtension(fileName);
            
            String pathWithFile = this.getPathInput().concat("\\").concat(fileName);

            List<String> lines  = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(pathWithFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if(!line.isEmpty())
                        lines.add(line);
                }
            }

            if(lines.size() > 0){
            	outputFileModel = this.inputFileUtils.processData(lines);
            }

        } catch (Exception e) {
            log.error("Error to read file: {}. {}", fileName, e.getMessage());
        }

        log.info("Ending to read file: {}", fileName);
        
        return outputFileModel;
	}
    
    /**
     * Full path of the input folder.
     * @return (String) Input folder path.
     */
	private String getPathInput() {
		String pathInput =  System.getProperty("user.home").concat("\\").concat(this.folderMonitorPath);
		log.info("Input Path: {}", pathInput);
		return pathInput;
	}
	
	/**
	 * Check if the file exists in the input folder.
	 * @param fileName (String) File name.
	 * @throws Exception
	 */
	private void checkFile(String fileName) throws Exception {
		File file = new File(this.getPathInput().concat("\\").concat(fileName));
		if (!file.canRead()) {
			throw new Exception("Failed to open file " + fileName + ".");
		}
	}
	
	/**
	 * Check if the file extension is valid.
	 * @param fileName (String) File name.
	 * @throws Exception
	 */
    private void checkExtension(String fileName) throws Exception {
        if(!(fileName.toLowerCase().endsWith(this.extensionAllowed))) {
            throw new Exception("Invalid format, only allowed " + this.extensionAllowed + " file extension.");
        }

    }
}
