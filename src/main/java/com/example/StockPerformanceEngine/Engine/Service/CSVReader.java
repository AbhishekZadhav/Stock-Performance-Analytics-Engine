package com.example.StockPerformanceEngine.Engine.Service;

import com.example.StockPerformanceEngine.Engine.Config.Config;
import com.example.StockPerformanceEngine.Engine.Models.StockWindow;
import com.example.StockPerformanceEngine.Engine.Utils.DateConverter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Component
@DependsOn("stockIdentifier")
@Lazy(false)
public class CSVReader {
    private final Config theConfig;
    private final StockIdentifier identifier;
    private Map<Integer, StockWindow> days7Map;
    private Map<Integer, StockWindow> days14Map;
    private Map<Integer, StockWindow> days30Map;
    private final CSVEntryHelper entryHelper;

    @Autowired
    public CSVReader(Config theConfig, StockIdentifier identifier, CSVEntryHelper entryHelper){
        this.theConfig = theConfig;
        this.identifier = identifier;
        this.days7Map = new HashMap<>();
        this.days14Map = new HashMap<>();
        this.days30Map = new HashMap<>();
        this.entryHelper = entryHelper;
    }

    public void readFiles(){
        try (BufferedReader reader = Files.newBufferedReader(Path.of(this.theConfig.getFiles().getPrices()));
             CSVParser parser = CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withIgnoreSurroundingSpaces(true)
                     .withQuote('"')
                     .parse(reader)) {

            for (CSVRecord record : parser) {
                int stockId = Integer.parseInt(record.get(0).replaceAll(",", "").trim());

                days7Map.putIfAbsent(stockId, new StockWindow());
                days14Map.putIfAbsent(stockId, new StockWindow());
                days30Map.putIfAbsent(stockId, new StockWindow());

                makeArrEntry(7, stockId, record);
                makeArrEntry(14, stockId, record);
                makeArrEntry(30, stockId, record);
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to load stock prices from " + this.theConfig.getFiles().getPrices(), e
            );
        }
    }

    private void makeArrEntry(int days, int stockId, CSVRecord record){
        Map<Integer, StockWindow> theMap;
        if(days == 7) theMap = this.days7Map;
        else if(days == 14) theMap = this.days14Map;
        else theMap = this.days30Map;

        StockWindow window = theMap.get(stockId);

        if(window.getCounter() == 0){
            window.setStartDate(DateConverter.toInt(record.get(4).trim(), this.theConfig.getCsv().getDate()));
            window.setStartPrice(Float.parseFloat(record.get(3).replaceAll(",", "").trim()));
        }

        window.incrementCounter();

        if(window.getCounter() == days){
            window.setEndDate(DateConverter.toInt(record.get(4).trim(), this.theConfig.getCsv().getDate()));
            window.setEndPrice(Float.parseFloat(record.get(3).replaceAll(",", "").trim()));

            this.entryHelper.writeEntry(days, stockId,
                    window.getStartDate(), window.getEndDate(),
                    window.getStartPrice(), window.getEndPrice());
            theMap.remove(stockId);
        }
    }
}