package com.gabrieldemery.dbc.monitor.utils.parsers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabrieldemery.dbc.monitor.configs.exceptions.InvalidFileDataSizeException;
import com.gabrieldemery.dbc.monitor.models.SaleItemModel;
import com.gabrieldemery.dbc.monitor.models.SaleModel;
import com.gabrieldemery.dbc.monitor.models.enums.DataEnum;

@Component
public class SaleParser extends Parser {
    
    @Autowired
    SaleItemsParser saleItemsParser;
	
	public SaleModel parse(String line) throws Exception {

        if(!line.startsWith(DataEnum.SALE.getCode()))
            throw new Exception("The line must start with " + DataEnum.SALE.getCode());

        String[] data = line.split(this.getFileSeparatorData());

        if(data.length != 4){
            throw new InvalidFileDataSizeException("Sale data size must be 4");
        }

        List<SaleItemModel> itens = this.saleItemsParser.parse(data[2]);

        SaleModel sale = SaleModel.builder()
                .id(new Integer(data[1]))
                .itens(itens)
                .salesmanName(data[3])
                .build();

        sale.updateTotal();
        return sale;
		
	}

}
