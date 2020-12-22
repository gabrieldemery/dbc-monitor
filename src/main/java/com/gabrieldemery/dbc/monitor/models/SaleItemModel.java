package com.gabrieldemery.dbc.monitor.models;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleItemModel {

    private Integer id;

    private BigDecimal quantity;

    private BigDecimal price;

}
