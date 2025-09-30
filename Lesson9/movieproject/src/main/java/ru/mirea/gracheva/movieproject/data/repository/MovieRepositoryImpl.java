package ru.mirea.gracheva.movieproject.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import ru.mirea.gracheva.movieproject.domain.models.Movie;
import ru.mirea.gracheva.movieproject.domain.repository.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {

    private static final String PREFS_NAME = "movie_preferences";
    private static final String KEY_MOVIE_NAME = "movie_name";
    private SharedPreferences sharedPreferences;

    public  MovieRepositoryImpl(Context context){
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    @Override
    public boolean saveMovie(Movie movie) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_MOVIE_NAME, movie.getName());
        editor.apply();
        return true;
    }

    @Override
    public Movie getMovie() {
        String movieName = sharedPreferences.getString(KEY_MOVIE_NAME, "Вы не сохранили ни одного фильма");
        return new Movie(movieName);
    }
}
