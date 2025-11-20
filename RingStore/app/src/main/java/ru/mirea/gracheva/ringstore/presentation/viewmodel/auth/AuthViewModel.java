package ru.mirea.gracheva.ringstore.presentation.viewmodel.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.gracheva.domain.models.User;
import ru.mirea.gracheva.domain.models.UserRole;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;
import ru.mirea.gracheva.domain.repository.auth.UserRoleRepository;
import ru.mirea.gracheva.domain.usecases.authentification.auth.LoginAsGuestUseCase;
import ru.mirea.gracheva.domain.usecases.authentification.auth.LoginUseCase;

public class AuthViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final UserRoleRepository userRoleRepository;

    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private MutableLiveData<UserRole> userRoleLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public AuthViewModel(AuthRepository authRepository, UserRoleRepository userRoleRepository){
        this.authRepository = authRepository;
        this.userRoleRepository = userRoleRepository;
    }
    public LiveData<User> getUser(){
        return userLiveData;
    }
    public LiveData<UserRole> getUserRole(){
        return userRoleLiveData;
    }

    public LiveData<String> getError(){
        return errorLiveData;
    }

    public void login(String email, String password){
        LoginUseCase loginUseCase = new LoginUseCase(authRepository, userRoleRepository);
        loginUseCase.execute(email, password, new AuthRepository.AuthCallback(){
            @Override
            public void onSuccess(User user, UserRole role){
                userLiveData.postValue(user);
                userRoleLiveData.postValue(role);
            }
            @Override
            public void onError(String error){
                errorLiveData.postValue(error);
            }
        });

    }

    public void loginAsGuest(){
        LoginAsGuestUseCase loginAsGuestUseCase = new LoginAsGuestUseCase(authRepository, userRoleRepository);
        loginAsGuestUseCase.execute(new AuthRepository.AuthCallback(){
            @Override
            public void onSuccess(User user, UserRole role){
                userLiveData.postValue(user);
                userRoleLiveData.postValue(role);
            }
            @Override
            public void onError(String error){
                errorLiveData.postValue(error);
            }
        } );
    }
}
