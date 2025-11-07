package ru.mirea.gracheva.lesson9.presentation;

import android.util.Log;

import androidx.lifecycle.ViewModel;


import ru.mirea.gracheva.data.storage.MovieStorage;
import ru.mirea.gracheva.data.storage.sharedPrefs.SharedPrefMovieStorage;
import ru.mirea.gracheva.data.repository.MovieRepositoryImpl;
import ru.mirea.gracheva.domain.models.MovieDomain;
import ru.mirea.gracheva.domain.repository.MovieRepository;
import ru.mirea.gracheva.domain.usecases.GetFavoriteMovieUseCase;
import ru.mirea.gracheva.domain.usecases.SaveMovieToFavoriteUseCase;

public class MainViewModel extends ViewModel {

    private final MovieRepository movieRepository;

    public MainViewModel() {
        MovieStorage sharedPrefMovieStorage = new SharedPrefMovieStorage(App.getContext());
        movieRepository = new MovieRepositoryImpl(sharedPrefMovieStorage);

        Log.d(MainViewModel.class.getSimpleName(), "MainViewModel created");
    }

    public Boolean saveFavoriteMovie(String movieName) {
        SaveMovieToFavoriteUseCase saveUseCase = new SaveMovieToFavoriteUseCase(movieRepository);
        return saveUseCase.execute(new MovieDomain(movieName));
    }

    public String getFavoriteMovie() {
        GetFavoriteMovieUseCase getUseCase = new GetFavoriteMovieUseCase(movieRepository);
        MovieDomain movie = getUseCase.execute();
        return movie.getName();
    }

    @Override
    protected void onCleared() {
        Log.d(MainViewModel.class.getSimpleName(), "MainViewModel cleared");
        super.onCleared();
    }
}