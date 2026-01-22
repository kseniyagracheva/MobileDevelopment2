package ru.mirea.gracheva.ringstore.presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import ru.mirea.gracheva.domain.models.User;
import ru.mirea.gracheva.ringstore.R;
import ru.mirea.gracheva.ringstore.databinding.FragmentFavoritesBinding;
import ru.mirea.gracheva.ringstore.databinding.FragmentUserInfoBinding;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.favorites.FavoritesViewModel;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.favorites.FavoritesViewModelFactory;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.userInfo.UserInfoViewModel;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.userInfo.UserInfoViewModelFactory;


public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;
    private NavController navController;

    private FavoritesViewModel vm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this, new FavoritesViewModelFactory()).get(FavoritesViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        vm.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.contentContainer.setVisibility(View.GONE);
                binding.guestContainer.setVisibility(View.GONE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        vm.getUser().observe(getViewLifecycleOwner(), this::updateUIForUser);
        vm.loadUser();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateUIForUser(User user){
        if (user == null){ //Гость
            setupGuestProfile();
        } else{ //Авторизованный
            setupAuthorizedProfile(user);
        }
    }

    private void setupGuestProfile(){
        binding.guestContainer.setVisibility(View.VISIBLE);
        binding.contentContainer.setVisibility(View.GONE);
        binding.loginButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_favoritesFragment_to_authFragment);
        });
    }

    private void setupAuthorizedProfile(User user){
        binding.contentContainer.setVisibility(View.VISIBLE);
        binding.emptyFavoritesText.setVisibility(View.VISIBLE);
        binding.recyclerFavorites.setVisibility(View.GONE);
        binding.emptyFavoritesText.setText("В вашем избранном пока пусто!");
    }
}