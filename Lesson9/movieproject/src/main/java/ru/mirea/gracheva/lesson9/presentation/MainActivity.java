package ru.mirea.gracheva.lesson9.presentation;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.gracheva.lesson9.R;
import ru.mirea.gracheva.lesson9.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel vm;

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

        vm = new ViewModelProvider(this).get(MainViewModel.class);

        binding.saveFavMovieButton.setOnClickListener(view -> {
            String movieName = binding.enterFavMovieText.getText().toString();
            Boolean result = vm.saveFavoriteMovie(movieName);
            binding.noDataText.setText(String.format("Вы сохранили %s", result));
        });

        binding.getFavMovieButton.setOnClickListener(view -> {
            String movieName = vm.getFavoriteMovie();
            binding.noDataText.setText(String.format("Ваш любимый фильм: %s", movieName));
        });

        Log.d(MainActivity.class.getSimpleName(), "MainActivity created");
    }
}