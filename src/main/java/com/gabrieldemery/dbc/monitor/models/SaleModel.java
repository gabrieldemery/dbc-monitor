package com.gabrieldemery.dbc.monitor.models;

import java.math.BigDecimal;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
public class SaleModel {

    private Integer id;

    private List<SaleItemModel> itens;

    private SalesmanModel salesman;

    private String salesmanName;
    
    @Setter(value = AccessLevel.PRIVATE)
    private BigDecimal total;
	
	public void updateTotal() {
        this.setTotal(new BigDecimal(0));

        BigDecimal sum = new BigDecimal(0);
        for (SaleItemModel item : itens) {
            sum = sum.add(item.getPrice().multiply(item.getQuantity()));
        }

        this.setTotal(sum);
    }

}
