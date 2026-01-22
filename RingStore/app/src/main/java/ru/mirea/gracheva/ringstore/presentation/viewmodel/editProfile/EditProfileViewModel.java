package ru.mirea.gracheva.ringstore.presentation.viewmodel.editProfile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.gracheva.domain.models.User;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;
import ru.mirea.gracheva.ringstore.presentation.model.UserUI;

public class EditProfileViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<UserUI> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    public LiveData<UserUI> getUser(){
        return userLiveData;
    }
    public LiveData<String> getError(){ return errorLiveData; }
    public void clearError() { errorLiveData.setValue(null); }
    public LiveData<Boolean> getLoading(){
        return loadingLiveData;
    }
    public EditProfileViewModel (AuthRepository authRepository){
        this.authRepository = authRepository;
        loadCurrentUser();
    }

    public void loadCurrentUser(){
        loadingLiveData.setValue(true);
        authRepository.getCurrentUser(new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(User user) {
                userLiveData.setValue(mapToUI(user));
                loadingLiveData.setValue(false);
            }

            @Override
            public void onError(String errorMessage) {
                errorLiveData.setValue(errorMessage);
                loadingLiveData.setValue(false);
            }
        });
    }

    public void editProfile(String name, String surname){
        UserUI currentUser = userLiveData.getValue();
        if (currentUser != null){
            loadingLiveData.setValue(true);
            authRepository.editUserProfile(currentUser.getUserId(), name, surname, new AuthRepository.AuthCallback() {
                @Override
                public void onSuccess(User user) {
                    loadCurrentUser();
                    userLiveData.setValue(mapToUI(user));
                    loadingLiveData.setValue(false);
                }

                @Override
                public void onError(String errorMessage) {
                    errorLiveData.setValue(errorMessage);
                    loadingLiveData.setValue(false);
                }
            });
        }
    }

    public void deleteProfile(){
        loadingLiveData.setValue(true);
        authRepository.deleteUserProfile(new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(User user) {
                userLiveData.setValue(null);
                loadingLiveData.setValue(false);
            }

            @Override
            public void onError(String errorMessage) {
                errorLiveData.setValue(errorMessage);
                loadingLiveData.setValue(false);
            }
        });
    }

    private UserUI mapToUI(User user){
        return new UserUI(
                user.getUserId(),
                user.getEmail(),
                user.getName(),
                user.getSurname()
        );
    }
}
