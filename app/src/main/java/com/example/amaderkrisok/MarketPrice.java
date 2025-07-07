package com.example.amaderkrisok;

public class MarketPrice {
    private String productName;
    private String price;
    private String change;
    private String date;
    private long timestamp;

    // Empty constructor needed for Firebase
    public MarketPrice() {}

    // Constructor
    public MarketPrice(String productName, String price, String change, String date, long timestamp) {
        this.productName = productName;
        this.price = price;
        this.change = change;
        this.date = date;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getProductName() { return productName; }
    public String getPrice() { return price; }
    public String getChange() { return change; }
    public String getDate() { return date; }
    public long getTimestamp() { return timestamp; }

    public void setProductName(String productName) { this.productName = productName; }
    public void setPrice(String price) { this.price = price; }
    public void setChange(String change) { this.change = change; }
    public void setDate(String date) { this.date = date; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}