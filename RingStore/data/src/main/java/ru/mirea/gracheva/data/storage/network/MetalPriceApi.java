package ru.mirea.gracheva.data.storage.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MetalPriceApi {
    @GET("ingots/prices/0")  // Цены золота
    Call<List<MetalPriceResponse>> getGoldPrices();

    @GET("ingots/prices/1")  // Цены серебра
    Call<List<MetalPriceResponse>> getSilverPrices();

    @GET("ingots/prices/2")  // Цены платины
    Call<List<MetalPriceResponse>> getPlatinumPrices();
}

