package ru.mirea.gracheva.domain.domain.usecases;

import ru.mirea.gracheva.ringstore.domain.repository.MetalPriceInfoRepository;

public class GetMetalPriceInfo {
    private MetalPriceInfoRepository metalPriceInfoRepository;

    public GetMetalPriceInfo(MetalPriceInfoRepository metalPriceInfoRepository) {
        this.metalPriceInfoRepository = metalPriceInfoRepository;
    }

    public void fetchMetalPriceInfo(MetalPriceInfoRepository.MetalPriceCallback callback) {
        metalPriceInfoRepository.getMetalPriceInfo(callback);
    }
}

