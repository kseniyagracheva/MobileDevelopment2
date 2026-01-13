package ru.mirea.gracheva.ringstore.presentation.viewmodel.userInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.gracheva.domain.models.User;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;
import ru.mirea.gracheva.domain.usecases.authentification.auth.LogOutUseCase;

public class UserInfoViewModel extends ViewModel {

    private final AuthRepository authRepository;
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();

    private final MutableLiveData<Boolean> logoutSuccess = new MutableLiveData<>();

    public UserInfoViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LiveData<User> getUser() {
        return userLiveData;
    }
    public LiveData<Boolean> getLogoutResult(){
        return logoutSuccess;
    }

    public void loadUser() {
        userLiveData.postValue(authRepository.getCurrentUser());
    }

    public void logout() {
        authRepository.logout(new AuthRepository.Callback() {
            @Override
            public void onSuccess() {
                userLiveData.postValue(null);
                logoutSuccess.postValue(true);
            }

            @Override
            public void onError(String errorMessage) {
                logoutSuccess.postValue(false);
            }
        });
    }
}
