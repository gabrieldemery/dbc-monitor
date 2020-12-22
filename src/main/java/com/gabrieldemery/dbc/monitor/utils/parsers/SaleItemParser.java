package com.gabrieldemery.dbc.monitor.utils.parsers;

import org.springframework.beans.factory.annotation.Value;

import com.gabrieldemery.dbc.monitor.configs.exceptions.InvalidFileDataSizeException;
import com.gabrieldemery.dbc.monitor.models.SaleItemModel;
import com.gabrieldemery.dbc.monitor.utils.converters.BigDecimalConverter;

public class SaleItemParser {
	
	@Value("${file.separator.sale.item.data}")
    private static String FILE_SEPARATOR_SALE_ITEM_DATA;
	
	public static SaleItemModel parse(String line) throws InvalidFileDataSizeException {
        String[] data = line.split(FILE_SEPARATOR_SALE_ITEM_DATA);

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
