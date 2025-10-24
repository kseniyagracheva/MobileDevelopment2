package ru.mirea.gracheva.data.storage.auth;

import ru.mirea.gracheva.data.DTO.UserDTO;

public interface AuthDataSource {
    interface AuthCallback {
        void onSuccess(UserDTO userDTO);
        void onError(String errorMessage);
    }

    interface Callback {
        void onSuccess();
        void onError(String errorMessage);
    }

    void register(String email, String password, AuthCallback callback);
    void login(String email, String password, AuthCallback callback);
    void loginAsGuest(AuthCallback callback);
    void logout(Callback callback);
}

