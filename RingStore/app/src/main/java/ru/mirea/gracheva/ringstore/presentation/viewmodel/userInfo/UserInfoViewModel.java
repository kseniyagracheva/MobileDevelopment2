package ru.mirea.gracheva.ringstore.presentation.viewmodel.userInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.gracheva.domain.models.UserRole;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;
import ru.mirea.gracheva.domain.repository.auth.UserRoleRepository;
import ru.mirea.gracheva.domain.usecases.authentification.auth.LogOutUseCase;

public class UserInfoViewModel extends ViewModel {
    private AuthRepository authRepository;
    private UserRoleRepository userRoleRepository;

    private MutableLiveData<UserRole> userRoleLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> successLiveData = new MutableLiveData<>();
    public UserInfoViewModel(AuthRepository authRepository, UserRoleRepository userRoleRepository){
        this.authRepository = authRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public LiveData<UserRole> getUserRole(){
        return userRoleLiveData;
    }
    public LiveData<String> getError(){
        return errorLiveData;
    }
    public LiveData<Boolean> ifSuccess(){
        return successLiveData;
    }

    public void loadUserRole(){
        UserRole role = userRoleRepository.getRole();
        userRoleLiveData.postValue(role != null ? role : UserRole.GUEST);
    }

    public void logout(){
        LogOutUseCase logOutUseCase = new LogOutUseCase(authRepository);
        logOutUseCase.execute(new AuthRepository.Callback(){
            @Override
            public void onSuccess(){
                userRoleRepository.saveRole(UserRole.GUEST);
                userRoleLiveData.postValue(UserRole.GUEST);
                successLiveData.postValue(true);
            };
            @Override
            public void onError(String error){
                errorLiveData.postValue(error);
            };
        });
    }

}
