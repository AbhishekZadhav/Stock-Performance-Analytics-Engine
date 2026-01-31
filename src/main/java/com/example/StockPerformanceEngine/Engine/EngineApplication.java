package com.example.StockPerformanceEngine.Engine;

import com.example.StockPerformanceEngine.Engine.Config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan("com.example.StockPerformanceEngine.Engine.Config")
public class EngineApplication {

	public static void main(String[] args) {
		System.out.println("FILES IDS (raw env) = " +
				System.getProperty("files.ids"));
		SpringApplication.run(EngineApplication.class, args);
	}

}
