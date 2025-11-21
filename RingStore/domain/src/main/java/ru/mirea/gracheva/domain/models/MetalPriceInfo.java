package ru.mirea.gracheva.domain.models;

public class MetalPriceInfo {
    private String metalName;
    private double price;
    private String lastUpdated;

    public MetalPriceInfo(String metalName, double price, String lastUpdated) {
        this.metalName = metalName;
        this.price = price;
        this.lastUpdated = lastUpdated;
    }

    public String getMetalName() { return metalName; }
    public double getPrice() { return price; }
    public String getLastUpdated() { return lastUpdated; }
}

