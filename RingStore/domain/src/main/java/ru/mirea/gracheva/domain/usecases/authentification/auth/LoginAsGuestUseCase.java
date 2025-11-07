package ru.mirea.gracheva.domain.usecases.authentification.auth;

import ru.mirea.gracheva.domain.models.User;
import ru.mirea.gracheva.domain.models.UserRole;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;
import ru.mirea.gracheva.domain.repository.auth.UserRoleRepository;

public class LoginAsGuestUseCase {
    private final AuthRepository authRepository;
    private final UserRoleRepository userRoleRepository;

    public LoginAsGuestUseCase(AuthRepository authRepository, UserRoleRepository userRoleRepository){
        this.authRepository = authRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public void execute(AuthRepository.AuthCallback callback){
        authRepository.loginAsGuest(new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(User user, UserRole role) {
                userRoleRepository.saveRole(UserRole.GUEST);
                callback.onSuccess(user, role);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }
}
