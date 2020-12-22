package com.gabrieldemery.dbc.monitor.utils.parsers;

import org.springframework.beans.factory.annotation.Value;

import com.gabrieldemery.dbc.monitor.configs.exceptions.InvalidFileDataSizeException;
import com.gabrieldemery.dbc.monitor.models.CustomerModel;
import com.gabrieldemery.dbc.monitor.models.enums.DataEnum;

public class CustomerParser {
	
	@Value("${file.separator.data}")
    private static String FILE_SEPARATOR_DATA;

    public static CustomerModel parse(String line) throws Exception {

        if(!line.startsWith(DataEnum.CUSTOMER.getCode()))
            throw new Exception("The line must start with " + DataEnum.CUSTOMER.getCode());

        String[] data = line.split(FILE_SEPARATOR_DATA);

        if(data.length != 4){
            throw new InvalidFileDataSizeException("Customer data size must be 4: " + line);
        }

        return CustomerModel.builder()
                .cnpj(data[1])
                .name(data[2])
                .businessArea(data[3])
                .build();
    }

}
