package ru.mirea.gracheva.data.storage.auth.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.gracheva.data.DTO.UserDTO;
import ru.mirea.gracheva.data.storage.auth.AuthDataSource;

public class FireBaseAuthDataSource implements AuthDataSource {
    private final FirebaseAuth firebaseAuth;

    public FireBaseAuthDataSource() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public UserDTO getCurrentUser(){
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();
        if(fbUser==null){
            return null;
        }
        return new UserDTO(fbUser.getUid(), fbUser.getEmail());
    }

    @Override
    public void register(String email, String password, AuthDataSource.RegisterCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onError(task.getException() != null
                                ? task.getException().getMessage()
                                : "Ошибка регистрации");
                    }
                });

    }

    @Override
    public void login(String email, String password, AuthCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        FirebaseUser fbUser = task.getResult().getUser();
                        UserDTO userDTO = new UserDTO(fbUser.getUid(), fbUser.getEmail());

                        callback.onSuccess(userDTO);
                    } else {
                        callback.onError(task.getException() != null ? task.getException().getMessage() : "Unknown error");
                    }
                });
    }

    @Override
    public void logout(Callback callback) {
        firebaseAuth.signOut();
        callback.onSuccess();
    }
}

