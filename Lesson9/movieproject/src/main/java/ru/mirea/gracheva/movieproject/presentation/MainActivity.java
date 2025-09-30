package ru.mirea.gracheva.movieproject.presentation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.gracheva.movieproject.R;
import ru.mirea.gracheva.movieproject.data.repository.MovieRepositoryImpl;
import ru.mirea.gracheva.movieproject.databinding.ActivityMainBinding;
import ru.mirea.gracheva.movieproject.domain.models.Movie;
import ru.mirea.gracheva.movieproject.domain.repository.MovieRepository;
import ru.mirea.gracheva.movieproject.domain.usecases.GetFavoriteMovieUseCase;
import ru.mirea.gracheva.movieproject.domain.usecases.SaveMovieToFavoriteUseCase;

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
        MovieRepository movieRepository = new MovieRepositoryImpl(this);

        binding.saveFavMovieButton.setOnClickListener(view -> {
            Boolean result = new SaveMovieToFavoriteUseCase(movieRepository).execute(new Movie(binding.enterFavMovieText.getText().toString()));
            binding.noDataText.setText(String.format("Вы сохранили %s", result));
        });

        binding.getFavMovieButton.setOnClickListener(view -> {
            Movie movie = new GetFavoriteMovieUseCase(movieRepository).execute();
            binding.noDataText.setText(String.format("Ваш любимый фильм: %s", movie.getName()));
        });
    }
}
