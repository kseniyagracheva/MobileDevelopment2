package ru.mirea.gracheva.domain.domain.models;

public class MetalPriceInfo {
    private String metalName;
    private double price;
    private String currency;
    private String lastUpdated;

    public MetalPriceInfo(String metalName, double price, String currency, String lastUpdated) {
        this.metalName = metalName;
        this.price = price;
        this.currency = currency;
        this.lastUpdated = lastUpdated;
    }

    public String getMetalName() { return metalName; }
    public double getPrice() { return price; }
    public String getCurrency() { return currency; }
    public String getLastUpdated() { return lastUpdated; }
}

