package com.gabrieldemery.dbc.monitor.services;

import com.gabrieldemery.dbc.monitor.models.OutputFileModel;

public interface WriterService {
	
	/**
	 * Process the data to generate the file in the output folder.
	 * @param fileName (String) File name.
	 * @param outputFile (OutputFileModel) Data to be processed for output file.
	 */
	void processFile(String fileName, OutputFileModel outputFile);

}
