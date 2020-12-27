package com.gabrieldemery.dbc.monitor.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gabrieldemery.dbc.monitor.models.CustomerModel;
import com.gabrieldemery.dbc.monitor.models.OutputFileModel;
import com.gabrieldemery.dbc.monitor.models.SaleItemModel;
import com.gabrieldemery.dbc.monitor.models.SaleModel;
import com.gabrieldemery.dbc.monitor.models.SalesmanModel;
import com.gabrieldemery.dbc.monitor.utils.converters.BigDecimalConverter;
import com.gabrieldemery.dbc.monitor.utils.parsers.CustomerParser;
import com.gabrieldemery.dbc.monitor.utils.parsers.SaleParser;
import com.gabrieldemery.dbc.monitor.utils.parsers.SalesmanParser;

public class InputFileUtilsTest {
	
	@Mock
	CustomerParser customerParser;
	
	@Mock
	SalesmanParser salesmanParser;

	@Mock
	SaleParser saleParser;
	
	@InjectMocks
	InputFileUtils inputFileUtils;
	
	@BeforeEach
	void init(){
	    MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test_processData_success_outputFile() throws Exception {
		
		List<String> lines = new ArrayList<String>();
		lines.add("001ç1234567891234çPedroç50000");
		lines.add("002ç2345675434544345çJose da SilvaçRural");
		lines.add("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro");
		
		SalesmanModel salesman = SalesmanModel.builder()
                .cpf("1234567891234")
                .name("Pedro")
                .salary(BigDecimalConverter.convert("50000"))
                .build();
		
		CustomerModel customer = CustomerModel.builder()
        .cnpj("2345675434544345")
        .name("Jose da Silva")
        .businessArea("Rural")
        .build();
		
		List<SaleItemModel> itens = new ArrayList<>();
		
		SaleItemModel saleItem1 = SaleItemModel.builder()
                .id(1)
                .quantity(BigDecimalConverter.convert("10"))
                .price(BigDecimalConverter.convert("100"))
                .build();
		itens.add(saleItem1);
		
		SaleItemModel saleItem2 = SaleItemModel.builder()
                .id(2)
                .quantity(BigDecimalConverter.convert("30"))
                .price(BigDecimalConverter.convert("2.50"))
                .build();
		itens.add(saleItem2);
		
		SaleItemModel saleItem3 = SaleItemModel.builder()
                .id(3)
                .quantity(BigDecimalConverter.convert("40"))
                .price(BigDecimalConverter.convert("3.10"))
                .build();
		itens.add(saleItem3);
		
		SaleModel sale = SaleModel.builder()
                .id(10)
                .itens(itens)
                .salesmanName("Pedro")
                .build();
        sale.updateTotal();

		when(this.salesmanParser.parse(ArgumentMatchers.anyString())).thenReturn(salesman);
		when(this.customerParser.parse(ArgumentMatchers.anyString())).thenReturn(customer);
		when(this.saleParser.parse(ArgumentMatchers.anyString())).thenReturn(sale);
		
		OutputFileModel outputFile = this.inputFileUtils.processData(lines);

		assertEquals(outputFile.getSalesmen().size(), 1);
		assertEquals(outputFile.getCustomers().size(), 1);
		assertEquals(outputFile.getSales().size(), 1);
	}
	
	@Test
	public void test_processData_error_startsWithUnknownValue() throws Exception {
		
		List<String> lines = new ArrayList<String>();
		lines.add("004ç1234567891234çPedroç50000");
		
		OutputFileModel outputFile = this.inputFileUtils.processData(lines);

		assertEquals(outputFile.getSalesmen().size(), 0);
		assertEquals(outputFile.getCustomers().size(), 0);
		assertEquals(outputFile.getSales().size(), 0);
	}
	
}
