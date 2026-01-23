package ru.mirea.gracheva.ringstore.presentation.viewmodel.ringDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.gracheva.domain.models.Ring;
import ru.mirea.gracheva.domain.models.User;
import ru.mirea.gracheva.domain.repository.RingRepository;
import ru.mirea.gracheva.domain.repository.auth.AuthRepository;
import ru.mirea.gracheva.domain.repository.favoriets.FavoriteRepository;
import ru.mirea.gracheva.domain.usecases.rings.GetRingByIdUseCase;
import ru.mirea.gracheva.ringstore.presentation.AuthFragment;
import ru.mirea.gracheva.ringstore.presentation.model.UserUI;

public class RingDetailViewModel extends ViewModel {
    RingRepository ringRepository;
    private MutableLiveData<Ring> ringLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();


    public RingDetailViewModel(RingRepository ringRepository){
        this.ringRepository = ringRepository;
    }

    public LiveData<Ring> getRingLiveData() {
        return ringLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }

    public void getRing(String ringId){
        isLoadingLiveData.setValue(true);
        GetRingByIdUseCase getRingByIdUseCase = new GetRingByIdUseCase(ringRepository);
        getRingByIdUseCase.execute(ringId, new RingRepository.Callback<Ring>() {
            @Override
            public void onSuccess(Ring result) {
                ringLiveData.postValue(result);
                isLoadingLiveData.postValue(false);
            }

            @Override
            public void onError(Throwable t) {
                errorLiveData.postValue("Кольцо не найдено" + t.getMessage());
                isLoadingLiveData.postValue(false);
            }
        });
    }
}
