package com.gabrieldemery.dbc.monitor.utils.parsers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.gabrieldemery.dbc.monitor.configs.exceptions.InvalidFileDataSizeException;
import com.gabrieldemery.dbc.monitor.models.SaleItemModel;

public class SaleItemsParser {
	
	@Value("${file.separator.sale.items}")
    private static String FILE_SEPARATOR_SALE_ITEMS;

    public static List<SaleItemModel> parse(String line) throws InvalidFileDataSizeException {

        line = line.replace("[", "").replace("]", "");

        List<SaleItemModel> itens = new ArrayList<>();

        String[] data = line.split(FILE_SEPARATOR_SALE_ITEMS);

        for (String item : data){
            itens.add(SaleItemParser.parse(item));
        }

        return itens;
    }

}
