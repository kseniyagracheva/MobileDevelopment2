package ru.mirea.gracheva.domain.usecases.authentification.user;

import ru.mirea.gracheva.domain.models.User;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;

public class GetCurrentUserUseCase {
    private final AuthRepository authRepository;

    public  GetCurrentUserUseCase(AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    public void execute(AuthRepository.AuthCallback callback){
        authRepository.getCurrentUser(new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(User user) {
                callback.onSuccess(user);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }
}
