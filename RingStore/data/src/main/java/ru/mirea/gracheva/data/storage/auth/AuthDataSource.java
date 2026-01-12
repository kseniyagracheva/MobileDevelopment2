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
    public interface RegisterCallback {
        public void onSuccess();
        public void onError(String errorMessage);
    }

    UserDTO getCurrentUser();
    public void register(String email, String password, RegisterCallback callback);
    void login(String email, String password, AuthCallback callback);
    void logout(Callback callback);
}

