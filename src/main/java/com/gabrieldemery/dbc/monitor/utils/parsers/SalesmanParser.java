package com.gabrieldemery.dbc.monitor.utils.parsers;

import org.springframework.beans.factory.annotation.Value;

import com.gabrieldemery.dbc.monitor.configs.exceptions.InvalidFileDataSizeException;
import com.gabrieldemery.dbc.monitor.models.SalesmanModel;
import com.gabrieldemery.dbc.monitor.models.enums.DataEnum;
import com.gabrieldemery.dbc.monitor.utils.converters.BigDecimalConverter;

public class SalesmanParser {
	
	@Value("${file.separator.data}")
    private static String FILE_SEPARATOR_DATA;
	
	public static SalesmanModel parse(String line) {

        if(!line.startsWith(DataEnum.SALESMAN.getCode()))
            throw new Exception("The line must start with " + DataEnum.SALESMAN.getCode());

        String[] data = line.split(FILE_SEPARATOR_DATA);

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
