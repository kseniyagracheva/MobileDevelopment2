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
import ru.mirea.gracheva.domain.usecases.authentification.register.RegisterUseCase;
import ru.mirea.gracheva.data.repository.AuthRepositoryImpl;
import ru.mirea.gracheva.data.repository.UserRoleRepositoryImpl;
import ru.mirea.gracheva.data.storage.auth.firebase.FireBaseAuthDataSource;
import ru.mirea.gracheva.data.storage.role.sharedPrefs.SharedPreferencesUserRoleDataSource;
import ru.mirea.gracheva.data.storage.auth.AuthDataSource;
import ru.mirea.gracheva.data.storage.role.UserRoleDataSource;
import ru.mirea.gracheva.ringstore.R;
import ru.mirea.gracheva.ringstore.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;
    private NavController navController;

    private RegisterUseCase registerUseCase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthDataSource authDataSource = new FireBaseAuthDataSource();
        UserRoleDataSource userRoleDataSource = new SharedPreferencesUserRoleDataSource(requireContext());

        AuthRepository authRepository = new AuthRepositoryImpl(authDataSource);

        registerUseCase = new RegisterUseCase(authRepository);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        binding.registerButton.setOnClickListener(v -> register());
    }

    private void register() {
        String email = binding.emailInput.getText().toString();
        String password = binding.passwordInput.getText().toString();

        registerUseCase.execute(email, password, new AuthRepository.RegisterCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(requireContext(), "Регистрация успешна", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_registerFragment_to_authFragment);
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
