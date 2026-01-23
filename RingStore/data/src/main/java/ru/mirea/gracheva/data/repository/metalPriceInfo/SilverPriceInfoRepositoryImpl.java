package ru.mirea.gracheva.data.repository.metalPriceInfo;

import ru.mirea.gracheva.data.storage.network.metal.MetalPriceService;
import ru.mirea.gracheva.domain.repository.metalPriceInfo.SilverPriceInfoRepository;

public class SilverPriceInfoRepositoryImpl implements SilverPriceInfoRepository {

    @Override
    public void getSilverPriceInfo(final MetalPriceCallback callback) {
        MetalPriceService.getSilverPrice(callback);
    }

    /*@Override
    public void getSilverPriceInfo(final MetalPriceCallback callback) {
        MetalPriceInfo mockInfo = new MetalPriceInfo("Silver", 129.1800, "22.11.2025"	);
        callback.onSuccess(mockInfo);
    }*/
}
