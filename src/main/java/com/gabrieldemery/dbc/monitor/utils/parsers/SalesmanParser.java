package com.gabrieldemery.dbc.monitor.utils.parsers;

import org.springframework.stereotype.Component;

import com.gabrieldemery.dbc.monitor.configs.exceptions.InvalidFileDataSizeException;
import com.gabrieldemery.dbc.monitor.models.SalesmanModel;
import com.gabrieldemery.dbc.monitor.models.enums.DataEnum;
import com.gabrieldemery.dbc.monitor.utils.converters.BigDecimalConverter;

@Component
public class SalesmanParser extends Parser {
	
	public SalesmanModel parse(String line) throws Exception {

        if(!line.startsWith(DataEnum.SALESMAN.getCode()))
            throw new Exception("The line must start with " + DataEnum.SALESMAN.getCode());

        String[] data = line.split(this.getFileSeparatorData());

        if(data.length != 4){
            throw new InvalidFileDataSizeException("Salesman data size must be 4");
        }

        return SalesmanModel.builder()
                .cpf(data[1])
                .name(data[2])
                .salary(BigDecimalConverter.convert(data[3]))
                .build();
	}

}