package ru.mirea.gracheva.data.storage.sharedPrefs;

import android.content.Context;
import android.content.SharedPreferences;

import java.time.LocalDate;

import ru.mirea.gracheva.data.storage.models.MovieStorageData;

public class SharedPrefMovieStorage implements ru.mirea.gracheva.data.storage.MovieStorage {
    private static final String PREFS_NAME = "movie_preferences";
    private static final String KEY_MOVIE_NAME = "movie_name";
    private static final String DATA_KEY = "movie_data";
    private SharedPreferences sharedPreferences;
    private Context context;
    public SharedPrefMovieStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean saveMovie(MovieStorageData movieStorageData) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_MOVIE_NAME, movieStorageData.getName());
        editor.putString(DATA_KEY, String.valueOf(LocalDate.now()));
        editor.apply();
        return true;
    }

    @Override
    public MovieStorageData getMovie() {
        String movieName = sharedPreferences.getString(KEY_MOVIE_NAME, "Вы не сохранили ни одного фильма");
        String movieDate = sharedPreferences.getString(DATA_KEY,String.valueOf(LocalDate.now()));
        return new MovieStorageData(movieName, movieDate);
    }
}
