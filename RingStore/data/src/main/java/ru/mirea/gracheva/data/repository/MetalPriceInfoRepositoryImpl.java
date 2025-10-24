package ru.mirea.gracheva.data.repository;


import ru.mirea.gracheva.domain.models.MetalPriceInfo;
import ru.mirea.gracheva.domain.repository.MetalPriceInfoRepository;

public class MetalPriceInfoRepositoryImpl implements MetalPriceInfoRepository {

    @Override
    public void getMetalPriceInfo(final MetalPriceCallback callback) {
        MetalPriceInfo mockInfo = new MetalPriceInfo("Gold", 1923.45, "USD", "2025-10-04 10:00");
        callback.onSuccess(mockInfo);
    }
}

