package com.example.StockPerformanceEngine.Engine.Runner;

import com.example.StockPerformanceEngine.Engine.Service.CSVReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobRunner implements CommandLineRunner {

    private final CSVReader csvReader;

    public JobRunner(CSVReader csvReader) {
        this.csvReader = csvReader;
    }

    @Override
    public void run(String... args) {
        csvReader.readFiles();
    }
}
