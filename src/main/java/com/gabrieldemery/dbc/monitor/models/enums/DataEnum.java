package com.gabrieldemery.dbc.monitor.models.enums;

public enum DataEnum {
	
	SALESMAN("001"),
	CUSTOMER("002"),
	SALE("003");

	private String code;
	
	DataEnum(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	
}
