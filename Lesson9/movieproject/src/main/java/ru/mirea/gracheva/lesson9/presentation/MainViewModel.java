package ru.mirea.gracheva.lesson9.presentation;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
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
    private MutableLiveData<String> favoriteMovie = new MutableLiveData<>();
    public MutableLiveData<String> getFavoriteMovie() {
        return favoriteMovie;
    }

    public MainViewModel(MovieRepository movieRepository) {
        Log.d(MainViewModel.class.getSimpleName().toString(), "MainViewModel created");
        this.movieRepository = movieRepository;
    }

    public Boolean saveMovie(String movieName) {
        SaveMovieToFavoriteUseCase saveUseCase = new SaveMovieToFavoriteUseCase(movieRepository);
        Boolean result = saveUseCase.execute(new MovieDomain(movieName));
        return result;
    }

    public void getMovie() {
        GetFavoriteMovieUseCase getUseCase = new GetFavoriteMovieUseCase(movieRepository);
        MovieDomain movie = getUseCase.execute();
        favoriteMovie.setValue(String.format("Мой любимый фильм: %s", movie.getName()));
    }

    @Override
    protected void onCleared() {
        Log.d(MainViewModel.class.getSimpleName(), "MainViewModel cleared");
        super.onCleared();
    }
}