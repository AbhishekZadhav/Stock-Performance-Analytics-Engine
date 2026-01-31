package com.example.StockPerformanceEngine.Engine.Service;

import com.example.StockPerformanceEngine.Engine.Config.Config;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import  com.example.StockPerformanceEngine.Engine.Utils.DateConverter;

@Component
public class CSVEntryHelper {

    private final Config config;

    public CSVEntryHelper(Config config) {
        this.config = config;
        initializeFiles();
    }

    // -------- Create files once at startup --------
    private void initializeFiles() {
        try {
            createFileIfMissing(7);
            createFileIfMissing(14);
            createFileIfMissing(30);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize CSV output files", e);
        }
    }

    private void createFileIfMissing(int days) throws Exception {
        Path filePath = Path.of(
                config.getFiles().getOut(),  // Changed from getIds() to getOut()
                days + "day.csv"
        );

        // Ensure output directory exists
        Files.createDirectories(filePath.getParent());

        // Create file if it doesn't exist
        if (Files.notExists(filePath)) {
            try (BufferedWriter writer = Files.newBufferedWriter(
                    filePath,
                    StandardOpenOption.CREATE
            )) {
                // Optional header
                writer.write("stockId,startDate,endDate,startPrice,endPrice,performance");
                writer.newLine();
            }
        }
    }

    // -------- Append one entry --------
    public void writeEntry(
            int days,
            int stockId,
            int startDate,
            int endDate,
            float startPrice,
            float endPrice
    ) {

        // Debug logging
        System.out.println("DEBUG writeEntry - stockId=" + stockId + ", days=" + days +
                ", startDate=" + startDate + ", endDate=" + endDate);

        if (startDate == 0 || endDate == 0) {
            throw new IllegalArgumentException(
                    "Date values cannot be 0! stockId=" + stockId +
                            ", startDate=" + startDate + ", endDate=" + endDate
            );
        }

        Path filePath = Path.of(
                config.getFiles().getOut(),
                days + "day.csv"
        );

        try (BufferedWriter writer = Files.newBufferedWriter(
                filePath,
                StandardOpenOption.APPEND
        )) {
            float performance = ((endPrice-startPrice)*100)/startPrice;
            writer.write(
                    stockId + "," +
                            DateConverter.intToDateString(startDate) + "," +
                            DateConverter.intToDateString(endDate) + "," +
                            startPrice + "," +
                            endPrice + "," +
                            performance
            );
            writer.newLine();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to write CSV entry for " + days + "day file",
                    e
            );
        }
    }
}
