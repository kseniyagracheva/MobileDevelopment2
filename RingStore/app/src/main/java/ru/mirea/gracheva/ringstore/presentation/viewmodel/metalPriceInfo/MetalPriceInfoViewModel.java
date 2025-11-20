package ru.mirea.gracheva.ringstore.presentation.viewmodel.metalPriceInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.gracheva.domain.models.MetalPriceInfo;
import ru.mirea.gracheva.domain.repository.MetalPriceInfoRepository;
import ru.mirea.gracheva.domain.usecases.metalInfo.GetMetalPriceInfo;

public class MetalPriceInfoViewModel extends ViewModel {
    private final MetalPriceInfoRepository metalPriceInfoRepository;

    private MutableLiveData<MetalPriceInfo> metalPriceInfoLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    public MetalPriceInfoViewModel(MetalPriceInfoRepository metalPriceInfoRepository){
        this.metalPriceInfoRepository = metalPriceInfoRepository;
    }

    public LiveData<MetalPriceInfo> getMetalPriveInfo(){
        return metalPriceInfoLiveData;
    }
    public LiveData<String> getError(){
        return errorLiveData;
    }

    public void fetch(){
        GetMetalPriceInfo getMetalPriceInfo = new GetMetalPriceInfo(metalPriceInfoRepository);
        getMetalPriceInfo.fetchMetalPriceInfo(new MetalPriceInfoRepository.MetalPriceCallback() {
            @Override
            public void onSuccess(MetalPriceInfo metalPriceInfo){
                metalPriceInfoLiveData.postValue(metalPriceInfo);
            }
            @Override
            public void onError(String error){
                errorLiveData.postValue(error);
            }
        });
    }
}
