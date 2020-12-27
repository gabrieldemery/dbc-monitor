package com.gabrieldemery.dbc.monitor.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabrieldemery.dbc.monitor.models.OutputFileModel;
import com.gabrieldemery.dbc.monitor.models.enums.DataEnum;
import com.gabrieldemery.dbc.monitor.utils.parsers.CustomerParser;
import com.gabrieldemery.dbc.monitor.utils.parsers.SaleParser;
import com.gabrieldemery.dbc.monitor.utils.parsers.SalesmanParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InputFileUtils {
	
	@Autowired
	CustomerParser customerParser;
	
	@Autowired
	SalesmanParser salesmanParser;

	@Autowired
	SaleParser saleParser;
	
	/**
	 * Process the input file data.
	 * @param lines (List<String>) List of data by line.
	 * @return (OutputFileModel) Data to be processed for output file.
	 */
	public OutputFileModel processData(List<String> lines) {
		
		OutputFileModel outputFile = new OutputFileModel();

        try {
            for (String line : lines) {

                if(line.startsWith(DataEnum.SALESMAN.getCode())){
                	outputFile.addSalesman(this.salesmanParser.parse(line));
                } else if(line.startsWith(DataEnum.CUSTOMER.getCode())){
                	outputFile.addCustomer(this.customerParser.parse(line));
                } else if(line.startsWith(DataEnum.SALE.getCode())){
                	outputFile.addSale(this.saleParser.parse(line));
                } else{
                    throw new Exception("Data type of this line is not valid: " + line);
                }
            }
        } catch (Exception e) {
            log.error("Error reading data from input file: {}", e.getMessage());
        }
		
        return outputFile;
	}
	
}
