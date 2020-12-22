package com.gabrieldemery.dbc.monitor.services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gabrieldemery.dbc.monitor.models.OutputFileModel;
import com.gabrieldemery.dbc.monitor.services.ReaderService;
import com.gabrieldemery.dbc.monitor.utils.InputFileUtils;

@Service
public class ReaderServiceImpl implements ReaderService {
	
	private final Logger logger = LoggerFactory.getLogger(ReaderServiceImpl.class);
	
	@Value("${file.input.extension}")
    private String extensionAllowed;
	
	@Value("${folder.monitor}")
    private String folderMonitorPath;
	
	@Autowired
	InputFileUtils inputFileUtils;

	@Override
	public OutputFileModel processFile(String fileName) {
		
		this.logger.info("Starting to read file: {}", fileName);
		
		OutputFileModel outputFileModel = null;

        try {
            this.checkFile(fileName);
            this.checkExtension(fileName);

            List<String> lines  = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
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
            this.logger.error("Error to read file: {}. {}", fileName, e.getMessage());
        }

        this.logger.info("Ending to read file: {}", fileName);
        
        return outputFileModel;
	}
	
	private void checkFile(String fileName) throws Exception {
		File file = new File(this.folderMonitorPath + "/" + fileName);
		if (!file.canRead()) {
			throw new Exception("Failed to open file " + fileName + ".");
		}
	} 

    private void checkExtension(String fileName) throws Exception {
        if(!(fileName.toLowerCase().endsWith(this.extensionAllowed))) {
            throw new Exception("Invalid format, only allowed " + this.extensionAllowed + " file extension");
        }

    }
	
	
	
}
