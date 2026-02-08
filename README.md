# Stock Performance Analytics Engine

A Java-based application that calculates multi-period stock performance metrics (7-day, 14-day, and 30-day returns) from historical price data, accounting for trading days only (excluding weekends and holidays).

## Overview

This project processes historical stock price data and generates performance analytics for multiple securities (AAPL, GOOG, MSFT, NVDA, SPX). The engine:

- Reads stock prices from CSV files
- Calculates rolling period returns (7, 14, and 30-day)
- Accounts for trading days only (excludes weekends and holidays)
- Generates output CSV files with calculated metrics
- Sends results via email notification

## Project Structure

```
Stock-Performance-Analytics-Engine/
├── src/
│   ├── main/java/com/example/StockPerformanceEngine/
│   │   ├── Engine/
│   │   │   ├── EngineApplication.java          # Main application entry point
│   │   │   ├── Config/
│   │   │   │   └── Config.java                 # Application configuration
│   │   │   ├── Models/
│   │   │   │   └── StockWindow.java            # Data model for stock windows
│   │   │   ├── Runner/
│   │   │   │   └── JobRunner.java              # Job execution logic
│   │   │   ├── Service/
│   │   │   │   ├── CSVReader.java              # CSV parsing service
│   │   │   │   ├── CSVEntryHelper.java         # CSV data transformation
│   │   │   │   ├── StockIdentifier.java        # Stock identifier service
│   │   │   │   └── Emailing.java               # Email notification service
│   │   │   └── Utils/
│   │   │       └── DateConverter.java          # Date utility functions
│   ├── resources/
│   │   └── application.properties              # Configuration file
│   └── test/java/com/example/StockPerformanceEngine/
│       └── EngineApplicationTests.java         # Unit tests
├── input/
│   ├── stock_identifiers.csv                   # Stock symbols and metadata
│   └── stock_prices.csv                        # Historical price data (1 year)
├── pom.xml                                     # Maven build configuration
└── README.md                                   # This file
```

## Requirements

- Java 11 or higher
- Maven 3.6+
- Docker (optional, for containerized execution)

## Building the Project

### Using Maven

```bash
mvn clean package
```

This will:
1. Compile the Java source code
2. Run unit tests
3. Create an executable JAR file in the `target/` directory

## Running the Application

### From Command Line

```bash
java -jar target/StockPerformanceEngine-1.0.jar
```

### Using Maven

```bash
mvn spring-boot:run
```

## Input Files

Place the following CSV files in the `input/` directory:

### stock_identifiers.csv
Contains list of stock symbols and related identifiers to analyze.

### stock_prices.csv
Contains historical daily price data for all securities over a 1-year period.
- Includes trading days only (gaps indicate weekends/holidays)
- Format: Date, Symbol, Price

## Output Files

The application generates three output CSV files:

- **7day.csv** - 7-day rolling performance for each stock
- **14day.csv** - 14-day rolling performance for each stock
- **30day.csv** - 30-day rolling performance for each stock

Each output file contains:
- Date (trading day)
- Stock Symbol
- Performance percentage (return over the specified period)

## Configuration

Edit `src/main/resources/application.properties` to configure:

- Email settings (SMTP server, credentials, recipient)
- Input/output file paths
- Trading calendar preferences
- Performance calculation parameters

Example:
```properties
app.input.prices=input/stock_prices.csv
app.input.identifiers=input/stock_identifiers.csv
app.output.dir=output/

mail.enabled=true
mail.smtp.host=smtp.gmail.com
mail.smtp.port=587
mail.from=your-email@example.com
```

## Features

✓ **Multi-period Analysis** - Calculate 7, 14, and 30-day returns simultaneously  
✓ **Trading Day Accuracy** - Automatically excludes weekends and non-trading days  
✓ **Scalable** - Handles multiple securities efficiently  
✓ **Email Notifications** - Automatic report delivery upon completion  
✓ **Configurable** - Easy adjustment of parameters via properties file  

## Docker Support

Build and run the application in a Docker container:

```bash
docker build -t stock-analytics .
docker run stock-analytics
```

Or use docker-compose:

```bash
docker-compose up
```

## Technologies Used

- **Java 11+** - Core language
- **Spring Boot** - Application framework
- **Apache Commons CSV** - CSV parsing
- **JavaMail** - Email functionality
- **Maven** - Build management
- **Docker** - Containerization

## Testing

Run unit tests:

```bash
mvn test
```

## Key Classes

### EngineApplication
Main entry point that orchestrates the entire workflow.

### JobRunner
Coordinates CSV reading, performance calculations, and report generation.

### CSVReader
Handles parsing of input CSV files into data structures.

### StockWindow
Data model representing a window of stock price data for performance calculation.

### Emailing
Manages email composition and delivery of generated reports.

### DateConverter
Utility for converting dates and identifying trading days.

## Performance Calculation

The engine calculates returns using the formula:

$$\text{Return} = \frac{\text{Current Price} - \text{Price N days ago}}{\text{Price N days ago}} \times 100$$

Where N is the rolling period (7, 14, or 30 days), accounting only for trading days.

## Supported Securities

- AAPL (Apple)
- GOOG (Google)
- MSFT (Microsoft)
- NVDA (NVIDIA)
- SPX (S&P 500 Index)

## Troubleshooting

**Issue: "No trading data found for date"**
- Verify input CSV includes all required dates
- Check that date format matches expected format

**Issue: Email not sending**
- Verify SMTP credentials in application.properties
- Check firewall/network connectivity
- Enable "Less secure app access" if using Gmail

**Issue: Performance calculation inaccurate**
- Ensure stock_prices.csv contains consecutive trading days
- Verify no missing data points in input files

## Contact & Support

For questions or issues, please contact your Nasdaq point of contact.

## License

Proprietary - Nasdaq Assessment Project
