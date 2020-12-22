package com.gabrieldemery.dbc.monitor.services;

import org.springframework.stereotype.Service;

import com.gabrieldemery.dbc.monitor.models.OutputFileModel;

@Service
public interface ReaderService {
	
	OutputFileModel processFile(String nameFile);
	
}
