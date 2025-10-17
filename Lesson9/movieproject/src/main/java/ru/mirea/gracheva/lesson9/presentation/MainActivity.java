package ru.mirea.gracheva.lesson9.presentation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.gracheva.data.storage.MovieStorage;
import ru.mirea.gracheva.data.storage.sharedPrefs.SharedPrefMovieStorage;
import ru.mirea.gracheva.lesson9.R;
import ru.mirea.gracheva.data.repository.MovieRepositoryImpl;
import ru.mirea.gracheva.lesson9.databinding.ActivityMainBinding;
import ru.mirea.gracheva.domain.models.MovieDomain;
import ru.mirea.gracheva.domain.repository.MovieRepository;
import ru.mirea.gracheva.domain.usecases.GetFavoriteMovieUseCase;
import ru.mirea.gracheva.domain.usecases.SaveMovieToFavoriteUseCase;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MovieStorage sharedPrefMovieStorage = new SharedPrefMovieStorage(this);
        MovieRepository movieRepository = new MovieRepositoryImpl(sharedPrefMovieStorage);

        binding.saveFavMovieButton.setOnClickListener(view -> {
            Boolean result = new SaveMovieToFavoriteUseCase(movieRepository).execute(new MovieDomain(binding.enterFavMovieText.getText().toString()));
            binding.noDataText.setText(String.format("Вы сохранили %s", result));
        });

        binding.getFavMovieButton.setOnClickListener(view -> {
            MovieDomain movieDomain = new GetFavoriteMovieUseCase(movieRepository).execute();
            binding.noDataText.setText(String.format("Ваш любимый фильм: %s", movieDomain.getName()));
        });
    }
}