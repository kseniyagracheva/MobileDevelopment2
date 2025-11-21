package ru.mirea.gracheva.ringstore.presentation.viewmodel.metalPriceInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.gracheva.domain.models.MetalPriceInfo;
import ru.mirea.gracheva.domain.repository.metalPriceInfo.GoldPriceInfoRepository;
import ru.mirea.gracheva.domain.repository.metalPriceInfo.PlatinumPriceInfoRepository;
import ru.mirea.gracheva.domain.repository.metalPriceInfo.SilverPriceInfoRepository;
import ru.mirea.gracheva.domain.usecases.metalInfo.GetGoldPriceInfo;
import ru.mirea.gracheva.domain.usecases.metalInfo.GetPlatinumPriceInfo;
import ru.mirea.gracheva.domain.usecases.metalInfo.GetSilverPriceInfo;

public class MetalPriceInfoViewModel extends ViewModel {
    private final GoldPriceInfoRepository goldPriceInfoRepository;
    private final SilverPriceInfoRepository silverPriceInfoRepository;
    private final PlatinumPriceInfoRepository platinumPriceInfoRepository;

    private MutableLiveData<MetalPriceInfo> goldPriceInfoLiveData = new MutableLiveData<>();
    private MutableLiveData<MetalPriceInfo> silverPriceInfoLiveData = new MutableLiveData<>();
    private MutableLiveData<MetalPriceInfo> platinumPriceInfoLiveData = new MutableLiveData<>();
    private MediatorLiveData<List<MetalPriceInfo>> combinedLiveData = new MediatorLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    public MetalPriceInfoViewModel(GoldPriceInfoRepository goldPriceInfoRepository, SilverPriceInfoRepository silverPriceInfoRepository, PlatinumPriceInfoRepository platinumPriceInfoRepository ){
        this.goldPriceInfoRepository = goldPriceInfoRepository;
        this.silverPriceInfoRepository = silverPriceInfoRepository;
        this.platinumPriceInfoRepository = platinumPriceInfoRepository;
        combinedLiveData.addSource(goldPriceInfoLiveData, gold -> updateCombined());
        combinedLiveData.addSource(silverPriceInfoLiveData, silver -> updateCombined());
        combinedLiveData.addSource(platinumPriceInfoLiveData, platinum -> updateCombined());
    }

    private void updateCombined(){
        List<MetalPriceInfo> combined = new ArrayList<>();
        if (goldPriceInfoLiveData.getValue()!=null) combined.add(goldPriceInfoLiveData.getValue());
        if (silverPriceInfoLiveData.getValue()!=null) combined.add(silverPriceInfoLiveData.getValue());
        if (platinumPriceInfoLiveData.getValue()!=null) combined.add(platinumPriceInfoLiveData.getValue());
        combinedLiveData.postValue(combined);
    }

    public LiveData<List<MetalPriceInfo>> getMetalPriceInfo(){
        return combinedLiveData;
    }
    public LiveData<String> getError(){
        return errorLiveData;
    }

    public void fetch(){
        new GetGoldPriceInfo(goldPriceInfoRepository).fetchMetalPriceInfo(new GoldPriceInfoRepository.MetalPriceCallback() {
            @Override
            public void onSuccess(MetalPriceInfo data){
                goldPriceInfoLiveData.postValue(data);
            }
            @Override
            public void onError(String error){
                errorLiveData.postValue(error);
            }
        });
        new GetSilverPriceInfo(silverPriceInfoRepository).fetchMetalPriceInfo(new SilverPriceInfoRepository.MetalPriceCallback() {
            @Override
            public void onSuccess(MetalPriceInfo data){
                silverPriceInfoLiveData.postValue(data);
            }
            @Override
            public void onError(String error){
                errorLiveData.postValue(error);
            }
        });
        new GetPlatinumPriceInfo(platinumPriceInfoRepository).fetchMetalPriceInfo(new PlatinumPriceInfoRepository.MetalPriceCallback() {
            @Override
            public void onSuccess(MetalPriceInfo data) {
                platinumPriceInfoLiveData.postValue(data);
            }

            @Override
            public void onError(String error) {
                errorLiveData.postValue(error);
            }
        });
    }
}
