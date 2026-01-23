package ru.mirea.gracheva.ringstore.presentation.viewmodel.ringDetail;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.gracheva.data.repository.RingRepositoryImpl;
import ru.mirea.gracheva.data.storage.rings.RingDataSource;
import ru.mirea.gracheva.data.storage.rings.firestore.FirestoreDataSource;
import ru.mirea.gracheva.domain.repository.RingRepository;
public class RingDetailViewModelFactory implements ViewModelProvider.Factory{
    private Context context;
    public RingDetailViewModelFactory(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){

        RingDataSource ringDataSource = new FirestoreDataSource();
        RingRepository ringRepository = new RingRepositoryImpl(context, ringDataSource);
        return (T) new RingDetailViewModel(ringRepository);
    }
}
