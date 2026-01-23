package ru.mirea.gracheva.ringstore.presentation.viewmodel.rings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.mirea.gracheva.domain.models.Ring;
import ru.mirea.gracheva.domain.repository.RingRepository;
import ru.mirea.gracheva.domain.usecases.rings.GetRingsUseCase;

public class RingListViewModel extends ViewModel {
    RingRepository ringRepository;
    private MutableLiveData<List<Ring>> ringsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    private MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();

    public RingListViewModel(RingRepository ringRepository){
        this.ringRepository = ringRepository;
    }
    public LiveData<List<Ring>> getRingsLiveData() {
        return ringsLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getIsLoadingLiveData(){
        return isLoadingLiveData;
    }
    public void getRings(){
        isLoadingLiveData.setValue(true);
        GetRingsUseCase getRingsUseCase = new GetRingsUseCase(ringRepository);
        getRingsUseCase.execute(new RingRepository.Callback<List<Ring>>() {
            @Override
            public void onSuccess(List<Ring> result) {
                ringsLiveData.postValue(result);
                isLoadingLiveData.postValue(false);
            }

            @Override
            public void onError(Throwable t) {
                errorLiveData.postValue("Ошибка загрузки каталога" + t.getMessage());
                isLoadingLiveData.postValue(false);
            }
        });
    }
}
