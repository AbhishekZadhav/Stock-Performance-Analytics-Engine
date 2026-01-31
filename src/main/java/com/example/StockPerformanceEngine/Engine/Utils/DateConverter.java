package com.example.StockPerformanceEngine.Engine.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateConverter {

//    private static final DateTimeFormatter INPUT_FORMAT =
//            DateTimeFormatter.ofPattern("dd-MM-yyyy");
    // -------- dd-MM-yyyy -> yyyyMMdd (int) --------
    public static int toInt(String date, String inputFormat) {
        LocalDate d = LocalDate.parse(date, DateTimeFormatter.ofPattern(inputFormat));
        return d.getYear() * 10000
                + d.getMonthValue() * 100
                + d.getDayOfMonth();
    }

    // -------- yyyyMMdd (int) -> formatted date --------
    public static String fromInt(int yyyyMMdd, String outputPattern) {
        int year  = yyyyMMdd / 10000;
        int month = (yyyyMMdd % 10000) / 100;
        int day   = yyyyMMdd % 100;

        LocalDate date = LocalDate.of(year, month, day);
        return date.format(DateTimeFormatter.ofPattern(outputPattern));
    }
}
