package com.example.StockPerformanceEngine.Engine.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class Config {

    private Files files = new Files();
    private Job job = new Job();
    private Csv csv = new Csv();

    // ---------- getters ----------
    public Files getFiles() { return files; }
    public Job getJob() { return job; }
    public Csv getCsv() { return csv; }

    // ---------- nested classes ----------

    public static class Files {
        private String ids;
        private String prices;
        private String out;

        public String getIds() { return ids; }
        public void setIds(String ids) { this.ids = ids; }

        public String getPrices() { return prices; }
        public void setPrices(String prices) { this.prices = prices; }

        public String getOut() { return out; }
        public void setOut(String out) { this.out = out; }
    }

    public static class Job {
        private String intervals;

        public String getIntervals() { return intervals; }
        public void setIntervals(String intervals) { this.intervals = intervals; }
    }

    public static class Csv {
        private String delimiter;
        private String date;

        public String getDelimiter() { return delimiter; }
        public void setDelimiter(String delimiter) { this.delimiter = delimiter; }

        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
    }
}
