package ru.mirea.gracheva.data.repository.metalPriceInfo;


import ru.mirea.gracheva.domain.models.MetalPriceInfo;
import ru.mirea.gracheva.domain.repository.metalPriceInfo.GoldPriceInfoRepository;

public class GoldPriceInfoRepositoryImpl implements GoldPriceInfoRepository {

    @Override
    public void getGoldPriceInfo(final MetalPriceCallback callback) {
        MetalPriceInfo mockInfo = new MetalPriceInfo("Gold", 10392.9800, "22.11.2025");
        callback.onSuccess(mockInfo);
    }
}

