package ru.mirea.gracheva.ringstore.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import ru.mirea.gracheva.domain.models.User;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;
import ru.mirea.gracheva.domain.usecases.authentification.auth.LoginAsGuestUseCase;
import ru.mirea.gracheva.domain.usecases.authentification.auth.LoginUseCase;
import ru.mirea.gracheva.data.repository.AuthRepositoryImpl;
import ru.mirea.gracheva.data.repository.UserRoleRepositoryImpl;
import ru.mirea.gracheva.data.storage.auth.firebase.FireBaseAuthDataSource;
import ru.mirea.gracheva.data.storage.role.sharedPrefs.SharedPreferencesUserRoleDataSource;
import ru.mirea.gracheva.data.storage.auth.AuthDataSource;
import ru.mirea.gracheva.data.storage.role.UserRoleDataSource;
import ru.mirea.gracheva.ringstore.R;
import ru.mirea.gracheva.ringstore.databinding.FragmentAuthBinding;

public class AuthFragment extends Fragment {

    private FragmentAuthBinding binding;
    private NavController navController;

    // UseCases
    private LoginUseCase loginUseCase;
    private LoginAsGuestUseCase loginAsGuestUseCase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthDataSource authDataSource = new FireBaseAuthDataSource();
        UserRoleDataSource userRoleDataSource = new SharedPreferencesUserRoleDataSource(requireContext());

        AuthRepository authRepository = new AuthRepositoryImpl(authDataSource, new UserRoleRepositoryImpl(userRoleDataSource));

        loginUseCase = new LoginUseCase(authRepository);
        loginAsGuestUseCase = new LoginAsGuestUseCase(authRepository);
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

        binding.loginButton.setOnClickListener(v -> login());
        binding.guestButton.setOnClickListener(v -> loginGuest());
        binding.registerButton.setOnClickListener(v -> navController.navigate(R.id.action_authFragment_to_registerFragment));
    }

    private void login() {
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
        loginUseCase.execute(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(User user) {
                Toast.makeText(requireContext(), "Добро пожаловать " + user.getEmail(), Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("email", user.getEmail());
                bundle.putString("role", user.getRole().name());

                navController.navigate(R.id.action_authFragment_to_userInfoFragment, bundle);
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginGuest() {
        loginAsGuestUseCase.execute(new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(User user) {
                Toast.makeText(requireContext(), "Вход выполнен как гость", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("role", user.getRole().name());

                navController.navigate(R.id.action_authFragment_to_userInfoFragment, bundle);
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
