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

    public void editUserProfile(String uid, String name, String surname, AuthCallback callback);
    public void deleteUserProfile(  AuthCallback callback);
    public void getCurrentUser(AuthCallback callback);
    public void register(String email, String password, RegisterCallback callback);
    public void login(String email, String password, AuthCallback callback);
    public void logout(Callback callback);
}

