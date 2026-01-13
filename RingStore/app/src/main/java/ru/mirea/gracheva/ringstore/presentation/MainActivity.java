package ru.mirea.gracheva.ringstore.presentation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.mirea.gracheva.data.repository.AuthRepositoryImpl;
import ru.mirea.gracheva.data.storage.auth.AuthDataSource;
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

    public MainActivity(){
        AuthDataSource dataSource = new FireBaseAuthDataSource();
        authRepository = new AuthRepositoryImpl(dataSource);
        getCurrentUserUseCase = new GetCurrentUserUseCase(authRepository);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = binding.navView;

        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        navController = navHostFragment.getNavController();

        guestCheck();
        setupBottomNavVis();

    }


    private void guestCheck() {
        binding.navView.setOnItemSelectedListener(this::onNavItemSelected);
    }

    private boolean onNavItemSelected(MenuItem item){
        if (getCurrentUserUseCase.execute() == null && isRestrictedItem(item.getItemId())) {
            handleGuestNavigation(item.getItemId());
            return true;
        }
        navController.navigate(item.getItemId());
        return true;
    }

    private boolean isRestrictedItem(int itemId){
        return itemId == R.id.favorietsFragment || itemId == R.id.userInfoFragment;
    }

    private void handleGuestNavigation(int itemId){
        if (itemId == R.id.favorietsFragment){
            navController.navigate(R.id.authFragment);
        }
        else if (itemId == R.id.userInfoFragment){
            navController.navigate(R.id.authFragment);
        }
    }

    private void setupBottomNavVis() {
        navController.addOnDestinationChangedListener((controller, destination, arguments) ->{
            if (destination.getId() == R.id.authFragment || destination.getId() == R.id.registerFragment){
                binding.navView.setVisibility(View.GONE);
            }
            else{
                binding.navView.setVisibility(View.VISIBLE);
            }
        });

    }

    // Вызывается после успешного логина или гостевого входа
    /*public void onLoginSuccess() {
        showMainGraph();
    }*/
}


