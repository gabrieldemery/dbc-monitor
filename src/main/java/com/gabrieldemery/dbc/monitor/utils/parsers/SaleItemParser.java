package com.gabrieldemery.dbc.monitor.utils.parsers;

import org.springframework.stereotype.Component;

import com.gabrieldemery.dbc.monitor.configs.exceptions.InvalidFileDataSizeException;
import com.gabrieldemery.dbc.monitor.models.SaleItemModel;
import com.gabrieldemery.dbc.monitor.utils.converters.BigDecimalConverter;

@Component
public class SaleItemParser extends Parser {
	
	public SaleItemModel parse(String line) throws InvalidFileDataSizeException {
        String[] data = line.split(this.getFileSeparatorSaleItemData());

        if(data.length != 3){
            throw new InvalidFileDataSizeException("Sale item data size must be 4");
        }

        return SaleItemModel.builder()
                .id(new Integer(data[0]))
                .quantity(BigDecimalConverter.convert(data[1]))
                .price(BigDecimalConverter.convert(data[2]))
                .build();
	}

}
