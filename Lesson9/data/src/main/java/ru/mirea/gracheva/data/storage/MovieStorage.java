package ru.mirea.gracheva.data.storage;

import ru.mirea.gracheva.data.storage.models.MovieStorageData;

public interface MovieStorage {
    public MovieStorageData getMovie();
    public boolean saveMovie(MovieStorageData movieStorageData);
}
