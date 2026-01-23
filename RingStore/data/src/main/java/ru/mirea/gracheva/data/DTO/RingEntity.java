package ru.mirea.gracheva.data.DTO;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rings")
public class RingEntity {

    @PrimaryKey
    @NonNull
    private String ringId;

    private String name;

    private String metal;

    private Integer price;

    private String imageUrl;

    public RingEntity(@NonNull String ringId, String name, String metal, Integer price, String imageUrl) {
        this.ringId = ringId;
        this.name = name;
        this.metal = metal;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    @NonNull
    public String getRingId() {
        return ringId;
    }

    public String getName() { return name; }

    public Integer getPrice() {
        return price;
    }

    public String getMetal() {
        return metal;
    }

    public String getImageUrl() { return imageUrl; }
}