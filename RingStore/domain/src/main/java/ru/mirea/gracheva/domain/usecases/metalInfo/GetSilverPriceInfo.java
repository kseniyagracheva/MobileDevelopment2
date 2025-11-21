package ru.mirea.gracheva.domain.usecases.metalInfo;

import ru.mirea.gracheva.domain.repository.metalPriceInfo.SilverPriceInfoRepository;

public class GetSilverPriceInfo {
    private SilverPriceInfoRepository silverPriceInfoRepository;

    public GetSilverPriceInfo( SilverPriceInfoRepository silverPriceInfoRepository) {
        this.silverPriceInfoRepository = silverPriceInfoRepository;
    }

    public void fetchMetalPriceInfo(SilverPriceInfoRepository.MetalPriceCallback callback) {
        silverPriceInfoRepository.getSilverPriceInfo(callback);
    }
}
