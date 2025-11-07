package ru.mirea.gracheva.data.storage.auth;

import ru.mirea.gracheva.data.DTO.UserDTO;
import ru.mirea.gracheva.data.DTO.UserRoleDTO;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;

public interface AuthDataSource {
    interface AuthCallback {
        void onSuccess(UserDTO userDTO, UserRoleDTO userRoleDTO);
        void onError(String errorMessage);
    }

    interface Callback {
        void onSuccess();
        void onError(String errorMessage);
    }
    public interface RegisterCallback {
        public void onSuccess();
        public void onError(String errorMessage);
    }

    public void register(String email, String password, RegisterCallback callback);
    void login(String email, String password, AuthCallback callback);
    void loginAsGuest(AuthCallback callback);
    void logout(Callback callback);
}

