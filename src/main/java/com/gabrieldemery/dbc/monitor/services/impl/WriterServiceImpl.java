package com.gabrieldemery.dbc.monitor.services.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gabrieldemery.dbc.monitor.models.OutputFileModel;
import com.gabrieldemery.dbc.monitor.models.SaleModel;
import com.gabrieldemery.dbc.monitor.models.SalesmanModel;
import com.gabrieldemery.dbc.monitor.services.WriterService;

@Service
public class WriterServiceImpl implements WriterService {
	
	private final Logger logger = LoggerFactory.getLogger(WriterServiceImpl.class);
	
	@Value("${folder.output}")
    private String folderOutputPath;
	
	@Override
	public void processFile(String fileName, OutputFileModel outputFile) {
		
		try {
            String pathWithFile = this.folderOutputPath.concat("\\").concat(fileName.toLowerCase().replace(".dat", ".done.dat"));

            BufferedWriter writer = new BufferedWriter(new FileWriter(pathWithFile));

            writer.write(String.format("Quantidade de clientes no arquivo de entrada: %s\r\n", outputFile.getCustomers().size()));

            writer.write(String.format("Quantidade de vendedores no arquivo de entrada: %s\r\n", outputFile.getSalesmen().size()));

            SaleModel greaterSale = outputFile.getGreaterSale();
            writer.write(String.format("ID da venda mais cara: %s\r\n", greaterSale != null ? greaterSale.getId() : "N/A"));

            SalesmanModel worstSalesman = outputFile.getWorstSalesman();
            writer.write(String.format("O pior vendedor: %s\r\n", worstSalesman != null ? worstSalesman.getName() : "N/A"));
            
            writer.close();
        } catch (IOException e) {
            this.logger.error("Erro ao escrever arquivo: " + e.getMessage());
        }
	}
	
}
