package com.gabrieldemery.dbc.monitor.utils.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gabrieldemery.dbc.monitor.models.CustomerModel;

public class CustomerParserTest {
	
	@Mock
	Parser parser;
	
	@InjectMocks
	CustomerParser customerParser;
	
	@BeforeEach
	void init(){
	    MockitoAnnotations.initMocks(this);
	}
	
	@Disabled
	@Test
	public void test_parse_success() throws Exception {
		
		String line = "002ç2345675434544345çJose da SilvaçRural";
		
		String[] data = {"2345675434544345", "Jose da Silva", "Rural"};
		
		when(line.split(ArgumentMatchers.any())).thenReturn(data);
		
		CustomerModel customer = this.customerParser.parse(line);
		
		assertEquals(customer.getCnpj(), "2345675434544345");
		assertEquals(customer.getName(), "Jose da Silva");
		assertEquals(customer.getBusinessArea(), "Rural");
	}
	
	@Test
	public void test_parse_error_startsWithUnknownValue() throws Exception {
		
		String line = "004ç2345675434544345çJose da SilvaçRural";
		
		assertThrows(Exception.class, () -> {
			this.customerParser.parse(line);
		});
	}
	
	@Test
	public void test_parse_error_notDividedIntoExpectedParts() throws Exception {
		
		String line = "002ç2345675434544345çJose da Silva";
		
		assertThrows(Exception.class, () -> {
			this.customerParser.parse(line);
		});
	}
	
}
