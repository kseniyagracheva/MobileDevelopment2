package ru.mirea.gracheva.ringstore.presentation.viewmodel.rings;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.gracheva.data.repository.MockNetworkApi;
import ru.mirea.gracheva.data.repository.RingRepositoryImpl;

import ru.mirea.gracheva.domain.repository.NetworkApi;
import ru.mirea.gracheva.domain.repository.RingRepository;

public class RingListViewModelFactory implements ViewModelProvider.Factory {
    private Context context;
    public RingListViewModelFactory(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        NetworkApi networkApi = new MockNetworkApi();
        RingRepository ringRepository = new RingRepositoryImpl(context, networkApi);
        return (T) new RingListViewModel(ringRepository);
    }
}
