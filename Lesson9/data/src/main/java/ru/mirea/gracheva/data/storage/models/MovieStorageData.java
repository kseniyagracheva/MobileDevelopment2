package ru.mirea.gracheva.data.storage.models;

public class MovieStorageData {
    private String name;
    private String localDate;

    public MovieStorageData(String name, String localDate) {
        this.name = name;
        this.localDate = localDate;
    }

    public String getName() {
        return name;
    }

    public String getLocalDate() {
        return localDate;
    }
}
