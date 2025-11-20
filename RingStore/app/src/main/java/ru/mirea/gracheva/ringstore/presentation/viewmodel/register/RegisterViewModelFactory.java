package ru.mirea.gracheva.ringstore.presentation.viewmodel.register;

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
import ru.mirea.gracheva.ringstore.presentation.viewmodel.auth.AuthViewModel;

public class RegisterViewModelFactory implements ViewModelProvider.Factory {
    private Context context;
    public RegisterViewModelFactory(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        AuthDataSource authDataSource = new FireBaseAuthDataSource();
        UserRoleDataSource userRoleDataSource = new SharedPreferencesUserRoleDataSource(context);
        AuthRepository authRepository = new AuthRepositoryImpl(authDataSource);
        UserRoleRepository userRoleRepository = new UserRoleRepositoryImpl(userRoleDataSource);
        return (T) new AuthViewModel(authRepository, userRoleRepository);
    }
}
