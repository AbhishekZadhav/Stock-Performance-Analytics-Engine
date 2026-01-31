package com.example.StockPerformanceEngine.Engine.Service;

import com.example.StockPerformanceEngine.Engine.Config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import com.example.StockPerformanceEngine.Engine.util.DateConverter;

import javax.swing.*;
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
    private Map<Integer, float[]> days7Map;
    private Map<Integer, float[]> days14Map;
    private Map<Integer, float[]> days30Map;
    @Autowired
    public CSVReader(Config theConfig, StockIdentifier identifier){
        this.theConfig = theConfig;
        this.identifier  = identifier;
        this.days7Map = new HashMap<>();
        this.days14Map = new HashMap<>();
        this.days30Map = new HashMap<>();
    }

    public void readFiles(){
        try (BufferedReader br = Files.newBufferedReader(Path.of(this.theConfig.getFilesIds()))) {
            String line = br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] record = line.split(this.theConfig.getCsvDelimiter());

                int stockId = Integer.parseInt(record[0].trim());
                if(!this.days7Map.containsKey(stockId)){
                    this.days7Map.put(stockId, new float[5]);
                }
                if(!this.days14Map.containsKey(stockId)){
                    this.days14Map.put(stockId, new float[5]);
                }
                if(!this.days30Map.containsKey(stockId)){
                    this.days30Map.put(stockId, new float[5]);
                }
                makeArrEntry(7,stockId,record);
                makeArrEntry(14,stockId,record);
                makeArrEntry(30,stockId,record);
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to load stock identifier from "+this.theConfig.getFilesIds(), e
            );
        }
    }
    private void makeArrEntry(int days, int stockId, String[] record){
        Map<Integer, float[]> theMap;
        if(days==7) theMap = this.days7Map;
        if(days==14) theMap = this.days14Map;
        else theMap = this.days30Map;

        float[] arr = theMap.get(stockId);
        if(arr[0]==0){
            //startDate
            arr[1] = DateConverter.toInt(record[4].trim(), this.theConfig.getCsvDate());
            //startprice
            arr[3] = Float.parseFloat(record[3].trim());
        }
        if(++arr[0]==days){
            //endDate
            arr[2] = DateConverter.toInt(record[4].trim(), this.theConfig.getCsvDate());
            //endPrice
            arr[4] = Float.parseFloat(record[3].trim());
        }

    }

}
