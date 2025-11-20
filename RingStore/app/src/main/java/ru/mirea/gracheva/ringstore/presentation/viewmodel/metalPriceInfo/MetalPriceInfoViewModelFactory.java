package ru.mirea.gracheva.ringstore.presentation.viewmodel.metalPriceInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.gracheva.data.repository.MetalPriceInfoRepositoryImpl;
import ru.mirea.gracheva.domain.repository.MetalPriceInfoRepository;
import ru.mirea.gracheva.ringstore.presentation.viewmodel.auth.AuthViewModel;

public class MetalPriceInfoViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        MetalPriceInfoRepository metalPriceInfoRepository = new MetalPriceInfoRepositoryImpl();
        return (T) new MetalPriceInfoViewModel(metalPriceInfoRepository);
    }
}
