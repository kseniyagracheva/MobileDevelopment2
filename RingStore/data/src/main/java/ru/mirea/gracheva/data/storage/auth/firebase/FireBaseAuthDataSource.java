package ru.mirea.gracheva.data.storage.auth.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.gracheva.data.DTO.UserDTO;
import ru.mirea.gracheva.data.DTO.UserRoleDTO;
import ru.mirea.gracheva.data.storage.auth.AuthDataSource;

public class FireBaseAuthDataSource implements AuthDataSource {
    private final FirebaseAuth firebaseAuth;

    public FireBaseAuthDataSource() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void register(String email, String password, AuthCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser fbUser = task.getResult().getUser();
                        UserDTO userDTO = new UserDTO(fbUser.getUid(), fbUser.getEmail(), new UserRoleDTO("AUTHORIZED"));
                        callback.onSuccess(userDTO);
                    } else {
                        callback.onError(task.getException() != null ? task.getException().getMessage() : "Unknown error");
                    }
                });
    }

    @Override
    public void login(String email, String password, AuthCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        FirebaseUser fbUser = task.getResult().getUser();
                        UserDTO userDTO = new UserDTO(fbUser.getUid(), fbUser.getEmail(), new UserRoleDTO("AUTHORIZED"));
                        callback.onSuccess(userDTO);
                    } else {
                        callback.onError(task.getException() != null ? task.getException().getMessage() : "Unknown error");
                    }
                });
    }

    @Override
    public void loginAsGuest(AuthCallback callback) {
        UserDTO guestUser = new UserDTO("", "guest@example.com", new UserRoleDTO("GUEST"));
        callback.onSuccess(guestUser);
    }

    @Override
    public void logout(Callback callback) {
        firebaseAuth.signOut();
        callback.onSuccess();
    }
}

