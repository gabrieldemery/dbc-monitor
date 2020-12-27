package com.gabrieldemery.dbc.monitor.utils.parsers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabrieldemery.dbc.monitor.configs.exceptions.InvalidFileDataSizeException;
import com.gabrieldemery.dbc.monitor.models.SaleItemModel;

@Component
public class SaleItemsParser extends Parser {
    
    @Autowired
    SaleItemParser saleItemParser;

    public List<SaleItemModel> parse(String line) throws InvalidFileDataSizeException {

        line = line.replace("[", "").replace("]", "");

        List<SaleItemModel> itens = new ArrayList<>();

        String[] data = line.split(this.getFileSeparatorSaleItems());

        for (String item : data){
            itens.add(this.saleItemParser.parse(item));
        }

        return itens;
    }

}
