package ru.mirea.gracheva.domain.usecases.rings;

import java.util.List;

import ru.mirea.gracheva.domain.models.Ring;
import ru.mirea.gracheva.domain.repository.RingRepository;

public class GetRingByIdUseCase {

    private final RingRepository ringRepository;
    public GetRingByIdUseCase(RingRepository ringRepository){
        this.ringRepository = ringRepository;
    }

    public void execute(String ringId, RingRepository.Callback<Ring> callback)
    {
        ringRepository.getRingById(ringId, new RingRepository.Callback<Ring>(){
            @Override
            public void onSuccess(Ring ring){
                callback.onSuccess(ring);
            }
            @Override
            public void onError(Throwable t){
                callback.onError(t);
            }
        });
    }
}
