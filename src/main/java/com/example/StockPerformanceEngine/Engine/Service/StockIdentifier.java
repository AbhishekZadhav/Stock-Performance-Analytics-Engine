package com.example.StockPerformanceEngine.Engine.Service;

import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;

import com.example.StockPerformanceEngine.Engine.Config.Config;

@Component("stockIdentifier")
@Lazy(false)
public class StockIdentifier {
    private Map<Integer, String[]> stockHash;
    private final Config theConfig;
    @Autowired
    public StockIdentifier(Config theConfig) {
        stockHash = new HashMap<>();
        this.theConfig = theConfig;
    }
    @PostConstruct
    public void debug() {
        System.out.println("FILES IDS (Config) = " + theConfig.getFiles().getIds());
    }
    @PostConstruct
    private void identifyStocks() {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(theConfig.getFiles().getIds()));
             CSVParser parser = CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withIgnoreSurroundingSpaces(true)
                     .withQuote('"')
                     .parse(reader)) {

            for (CSVRecord record : parser) {
                // Access by column index: 0=ID, 1=Name, 2=Symbol
                int stockId = Integer.parseInt(record.get(0).replaceAll(",", "").trim());
                String name = record.get(1).trim();
                String symbol = record.get(2).trim();

                stockHash.put(stockId, new String[]{name, symbol});
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to load stock identifier from " + theConfig.getFiles().getIds(), e
            );
        }
    }

    public String[] getCompanyInfo(int id){
        return stockHash.get(id);
    }

}
