package com.example.StockPerformanceEngine.Engine.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class Config {

    // -------- files --------
    private String filesIds;
    private String filesPrices;
    private String filesOut;

    // -------- job --------
    private String jobIntervals;

    // -------- csv --------
    private String csvDelimiter;
    private String csvDate;

    // -------- getters & setters --------

    public String getFilesIds() {
        return filesIds;
    }

    public void setFilesIds(String filesIds) {
        this.filesIds = filesIds;
    }

    public String getFilesPrices() {
        return filesPrices;
    }

    public void setFilesPrices(String filesPrices) {
        this.filesPrices = filesPrices;
    }

    public String getFilesOut() {
        return filesOut;
    }

    public void setFilesOut(String filesOut) {
        this.filesOut = filesOut;
    }

    public String getJobIntervals() {
        return jobIntervals;
    }

    public void setJobIntervals(String jobIntervals) {
        this.jobIntervals = jobIntervals;
    }

    public String getCsvDelimiter() {
        return csvDelimiter;
    }

    public void setCsvDelimiter(String csvDelimiter) {
        this.csvDelimiter = csvDelimiter;
    }

    public String getCsvDate() {
        return csvDate;
    }

    public void setCsvDate(String csvDate) {
        this.csvDate = csvDate;
    }
}
