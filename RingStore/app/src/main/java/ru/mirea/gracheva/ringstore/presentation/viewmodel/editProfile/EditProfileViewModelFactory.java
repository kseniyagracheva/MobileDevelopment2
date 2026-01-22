package ru.mirea.gracheva.ringstore.presentation.viewmodel.editProfile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.gracheva.data.repository.AuthRepositoryImpl;
import ru.mirea.gracheva.data.storage.auth.AuthDataSource;
import ru.mirea.gracheva.data.storage.auth.firebase.FireBaseAuthDataSource;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.userInfo.UserInfoViewModel;

public class EditProfileViewModelFactory implements ViewModelProvider.Factory{

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        AuthDataSource authDataSource = new FireBaseAuthDataSource();
        AuthRepository authRepository = new AuthRepositoryImpl(authDataSource);
        return (T) new EditProfileViewModel(authRepository);
    }
}
