package ru.mirea.gracheva.domain.usecases.metalInfo;

import ru.mirea.gracheva.domain.repository.metalPriceInfo.GoldPriceInfoRepository;

public class GetGoldPriceInfo {
    private GoldPriceInfoRepository goldPriceInfoRepository;

    public GetGoldPriceInfo(GoldPriceInfoRepository goldPriceInfoRepository) {
        this.goldPriceInfoRepository = goldPriceInfoRepository;
    }

    public void fetchMetalPriceInfo(GoldPriceInfoRepository.MetalPriceCallback callback) {
        goldPriceInfoRepository.getGoldPriceInfo(callback);
    }
}

