package com.gabrieldemery.dbc.monitor.services;

public interface MonitorService {
	
	/**
	 * Start monitoring the input folder.
	 */
	public void launchMonitoring();
	
	/**
	 * Stop monitoring the input folder.
	 */
	public void stopMonitoring();
	
}
