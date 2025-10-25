package ru.mirea.gracheva.data.DTO;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rings")
public class RingEntity {

    @PrimaryKey
    @NonNull
    private String ringId;

    private String metal;

    private Integer price;

    public RingEntity(@NonNull String ringId, String metal, Integer price) {
        this.ringId = ringId;
        this.metal = metal;
        this.price = price;
    }

    @NonNull
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