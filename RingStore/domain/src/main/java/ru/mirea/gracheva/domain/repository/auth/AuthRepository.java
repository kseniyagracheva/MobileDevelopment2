package ru.mirea.gracheva.domain.repository.auth;

import ru.mirea.gracheva.domain.models.User;

public interface AuthRepository {
    public interface AuthCallback {
        public void onSuccess(User user);
        public void onError(String errorMessage);
    }
    public interface Callback {
        public void onSuccess();
        public void onError(String errorMessage);
    }

    public interface RegisterCallback {
        public void onSuccess();
        public void onError(String errorMessage);
    }

    public void editUserProfile(String uid, String name, String surname, AuthCallback callback);
    public void deleteUserProfile(AuthCallback callback);
    public void getCurrentUser(AuthCallback callback);
    public void register(String email, String password, RegisterCallback callback);
    public void login(String email, String password, AuthCallback callback);
    public void logout(Callback callback);
}


