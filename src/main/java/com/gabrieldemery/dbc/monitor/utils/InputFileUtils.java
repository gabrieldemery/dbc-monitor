package com.gabrieldemery.dbc.monitor.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gabrieldemery.dbc.monitor.models.OutputFileModel;
import com.gabrieldemery.dbc.monitor.models.enums.DataEnum;
import com.gabrieldemery.dbc.monitor.utils.parsers.CustomerParser;
import com.gabrieldemery.dbc.monitor.utils.parsers.SaleParser;
import com.gabrieldemery.dbc.monitor.utils.parsers.SalesmanParser;

@Component
public class InputFileUtils {
	
	private final Logger logger = LoggerFactory.getLogger(InputFileUtils.class);
	
	public OutputFileModel processData(List<String> lines) {
		
		OutputFileModel outputFile = new OutputFileModel();

        try {
            for (String line : lines) {

                if(line.startsWith(DataEnum.SALESMAN.getCode())){
                	outputFile.addSalesman(SalesmanParser.parse(line));
                } else if(line.startsWith(DataEnum.CUSTOMER.getCode())){
                	outputFile.addCustomer(CustomerParser.parse(line));
                } else if(line.startsWith(DataEnum.SALE.getCode())){
                	outputFile.addSale(SaleParser.parse(line));
                } else{
                    throw new Exception("Data type of this line is not valid: " + line);
                }
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
		
        return outputFile;
	}
	
}
