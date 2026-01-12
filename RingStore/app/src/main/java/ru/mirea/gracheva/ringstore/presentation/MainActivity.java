package ru.mirea.gracheva.ringstore.presentation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.mirea.gracheva.data.repository.AuthRepositoryImpl;
import ru.mirea.gracheva.data.storage.auth.firebase.FireBaseAuthDataSource;
import ru.mirea.gracheva.domain.models.User;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;
import ru.mirea.gracheva.domain.usecases.authentification.user.GetCurrentUserUseCase;
import ru.mirea.gracheva.ringstore.R;
import ru.mirea.gracheva.ringstore.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private BottomNavigationView navView;

    private AuthRepository authRepository;
    private GetCurrentUserUseCase getCurrentUserUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = binding.navView;

        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        navController = navHostFragment.getNavController();

        // 🔹 Инициализация репозитория и UseCase
        authRepository = new AuthRepositoryImpl(new FireBaseAuthDataSource());
        getCurrentUserUseCase = new GetCurrentUserUseCase(authRepository);

        // 🔹 Проверка текущего пользователя через UseCase
        User currentUser = getCurrentUserUseCase.execute();

        if (currentUser != null) {
            showMainGraph();
        } else {
            showAuthGraph();
        }
    }

    private void showAuthGraph() {
        navView.setVisibility(View.GONE);
        navController.setGraph(R.navigation.nav_graph_auth);
    }

    private void showMainGraph() {
        navView.setVisibility(View.VISIBLE);
        navController.setGraph(R.navigation.nav_graph_main);

        navView.setOnNavigationItemSelectedListener(item -> {
            navController.navigate(item.getItemId());
            return true;
        });

        // Автопереход на UserInfoFragment
        navController.navigate(R.id.userInfoFragment, null);
    }

    // Вызывается после успешного логина или гостевого входа
    public void onLoginSuccess() {
        showMainGraph();
    }
}


