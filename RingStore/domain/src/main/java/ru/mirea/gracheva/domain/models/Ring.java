package ru.mirea.gracheva.domain.models;

public class Ring {
    private final String ringId;
    private final String metal;
    private final Integer price ;

    public Ring(String ringId, String metal, Integer price) {
        this.ringId = ringId;
        this.metal = metal;
        this.price = price;
    }

    public String getRingId() {
        return ringId;
    }

    public Integer getPrice() {
        return price;
    }

    public String getMetal() {
        return metal;
    }
}
