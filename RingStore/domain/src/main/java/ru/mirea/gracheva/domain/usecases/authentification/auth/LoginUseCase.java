package ru.mirea.gracheva.domain.usecases.authentification.auth;

import ru.mirea.gracheva.domain.repository.auth.AuthRepository;

public class LoginUseCase {
    private final AuthRepository authRepository;

    public LoginUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute(String email, String password, AuthRepository.AuthCallback callback) {
        authRepository.login(email, password, callback);
    }
}
