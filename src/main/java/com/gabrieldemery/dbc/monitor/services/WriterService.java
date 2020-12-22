package com.gabrieldemery.dbc.monitor.services;

import com.gabrieldemery.dbc.monitor.models.OutputFileModel;

public interface WriterService {
	
	void processFile(String fileName, OutputFileModel outputFile);

}
