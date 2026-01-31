package com.example.StockPerformanceEngine.Engine.Models;

public class StockWindow {
    private int counter;
    private int startDate;
    private int endDate;
    private float startPrice;
    private float endPrice;

    public StockWindow() {
        this.counter = 0;
    }

    // Getters and setters
    public int getCounter() { return counter; }
    public void incrementCounter() { this.counter++; }

    public int getStartDate() { return startDate; }
    public void setStartDate(int startDate) { this.startDate = startDate; }

    public int getEndDate() { return endDate; }
    public void setEndDate(int endDate) { this.endDate = endDate; }

    public float getStartPrice() { return startPrice; }
    public void setStartPrice(float startPrice) { this.startPrice = startPrice; }

    public float getEndPrice() { return endPrice; }
    public void setEndPrice(float endPrice) { this.endPrice = endPrice; }
}
