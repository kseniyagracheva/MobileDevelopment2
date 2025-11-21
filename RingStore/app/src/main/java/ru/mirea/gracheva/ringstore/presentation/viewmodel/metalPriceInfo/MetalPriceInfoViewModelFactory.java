package ru.mirea.gracheva.ringstore.presentation.viewmodel.metalPriceInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.gracheva.data.repository.metalPriceInfo.GoldPriceInfoRepositoryImpl;
import ru.mirea.gracheva.data.repository.metalPriceInfo.PlatinumPriceInfoRepositoryImpl;
import ru.mirea.gracheva.data.repository.metalPriceInfo.SilverPriceInfoRepositoryImpl;
import ru.mirea.gracheva.domain.repository.metalPriceInfo.GoldPriceInfoRepository;
import ru.mirea.gracheva.domain.repository.metalPriceInfo.PlatinumPriceInfoRepository;
import ru.mirea.gracheva.domain.repository.metalPriceInfo.SilverPriceInfoRepository;

public class MetalPriceInfoViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        GoldPriceInfoRepository goldPriceInfoRepository = new GoldPriceInfoRepositoryImpl();
        SilverPriceInfoRepository silverPriceInfoRepository = new SilverPriceInfoRepositoryImpl();
        PlatinumPriceInfoRepository platinumPriceInfoRepository = new PlatinumPriceInfoRepositoryImpl();
        return (T) new MetalPriceInfoViewModel(goldPriceInfoRepository, silverPriceInfoRepository, platinumPriceInfoRepository);
    }
}
