package com.gabrieldemery.dbc.monitor.services;

import org.springframework.stereotype.Service;

import com.gabrieldemery.dbc.monitor.models.OutputFileModel;

@Service
public interface ReaderService {
	
	/**
	 * Process file created in the input folder.
	 * @param fileName (String) File name.
	 * @return (OutputFileModel) Data to be processed for output file.
	 */
	OutputFileModel processFile(String fileName);
	
}
