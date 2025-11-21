package ru.mirea.gracheva.data.repository.metalPriceInfo;

import ru.mirea.gracheva.domain.models.MetalPriceInfo;
import ru.mirea.gracheva.domain.repository.metalPriceInfo.PlatinumPriceInfoRepository;

public class PlatinumPriceInfoRepositoryImpl implements PlatinumPriceInfoRepository {
    @Override
    public void getPlatinumPriceInfo(final MetalPriceCallback callback) {
        MetalPriceInfo mockInfo = new MetalPriceInfo("Platinum", 3925.3800	, "22.11.2025");
        callback.onSuccess(mockInfo);
    }
}
