package ru.mirea.gracheva.data.repository;

import ru.mirea.gracheva.data.DTO.UserDTO;
import ru.mirea.gracheva.data.storage.auth.AuthDataSource;
import ru.mirea.gracheva.domain.models.User;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;

public class AuthRepositoryImpl implements AuthRepository {
    private final AuthDataSource authDataSource;

    public AuthRepositoryImpl(AuthDataSource authDataSource) {
        this.authDataSource = authDataSource;
    }

    @Override
    public User getCurrentUser(){
        UserDTO userDTO = authDataSource.getCurrentUser();
        return userDTO == null? null: mapToDomain(userDTO);
    }

    @Override
    public void register(String email, String password, RegisterCallback callback) {
        authDataSource.register(email, password, new AuthDataSource.RegisterCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void login(String email, String password, AuthCallback callback) {
        authDataSource.login(email, password, new AuthDataSource.AuthCallback() {
            @Override
            public void onSuccess(UserDTO userDTO) {
                User user = mapToDomain(userDTO);
                callback.onSuccess(user);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void logout(Callback callback) {
        authDataSource.logout(new AuthDataSource.Callback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    private User mapToDomain(UserDTO userDTO) {
        return new User(
                userDTO.getUserId(),
                userDTO.getEmail()
        );
    }
}
