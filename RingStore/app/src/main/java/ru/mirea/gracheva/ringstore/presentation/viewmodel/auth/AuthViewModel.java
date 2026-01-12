package ru.mirea.gracheva.ringstore.presentation.viewmodel.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.gracheva.domain.models.User;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;
import ru.mirea.gracheva.domain.usecases.authentification.auth.LoginUseCase;

public class AuthViewModel extends ViewModel {
    private final AuthRepository authRepository;

    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public AuthViewModel(AuthRepository authRepository){
        this.authRepository = authRepository;
    }
    public LiveData<User> getUser(){
        return userLiveData;
    }

    public LiveData<String> getError(){
        return errorLiveData;
    }

    public void login(String email, String password){
        LoginUseCase loginUseCase = new LoginUseCase(authRepository);
        loginUseCase.execute(email, password, new AuthRepository.AuthCallback(){
            @Override
            public void onSuccess(User user){
                userLiveData.postValue(user);
            }
            @Override
            public void onError(String error){
                errorLiveData.postValue(error);
            }
        });

    }

    public void loginAsGuest(){
        // просто создаём гостя и пушим в LiveData
        User guest = new User("", "guest@example.com");
        userLiveData.postValue(guest);
    }
}
