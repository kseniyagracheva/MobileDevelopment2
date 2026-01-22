package ru.mirea.gracheva.data.storage.auth.firebase;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
    public void editUserProfile(String uid, String name, String surname, AuthDataSource.AuthCallback callback){
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", name);
        updates.put("surname", surname);

        firestore.collection("users").document(uid)
                .update(updates)
                .addOnSuccessListener(aVoid -> callback.onSuccess(new UserDTO(uid, null, name, surname)))
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    @Override
    public void deleteUserProfile(AuthDataSource.AuthCallback callback){
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();
        if (fbUser == null) {
            callback.onError("Нет активной сессии");
            return;
        }

        //Удаление документа из FireStore
        firestore.collection("users").document(fbUser.getUid())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    deleteFirebaseUser(fbUser, callback);
                })
                .addOnFailureListener(e -> callback.onError("Ошибка удаления" + e.getMessage()));
    }

    @Override
    public void getCurrentUser(AuthDataSource.AuthCallback callback){
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();
        if(fbUser==null){
            callback.onError("Пользователь вошел как гость");
            return;
        }

        firestore.collection("users").document(fbUser.getUid())
                .get()
                .addOnSuccessListener(doc -> {
                    String name = doc.getString("name") != null ? doc.getString("name") : "";
                    String surname = doc.getString("surname") != null ? doc.getString("surname") : "";
                    callback.onSuccess(new UserDTO(fbUser.getUid(), fbUser.getEmail(), name, surname));
                })
                .addOnFailureListener(e ->{
                    Log.e("AuthDataSource", "getCurrentUser Firestore error: " + e.getMessage());
                    callback.onSuccess(new UserDTO(fbUser.getUid(), fbUser.getEmail(), "", ""));
                });
    }

    @Override
    public void register(String email, String password, AuthDataSource.RegisterCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = task.getResult().getUser().getUid();
                        Log.d("AuthDataSource", "Auth successful, UID: " + uid);
                        createUserProfile(uid, () -> {
                            Log.d("AuthDataSource", "Profile created successfully for UID: " + uid);
                            callback.onSuccess();
                        });
                    } else {
                        Log.e("AuthDataSource", "Auth failed: " + task.getException());
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
        Log.d("AuthDataSource", "Creating profile for UID: " + uid);

        Map<String, Object> data = new HashMap<>();
        data.put("name", "");
        data.put("surname", "");
        firestore.collection("users").document(uid)
                .set(data)
                .addOnSuccessListener(aVoid -> {
                    Log.d("AuthDataSource", "Document CREATED: users/" + uid);
                    onComplete.run();
                })
                .addOnFailureListener(e -> {
                    Log.e("AuthDataSource", " Firestore ERROR: " + e.getMessage());
                    //onComplete.run();
                });
    }

    private void deleteFirebaseUser(FirebaseUser fbUser, AuthDataSource.AuthCallback callback){
        fbUser.delete()
                .addOnCompleteListener(task ->{
                    if(task.isSuccessful()){
                        callback.onSuccess(new UserDTO(fbUser.getUid(), null, "", ""));
                    }
                    else{
                        callback.onError("Ошибка удаления аккаунта: " + task.getException().getMessage());
                    }
                });
    }
}

