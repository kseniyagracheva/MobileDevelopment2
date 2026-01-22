package ru.mirea.gracheva.ringstore.presentation.viewmodel.userInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.gracheva.domain.models.User;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;
import ru.mirea.gracheva.ringstore.presentation.model.UserUI;

public class UserInfoViewModel extends ViewModel {

    private final AuthRepository authRepository;
    private final MutableLiveData<UserUI> userLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    public UserInfoViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LiveData<UserUI> getUser() {
        return userLiveData;
    }

    public LiveData<String> getError(){
        return errorLiveData;
    }


    public void loadUser() {
        authRepository.getCurrentUser(new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(User user) {
                userLiveData.postValue(mapToUI(user));
            }

            @Override
            public void onError(String errorMessage) {
                errorLiveData.postValue(errorMessage);
            }
        });
    }

    public void logout() {
        authRepository.logout(new AuthRepository.Callback() {
            @Override
            public void onSuccess() {
                userLiveData.postValue(null);
            }

            @Override
            public void onError(String errorMessage) {
                errorLiveData.postValue(errorMessage);
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
