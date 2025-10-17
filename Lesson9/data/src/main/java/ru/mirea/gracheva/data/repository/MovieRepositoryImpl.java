package ru.mirea.gracheva.data.repository;

import java.time.LocalDate;

import ru.mirea.gracheva.data.storage.MovieStorage;
import ru.mirea.gracheva.data.storage.models.MovieStorageData;
import ru.mirea.gracheva.domain.models.MovieDomain;
import ru.mirea.gracheva.domain.repository.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {
    private MovieStorage movieStorage;

    public MovieRepositoryImpl(MovieStorage movieStorage){
        this.movieStorage=movieStorage;
    }

    @Override
    public boolean saveMovie(MovieDomain movieDomain){
        MovieStorageData movieStorageData = mapToStorage(movieDomain);
        return movieStorage.saveMovie(movieStorageData);
    }

    @Override
    public MovieDomain getMovie(){
        MovieStorageData movieStorageData = movieStorage.getMovie();
        return mapToDomain(movieStorageData);
    }

    private MovieStorageData mapToStorage(MovieDomain movieDomain){
        String name = movieDomain.getName();
        return new MovieStorageData(name, LocalDate.now().toString());
    }

    private MovieDomain mapToDomain(MovieStorageData movieStorageData){
        return new MovieDomain(movieStorageData.getName());
    }
}
