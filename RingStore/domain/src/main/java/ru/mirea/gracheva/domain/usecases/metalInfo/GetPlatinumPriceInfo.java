package ru.mirea.gracheva.domain.usecases.metalInfo;

import ru.mirea.gracheva.domain.repository.metalPriceInfo.PlatinumPriceInfoRepository;

public class GetPlatinumPriceInfo {
    private PlatinumPriceInfoRepository platinumPriceInfoRepository;

    public GetPlatinumPriceInfo(PlatinumPriceInfoRepository platinumPriceInfoRepository) {
        this.platinumPriceInfoRepository = platinumPriceInfoRepository;
    }

    public void fetchMetalPriceInfo(PlatinumPriceInfoRepository.MetalPriceCallback callback) {
        platinumPriceInfoRepository.getPlatinumPriceInfo(callback);
    }
}
