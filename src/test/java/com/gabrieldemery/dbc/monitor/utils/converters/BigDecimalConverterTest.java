package com.gabrieldemery.dbc.monitor.utils.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class BigDecimalConverterTest {

	@InjectMocks
	BigDecimalConverter bigDecimalConverter;
	
	@BeforeEach
	void init(){
	    MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test_convert_success() {
		BigDecimal bigDecimal = BigDecimalConverter.convert("2.50");
		
		assertEquals(bigDecimal, new BigDecimal("2.50"));
	}
		
}
