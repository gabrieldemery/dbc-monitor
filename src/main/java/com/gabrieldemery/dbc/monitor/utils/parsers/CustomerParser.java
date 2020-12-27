package com.gabrieldemery.dbc.monitor.utils.parsers;

import org.springframework.stereotype.Component;

import com.gabrieldemery.dbc.monitor.configs.exceptions.InvalidFileDataSizeException;
import com.gabrieldemery.dbc.monitor.models.CustomerModel;
import com.gabrieldemery.dbc.monitor.models.enums.DataEnum;

@Component
public class CustomerParser extends Parser {

    public CustomerModel parse(String line) throws Exception {

        if(!line.startsWith(DataEnum.CUSTOMER.getCode()))
            throw new Exception("The line must start with " + DataEnum.CUSTOMER.getCode());

        String[] data = line.split(this.getFileSeparatorData());

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
