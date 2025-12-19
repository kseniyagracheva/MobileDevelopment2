package ru.mirea.gracheva.domain.models;

public class MetalPriceInfo {
    private String metalName;
    private double price;
    private String lastUpdated;
    private String imageUrl;

    public MetalPriceInfo(String metalName, double price, String lastUpdated, String imageUrl) {
        this.metalName = metalName;
        this.price = price;
        this.lastUpdated = lastUpdated;
        this.imageUrl = imageUrl;
    }

    public String getMetalName() { return metalName; }
    public double getPrice() { return price; }
    public String getLastUpdated() { return lastUpdated; }

    public String getImageUrl() {return imageUrl;}
}

