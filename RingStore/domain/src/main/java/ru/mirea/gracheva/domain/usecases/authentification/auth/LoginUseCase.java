package ru.mirea.gracheva.domain.usecases.authentification.auth;

import ru.mirea.gracheva.domain.models.User;
import ru.mirea.gracheva.domain.models.UserRole;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;
import ru.mirea.gracheva.domain.repository.auth.UserRoleRepository;

public class LoginUseCase {
    private final AuthRepository authRepository;
    private final UserRoleRepository userRoleRepository;

    public LoginUseCase(AuthRepository authRepository, UserRoleRepository userRoleRepository) {
        this.authRepository = authRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public void execute(String email, String password, AuthRepository.AuthCallback callback) {
        authRepository.login(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(User user, UserRole role) {
                userRoleRepository.saveRole(UserRole.AUTHORIZED);
                callback.onSuccess(user, role);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }
}

