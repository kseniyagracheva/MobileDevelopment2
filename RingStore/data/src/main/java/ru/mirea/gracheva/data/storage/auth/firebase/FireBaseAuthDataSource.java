package ru.mirea.gracheva.data.storage.auth.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import ru.mirea.gracheva.data.DTO.UserDTO;
import ru.mirea.gracheva.data.storage.auth.AuthDataSource;

public class FireBaseAuthDataSource implements AuthDataSource {
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore firestore;

    public FireBaseAuthDataSource() {

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void getCurrentUser(AuthDataSource.AuthCallback callback){
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();
        if(fbUser==null){
            callback.onError("Пользователь вошел как гость");
            return;
        }
        callback.onSuccess(new UserDTO(fbUser.getUid(), fbUser.getEmail(), "", ""));
    }

    @Override
    public void register(String email, String password, AuthDataSource.RegisterCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = task.getResult().getUser().getUid();
                        createUserProfile(uid, () ->
                                callback.onSuccess());
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
                    if(task.isSuccessful()) {
                        FirebaseUser fbUser = task.getResult().getUser();
                        firestore.collection("users").document(fbUser.getUid())
                                .get()
                                .addOnSuccessListener(document -> {
                                    String name = document.getString("name");
                                    String surname = document.getString("surname");
                                    callback.onSuccess(new UserDTO(fbUser.getUid(), fbUser.getEmail(),
                                            name != null ? name : "", surname != null ? surname : ""));
                                })
                                .addOnFailureListener(e ->
                                        callback.onSuccess(new UserDTO(fbUser.getUid(), fbUser.getEmail(), "", ""))
                                );
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

    private void createUserProfile(String uid, Runnable onComplete){//в аргументы передается айди пользователя для дока и колбэк, которыф будет выполнен после создания дока
        firestore.collection("users").document(uid)
                .set(new HashMap<String, Object>() {{//создаем/перезаписываем док. HashMap<String, Object>() - данные ключ - значение
                    put ("name", "");
                    put("surname", "");

                }})// {{...}} создает анонимный класс, т.е. класс без имени, который существует только в контексте функции
                .addOnSuccessListener(aVoid -> onComplete.run())
                .addOnFailureListener(e -> onComplete.run());
    }
}

