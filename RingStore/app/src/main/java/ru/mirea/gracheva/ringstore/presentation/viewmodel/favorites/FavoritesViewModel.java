package ru.mirea.gracheva.ringstore.presentation.viewmodel.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.gracheva.domain.models.User;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;

public class FavoritesViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();


    public FavoritesViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LiveData<User> getUser() {
        return userLiveData;
    }

    public LiveData<Boolean> getLoading(){
        return loadingLiveData;
    }

    public void loadUser() {
        loadingLiveData.setValue(true);
        authRepository.getCurrentUser(new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(User user) {
                userLiveData.postValue(user);
                loadingLiveData.setValue(false);
            }

            @Override
            public void onError(String errorMessage) {
                userLiveData.postValue(null);
                loadingLiveData.setValue(false);
            }
        });
    }

}
