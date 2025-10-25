package ru.mirea.gracheva.domain.usecases.rings;

import java.util.List;
import ru.mirea.gracheva.domain.models.Ring;
import ru.mirea.gracheva.domain.repository.RingRepository;

public class GetRingsUseCase {

    private final RingRepository ringRepository;

    public GetRingsUseCase(RingRepository ringRepository) {
        this.ringRepository = ringRepository;
    }

    public List<Ring> execute() {
        return ringRepository.getRings();
    }
}

