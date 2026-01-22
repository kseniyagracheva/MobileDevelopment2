package ru.mirea.gracheva.ringstore.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import ru.mirea.gracheva.ringstore.R;
import ru.mirea.gracheva.ringstore.databinding.FragmentUserInfoBinding;
import ru.mirea.gracheva.ringstore.presentation.model.UserUI;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.userInfo.UserInfoViewModel;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.userInfo.UserInfoViewModelFactory;

public class UserInfoFragment extends Fragment {
    private FragmentUserInfoBinding binding;
    private NavController navController;

    private UserInfoViewModel vm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this, new UserInfoViewModelFactory()).get(UserInfoViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        vm.getUser().observe(getViewLifecycleOwner(), this::updateUIForUser);
        vm.loadUser();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showLogoutConfirmationDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Выход из аккаунта")
                .setMessage("Вы действительно хотите выйти из аккаунта?")
                .setPositiveButton("Да, выйти", (dialog, which) -> {
                    setupGuestProfile();
                    vm.logout();
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void updateUIForUser(UserUI user){
        if (user == null){ //Гость
            setupGuestProfile();
        } else{ //Авторизованный
            setupAuthorizedProfile(user);
        }

    }

    private void setupGuestProfile(){
        binding.emailText.setText("Гость");
        binding.logOutButton.setVisibility(View.GONE);
        binding.nameText.setVisibility(View.GONE);
        binding.surnameText.setVisibility(View.GONE);
        binding.editProfileButton.setVisibility(View.GONE);
        binding.loginButton.setVisibility(View.VISIBLE);
        binding.loginButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_userInfoFragment_to_authFragment);
        });
    }

    private void setupAuthorizedProfile(UserUI user){
        binding.emailText.setText(user.getEmail());
        binding.nameText.setText(user.getName());
        binding.surnameText.setText(user.getSurname());
        binding.logOutButton.setVisibility(View.VISIBLE);
        binding.logOutButton.setOnClickListener(v -> showLogoutConfirmationDialog());
        binding.editProfileButton.setVisibility(View.VISIBLE);
        binding.loginButton.setVisibility(View.GONE);
    }
}
