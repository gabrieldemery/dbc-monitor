package com.gabrieldemery.dbc.monitor.models;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SalesmanModel {

    private String cpf;

    private String name;

    private BigDecimal salary;

    private BigDecimal salesAmount;

    public SalesmanModel() {
        this.salesAmount = new BigDecimal(0);
    }

	public BigDecimal getSalesAmount(){
        if(this.salesAmount == null)
        	this.salesAmount = new BigDecimal(0);
        return this.salesAmount;
    }

    public void getSalesAmount(BigDecimal value) {
    	this.salesAmount = value;
    }

    public void addSaleAmount(BigDecimal value) {
        if(this.salesAmount == null)
        	this.salesAmount = new BigDecimal(0);
        this.salesAmount = this.salesAmount.add(value);
    }

}
