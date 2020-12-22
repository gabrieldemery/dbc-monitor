package com.gabrieldemery.dbc.monitor.utils.converters;

import java.math.BigDecimal;

public class BigDecimalConverter {

    public static BigDecimal convert(String value) {
        return new BigDecimal(value);
    }
}
