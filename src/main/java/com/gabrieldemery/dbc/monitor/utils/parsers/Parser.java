package com.gabrieldemery.dbc.monitor.utils.parsers;

import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

public abstract class Parser {

    @Value("${file.separator.data}")
    private String fileSeparatorData;

    @Value("${file.separator.sale.items}")
    private String fileSeparatorSaleItems;
	
    @Value("${file.separator.sale.item.data}")
    private String fileSeparatorSaleItemData;
    
    protected String getFileSeparatorData() {
    	return StringUtils.toEncodedString(this.fileSeparatorData.getBytes(Charset.forName("ISO-8859-1")), Charset.forName("UTF-8"));
	}
    
    protected String getFileSeparatorSaleItems() {
    	return StringUtils.toEncodedString(this.fileSeparatorSaleItems.getBytes(Charset.forName("ISO-8859-1")), Charset.forName("UTF-8"));
    }
    
    protected String getFileSeparatorSaleItemData() {
    	return StringUtils.toEncodedString(this.fileSeparatorSaleItemData.getBytes(Charset.forName("ISO-8859-1")), Charset.forName("UTF-8"));
    }

}
