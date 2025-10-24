package ru.mirea.gracheva.data.repository;

import ru.mirea.gracheva.data.DTO.UserDTO;
import ru.mirea.gracheva.data.storage.auth.AuthDataSource;
import ru.mirea.gracheva.domain.models.User;
import ru.mirea.gracheva.domain.models.UserRole;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;
import ru.mirea.gracheva.domain.repository.auth.UserRoleRepository;

public class AuthRepositoryImpl implements AuthRepository {
    private final AuthDataSource authDataSource;
    private final UserRoleRepository userRoleRepository;

    public AuthRepositoryImpl(AuthDataSource authDataSource, UserRoleRepository userRoleRepository) {
        this.authDataSource = authDataSource;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void register(String email, String password, AuthCallback callback) {
        authDataSource.register(email, password, new AuthDataSource.AuthCallback() {
            @Override
            public void onSuccess(UserDTO userDTO) {
                User user = mapToDomain(userDTO);
                userRoleRepository.saveRole(user.getRole());
                callback.onSuccess(user);
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
    public void loginAsGuest(AuthCallback callback) {
        authDataSource.loginAsGuest(new AuthDataSource.AuthCallback() {
            @Override
            public void onSuccess(UserDTO userDTO) {
                User user = mapToDomain(userDTO);
                userRoleRepository.saveRole(user.getRole());
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
                userRoleRepository.saveRole(UserRole.GUEST);
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
                userDTO.getEmail(),
                UserRole.valueOf(userDTO.getUserRoleDTO().getUserRoleName())
        );
    }
}
