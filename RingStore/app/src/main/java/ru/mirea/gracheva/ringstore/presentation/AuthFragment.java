package ru.mirea.gracheva.ringstore.presentation;

import android.content.Context;
import android.content.SharedPreferences;
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

import ru.mirea.gracheva.ringstore.R;
import ru.mirea.gracheva.ringstore.databinding.FragmentAuthBinding;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.auth.AuthViewModel;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.auth.AuthViewModelFactory;

public class AuthFragment extends Fragment {

    private AuthViewModel vm;
    private FragmentAuthBinding binding;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this, new AuthViewModelFactory()).get(AuthViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        vm.getUser().observe(getViewLifecycleOwner(), user ->{
            Toast.makeText(requireContext(), "Добро пожаловать " + user.getEmail(), Toast.LENGTH_SHORT).show();
            //((MainActivity) requireActivity()).onLoginSuccess();
        });
        vm.getError().observe(getViewLifecycleOwner(), error ->{
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        });

        binding.loginButton.setOnClickListener(v -> {
            String email = binding.emailInput.getText().toString();
            String password = binding.passwordInput.getText().toString();

            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Пожалуйста, введите email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.isEmpty()) {
                Toast.makeText(requireContext(), "Пожалуйста, введите пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            vm.login(email,password);
            navController.navigate(R.id.action_authFragment_to_userInfoFragment);
        });

        binding.guestButton.setOnClickListener(v -> navController.popBackStack());

        binding.registerButton.setOnClickListener(v -> navController.navigate(R.id.action_authFragment_to_registerFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
