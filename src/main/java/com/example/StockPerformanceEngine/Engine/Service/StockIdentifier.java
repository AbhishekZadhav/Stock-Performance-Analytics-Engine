package com.example.StockPerformanceEngine.Engine.Service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

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
        identifyStocks();
    }
    @PostConstruct
    private void identifyStocks() {
        try (BufferedReader br = Files.newBufferedReader(Path.of(this.theConfig.getFilesIds()))) {
            String line = br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(this.theConfig.getCsvDelimiter());

                int stockId = Integer.parseInt(tokens[0].trim());
                String name = tokens[1].trim();
                String symbol = tokens[2].trim();
                this.stockHash.put(stockId, new String[]{name, symbol});
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to load stock identifier from "+this.theConfig.getFilesIds(), e
            );
        }
    }

    public String[] getCompanyInfo(int id){
        return stockHash.get(id);
    }

}
