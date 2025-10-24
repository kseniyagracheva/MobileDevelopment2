package ru.mirea.gracheva.domain.usecases.authentification.auth;

import ru.mirea.gracheva.domain.repository.auth.AuthRepository;

public class LoginAsGuestUseCase {
    private final AuthRepository authRepository;

    public LoginAsGuestUseCase(AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    public void execute(AuthRepository.AuthCallback callback){
        authRepository.loginAsGuest(callback);
    }
}
