package ru.mirea.gracheva.ringstore.presentation.viewmodel.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.gracheva.domain.models.User;
import ru.mirea.gracheva.domain.models.UserRole;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;
import ru.mirea.gracheva.domain.repository.auth.UserRoleRepository;
import ru.mirea.gracheva.domain.usecases.authentification.register.RegisterUseCase;

public class RegisterViewModel extends ViewModel {
    private final AuthRepository authRepository;

    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> successLiveData = new MutableLiveData<>();
    public RegisterViewModel(AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    public LiveData<String> getError(){
        return errorLiveData;
    }

    public LiveData<Boolean> ifSuccess(){
        return successLiveData;
    }

    public void register(String email, String password){
        RegisterUseCase registerUseCase = new RegisterUseCase(authRepository);
        registerUseCase.execute(email, password, new AuthRepository.RegisterCallback(){
            @Override
            public void onSuccess(){
                successLiveData.postValue(true);
            };
            @Override
            public void onError(String error){
                errorLiveData.postValue(error);
            }
        });
    }
}
