package ru.mirea.gracheva.domain.usecases.authentification.user;

import ru.mirea.gracheva.domain.models.User;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;

public class GetCurrentUserUseCase {
    private final AuthRepository authRepository;

    public  GetCurrentUserUseCase(AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    public User execute(){
        return authRepository.getCurrentUser();
    }
}
