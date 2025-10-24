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

import ru.mirea.gracheva.data.repository.AuthRepositoryImpl;
import ru.mirea.gracheva.data.repository.UserRoleRepositoryImpl;
import ru.mirea.gracheva.data.storage.auth.AuthDataSource;
import ru.mirea.gracheva.data.storage.auth.firebase.FireBaseAuthDataSource;
import ru.mirea.gracheva.data.storage.role.UserRoleDataSource;
import ru.mirea.gracheva.data.storage.role.sharedPrefs.SharedPreferencesUserRoleDataSource;
import ru.mirea.gracheva.domain.models.UserRole;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;
import ru.mirea.gracheva.domain.repository.auth.UserRoleRepository;
import ru.mirea.gracheva.domain.usecases.authentification.auth.LogOutUseCase;
import ru.mirea.gracheva.ringstore.R;
import ru.mirea.gracheva.ringstore.databinding.FragmentUserInfoBinding;

public class UserInfoFragment extends Fragment {
    private FragmentUserInfoBinding binding;
    private NavController navController;

    private LogOutUseCase logOutUseCase;

    private UserRoleRepository userRoleRepository;
    private AuthRepository authRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthDataSource authDataSource = new FireBaseAuthDataSource();
        UserRoleDataSource userRoleDataSource = new SharedPreferencesUserRoleDataSource(requireContext());

        userRoleRepository = new UserRoleRepositoryImpl(userRoleDataSource);
        authRepository = new AuthRepositoryImpl(authDataSource, userRoleRepository);

        logOutUseCase = new LogOutUseCase(authRepository);
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

        Bundle args = getArguments();
        if (args != null) {
            String email = args.getString("email", "");
            String role = args.getString("role", "");
            binding.emailText.setText(email.isEmpty() ? "Гость" : email);
            binding.roleText.setText("Роль: " + role);
        }

        binding.goToMetalsButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_userInfoFragment_to_metalPriceFragment);
        });

        binding.logOutButton.setOnClickListener(v -> {
            logOutUseCase.execute(new AuthRepository.Callback() {
                @Override
                public void onSuccess() {
                    userRoleRepository.saveRole(UserRole.GUEST);
                    Toast.makeText(requireContext(), "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.action_userInfoFragment_to_authFragment);
                }

                @Override
                public void onError(String errorMessage) {
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
