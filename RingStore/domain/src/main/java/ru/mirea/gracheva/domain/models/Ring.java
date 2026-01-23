package ru.mirea.gracheva.domain.models;

public class Ring {
    private final String ringId;
    private final String name;
    private final String metal;
    private final Integer price ;
    private final String imageUrl;

    public Ring(String ringId, String name, String metal, Integer price, String imageUrl) {
        this.ringId = ringId;
        this.name = name;
        this.metal = metal;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getRingId() {
        return ringId;
    }

    public String getName() { return name; }

    public Integer getPrice() {
        return price;
    }

    public String getMetal() { return metal; }

    public String getImageUrl() { return imageUrl; }
}
