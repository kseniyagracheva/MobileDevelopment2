package ru.mirea.gracheva.ringstore.data.repository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mirea.gracheva.ringstore.domain.models.MetalPriceInfo;
import ru.mirea.gracheva.ringstore.domain.repository.MetalPriceInfoRepository;

public class MetalPriceInfoRepositoryImpl implements MetalPriceInfoRepository {

    @Override
    public void getMetalPriceInfo(final MetalPriceCallback callback) {
        MetalPriceInfo mockInfo = new MetalPriceInfo("Gold", 1923.45, "USD", "2025-10-04 10:00");
        callback.onSuccess(mockInfo);
    }
}

