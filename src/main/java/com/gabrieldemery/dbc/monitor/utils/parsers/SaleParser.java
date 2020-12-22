package com.gabrieldemery.dbc.monitor.utils.parsers;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.gabrieldemery.dbc.monitor.configs.exceptions.InvalidFileDataSizeException;
import com.gabrieldemery.dbc.monitor.models.SaleItemModel;
import com.gabrieldemery.dbc.monitor.models.SaleModel;
import com.gabrieldemery.dbc.monitor.models.enums.DataEnum;

public class SaleParser {
	
	@Value("${file.separator.data}")
    private static String FILE_SEPARATOR_DATA;
	
	public static SaleModel parse(String line) {

        if(!line.startsWith(DataEnum.SALE.getCode()))
            throw new Exception("The line must start with " + DataEnum.SALE.getCode());

        String[] data = line.split(FILE_SEPARATOR_DATA);

        if(data.length != 4){
            throw new InvalidFileDataSizeException("Sale data size must be 4");
        }

        List<SaleItemModel> itens = SaleItemsParser.parse(data[2]);

        SaleModel sale = SaleModel.builder()
                .id(new Integer(data[1]))
                .itens(itens)
                .salesmanName(data[3])
                .build();

        sale.updateTotal();
        return sale;
		
	}

}
