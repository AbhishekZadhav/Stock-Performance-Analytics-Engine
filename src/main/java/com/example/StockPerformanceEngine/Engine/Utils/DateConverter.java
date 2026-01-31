package com.example.StockPerformanceEngine.Engine.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter {

    public static int toInt(String dateString, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate date = LocalDate.parse(dateString, formatter);

            int year = date.getYear();
            int month = date.getMonthValue();
            int day = date.getDayOfMonth();

            return year * 10000 + month * 100 + day;
        } catch (Exception e) {
            System.err.println("ERROR parsing date: '" + dateString + "' with pattern: '" + pattern + "'");
            e.printStackTrace();
            throw new RuntimeException("Failed to parse date", e);
        }
    }

    public static String intToDateString(int dateInt) {
        if (dateInt == 0) {
            throw new IllegalArgumentException("Cannot convert date value 0");
        }

        int year = dateInt / 10000;
        int month = (dateInt % 10000) / 100;
        int day = dateInt % 100;

        LocalDate date = LocalDate.of(year, month, day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        return date.format(formatter);
    }
}