package com.gabrieldemery.dbc.monitor.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.gabrieldemery.dbc.monitor.configs.exceptions.FileDataException;

public class OutputFileModel {
	
    private List<SalesmanModel> salesmen;
    private List<CustomerModel> customers;
    private List<SaleModel> sales;

    public OutputFileModel() {
        this.salesmen = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.sales = new ArrayList<>();
    }

    public List<SalesmanModel> getSalesmen() {
        return this.salesmen;
    }

    public void addSalesman(SalesmanModel salesman) {

        for (SalesmanModel man : this.salesmen) {
            if (man.equals(salesman))
                return;
        }

        this.salesmen.add(salesman);

        SaleModel sale = this.sales
                .stream()
                .filter((sl) ->
                        sl.getSalesman() == null && sl.getSalesmanName().equals(salesman.getName()))
                .findFirst()
                .orElse(null);

        if (sale != null) {
            sale.setSalesman(salesman);
            sale.updateTotal();
            salesman.addSaleAmount(sale.getTotal());
        }

    }

    public List<CustomerModel> getCustomers() {
        return this.customers;
    }

    public void addCustomer(CustomerModel customer) {

        for (CustomerModel currentCustomer : this.customers) {
            if (currentCustomer.equals(customer))
                return;
        }

        this.customers.add(customer);
    }

    public List<SaleModel> getSales() {
        return this.sales;
    }

    public void addSale(SaleModel sale) throws FileDataException {
        if (sale.getSalesman() != null) {
            sale.updateTotal();
            sale.getSalesman().addSaleAmount(sale.getTotal());
            sale.setSalesmanName(sale.getSalesman().getName());
        } else if (sale.getSalesmanName() != null && !sale.getSalesmanName().isEmpty()) {

            SalesmanModel salesman = this.salesmen
                    .stream()
                    .filter((cur) ->
                            cur.getName().equals(sale.getSalesmanName()))
                    .findFirst()
                    .orElse(null);

            if (salesman != null) {
                sale.setSalesman(salesman);
                sale.updateTotal();
                salesman.addSaleAmount(sale.getTotal());
            }

        } else {
            throw new FileDataException("There is no information about salesman in this sale");
        }

        sale.updateTotal();
        this.sales.add(sale);
    }

    public int getCustomersSize(){
        return this.customers.size();
    }

    public int getSalesmanSize(){
        return this.salesmen.size();
    }

    public SaleModel getGreaterSale(){
        SaleModel sale = this.sales
                .stream()
                .sorted(Comparator.comparing(SaleModel::getTotal).reversed())
                .findFirst().orElse(null);

        return sale;
    }

    public SalesmanModel getWorstSalesman(){

        SalesmanModel salesman = this.salesmen
                .stream()
                .sorted(Comparator.comparing(SalesmanModel::getSalesAmount))
                .findFirst().orElse(null);

        return salesman;

    }

}
