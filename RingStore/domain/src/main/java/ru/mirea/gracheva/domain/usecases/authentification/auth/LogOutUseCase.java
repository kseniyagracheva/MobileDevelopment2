package ru.mirea.gracheva.domain.usecases.authentification.auth;

import ru.mirea.gracheva.domain.repository.auth.AuthRepository;

public class LogOutUseCase {
    private final AuthRepository authRepository;

    public LogOutUseCase(AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    public void execute(AuthRepository.Callback callback){
        authRepository.logout(callback);
    }
}
