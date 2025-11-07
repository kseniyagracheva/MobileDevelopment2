package ru.mirea.gracheva.domain.usecases.rings;

import java.util.List;


import ru.mirea.gracheva.domain.models.Ring;
import ru.mirea.gracheva.domain.repository.RingRepository;

public class GetRingsUseCase {

    private final RingRepository ringRepository;

    public GetRingsUseCase(RingRepository ringRepository) {
        this.ringRepository = ringRepository;
    }

    public void execute(RingRepository.Callback<List<Ring>> callback)
    {
        ringRepository.getRings(new RingRepository.Callback<List<Ring>>(){
            @Override
            public void onSuccess(List<Ring> rings){
                callback.onSuccess(rings);
            }
            @Override
            public void onError(Throwable t){
                callback.onError(t);
            }
        });
    }
}

