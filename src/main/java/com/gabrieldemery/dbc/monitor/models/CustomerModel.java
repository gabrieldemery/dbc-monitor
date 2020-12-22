package com.gabrieldemery.dbc.monitor.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerModel {

    private String cnpj;

    private String name;

    private String businessArea;

}
