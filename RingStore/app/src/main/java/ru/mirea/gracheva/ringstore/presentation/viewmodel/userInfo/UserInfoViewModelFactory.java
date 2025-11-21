package ru.mirea.gracheva.ringstore.presentation.viewmodel.userInfo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.gracheva.data.repository.AuthRepositoryImpl;
import ru.mirea.gracheva.data.repository.UserRoleRepositoryImpl;
import ru.mirea.gracheva.data.storage.auth.AuthDataSource;
import ru.mirea.gracheva.data.storage.auth.firebase.FireBaseAuthDataSource;
import ru.mirea.gracheva.data.storage.role.UserRoleDataSource;
import ru.mirea.gracheva.data.storage.role.sharedPrefs.SharedPreferencesUserRoleDataSource;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;
import ru.mirea.gracheva.domain.repository.auth.UserRoleRepository;

public class UserInfoViewModelFactory implements ViewModelProvider.Factory {
    private Context context;
    public UserInfoViewModelFactory(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        AuthDataSource authDataSource = new FireBaseAuthDataSource();
        UserRoleDataSource userRoleDataSource = new SharedPreferencesUserRoleDataSource(context);
        UserRoleRepository userRoleRepository = new UserRoleRepositoryImpl(userRoleDataSource);
        AuthRepository authRepository = new AuthRepositoryImpl(authDataSource);
        return (T) new UserInfoViewModel(authRepository, userRoleRepository);
    }
}
