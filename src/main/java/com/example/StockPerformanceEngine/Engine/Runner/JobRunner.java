package com.example.StockPerformanceEngine.Engine.Runner;

import com.example.StockPerformanceEngine.Engine.Config.Config;
import com.example.StockPerformanceEngine.Engine.Service.CSVReader;
import com.example.StockPerformanceEngine.Engine.Service.Emailing;
import jakarta.mail.MessagingException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobRunner implements CommandLineRunner {

    private final CSVReader csvReader;
    private final Config theConfig;
    private final Emailing emailingService;

    public JobRunner(CSVReader csvReader, Config theConfig, Emailing emailingService) {
        this.csvReader = csvReader;
        this.theConfig = theConfig;
        this.emailingService = emailingService;
    }

    @Override
    public void run(String... args) {
        csvReader.readFiles();
        try {
            String sevenDay = "7day.csv";
            String fourteenDay = "14day.csv";
            String thirtyDay = "30day.csv";
            this.emailingService.sendMailWithAttachment(this.theConfig.getEmail().getTo(), this.theConfig.getEmail().getSubject(), this.theConfig.getEmail().getBody(), this.theConfig.getFiles().getOut()+"/"+sevenDay);
            this.emailingService.sendMailWithAttachment(this.theConfig.getEmail().getTo(), this.theConfig.getEmail().getSubject(), this.theConfig.getEmail().getBody(), this.theConfig.getFiles().getOut()+"/"+fourteenDay);
            this.emailingService.sendMailWithAttachment(this.theConfig.getEmail().getTo(), this.theConfig.getEmail().getSubject(), this.theConfig.getEmail().getBody(), this.theConfig.getFiles().getOut()+"/"+thirtyDay);

        }
        catch (Exception e){
            System.out.println(e);
        }
        }
}
