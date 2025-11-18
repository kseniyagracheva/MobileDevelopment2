package ru.mirea.gracheva.lesson9.presentation;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
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

        vm = new ViewModelProvider(this, new ViewModelFactory(this)).get(MainViewModel.class);

        vm.getFavoriteMovie().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.noDataText.setText(s);
            }
        });
        binding.saveFavMovieButton.setOnClickListener(view -> {
            String movieName = binding.enterFavMovieText.getText().toString();
            Boolean res = vm.saveMovie(movieName);
            binding.noDataText.setText(String.format("Вы сохранили %s", res));
        });

        binding.getFavMovieButton.setOnClickListener(view -> {
            vm.getMovie();
        });

        Log.d(MainActivity.class.getSimpleName(), "MainActivity created");
    }
}