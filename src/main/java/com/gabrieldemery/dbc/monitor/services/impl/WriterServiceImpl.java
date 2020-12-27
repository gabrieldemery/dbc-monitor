package com.gabrieldemery.dbc.monitor.services.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gabrieldemery.dbc.monitor.models.OutputFileModel;
import com.gabrieldemery.dbc.monitor.models.SaleModel;
import com.gabrieldemery.dbc.monitor.models.SalesmanModel;
import com.gabrieldemery.dbc.monitor.services.WriterService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WriterServiceImpl implements WriterService {
	
	@Value("${folder.output}")
    private String folderOutputPath;
	
	@Override
	public void processFile(String fileName, OutputFileModel outputFile) {
		
		this.checkFolderOutput();
		
		try {
            String pathWithFile = this.getPathOutput().concat("\\").concat(fileName.toLowerCase().replace(".dat", ".done.dat"));

            BufferedWriter writer = new BufferedWriter(new FileWriter(pathWithFile));

            if (Objects.nonNull(outputFile.getCustomers()) && !outputFile.getCustomers().isEmpty()) {
            	writer.write(String.format("Quantidade de clientes no arquivo de entrada: %s\r\n", outputFile.getCustomers().size()));
            }
            
            if (Objects.nonNull(outputFile.getSalesmen()) && !outputFile.getSalesmen().isEmpty()) {
            	writer.write(String.format("Quantidade de vendedores no arquivo de entrada: %s\r\n", outputFile.getSalesmen().size()));
            }

            SaleModel greaterSale = outputFile.getGreaterSale();
            writer.write(String.format("ID da venda mais cara: %s\r\n", greaterSale != null ? greaterSale.getId() : "N/A"));

            SalesmanModel worstSalesman = outputFile.getWorstSalesman();
            writer.write(String.format("O pior vendedor: %s\r\n", worstSalesman != null ? worstSalesman.getName() : "N/A"));
            
            writer.close();
        } catch (IOException e) {
            log.error("Error writing file: {}", e.getMessage());
        }
	}
	
	/**
	 * Check if the output folder exists. If not, it will create.
	 */
	private void checkFolderOutput() {
		
		try {
	        File directoryOutput = new File(this.getPathOutput());
	        if (!directoryOutput.exists()) {
	        	directoryOutput.mkdir();
	        }
		} catch (Exception e) {
			log.error("Failed to create the output folder: {}", e.getMessage());
		}
	}
    
    /**
     * Full path of the output folder.
     * @return (String) Output folder path.
     */
	private String getPathOutput() {
		return System.getProperty("user.home").concat("\\").concat(this.folderOutputPath);
	}
	
}
