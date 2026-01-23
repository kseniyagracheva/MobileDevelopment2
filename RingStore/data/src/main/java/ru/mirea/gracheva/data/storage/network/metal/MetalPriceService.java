package ru.mirea.gracheva.data.storage.network.metal;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mirea.gracheva.domain.models.MetalPriceInfo;
import ru.mirea.gracheva.domain.repository.metalPriceInfo.GoldPriceInfoRepository;
import ru.mirea.gracheva.domain.repository.metalPriceInfo.PlatinumPriceInfoRepository;
import ru.mirea.gracheva.domain.repository.metalPriceInfo.SilverPriceInfoRepository;

public class MetalPriceService {
    private static MetalPriceApi api;


    static {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        clientBuilder.readTimeout(30, TimeUnit.SECONDS);
        clientBuilder.addInterceptor(new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY));

        OkHttpClient client = clientBuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nbrb.by/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(MetalPriceApi.class);
    }
    public static void getGoldPrice(GoldPriceInfoRepository.MetalPriceCallback callback) {
        Call<List<MetalPriceResponse>> call = api.getGoldPrices();

        call.enqueue(new Callback<List<MetalPriceResponse>>() {
            @Override
            public void onResponse(Call<List<MetalPriceResponse>> call,
                                   Response<List<MetalPriceResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    MetalPriceResponse gold = response.body().get(0);
                    MetalPriceInfo info = new MetalPriceInfo(
                            "Gold",
                            gold.officialRate,
                            gold.date.split("T")[0],
                            "https://i.postimg.cc/HnsWTRwG/Gold.jpg"
                    );
                    callback.onSuccess(info);
                } else {
                    callback.onError("Золото не найдено");
                }
            }

            @Override
            public void onFailure(Call<List<MetalPriceResponse>> call, Throwable t) {
                callback.onError("Ошибка сети: " + t.getMessage());
            }
        });
    }

    public static void getSilverPrice(SilverPriceInfoRepository.MetalPriceCallback callback) {
        Call<List<MetalPriceResponse>> call = api.getSilverPrices();

        call.enqueue(new Callback<List<MetalPriceResponse>>() {
            @Override
            public void onResponse(Call<List<MetalPriceResponse>> call,
                                   Response<List<MetalPriceResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    MetalPriceResponse silver = response.body().get(0);
                    MetalPriceInfo info = new MetalPriceInfo(
                            "Silver",
                            silver.officialRate,
                            silver.date.split("T")[0],
                            "https://i.postimg.cc/1XjSstR6/Silver.jpg"
                    );
                    callback.onSuccess(info);
                } else {
                    callback.onError("Серебро не найдено");
                }
            }

            @Override
            public void onFailure(Call<List<MetalPriceResponse>> call, Throwable t) {
                callback.onError("Ошибка сети: " + t.getMessage());
            }
        });
    }

    public static void getPlatinumPrice(PlatinumPriceInfoRepository.MetalPriceCallback callback) {
        Call<List<MetalPriceResponse>> call = api.getPlatinumPrices();

        call.enqueue(new Callback<List<MetalPriceResponse>>() {
            @Override
            public void onResponse(Call<List<MetalPriceResponse>> call,
                                   Response<List<MetalPriceResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    MetalPriceResponse platinum = response.body().get(0);
                    MetalPriceInfo info = new MetalPriceInfo(
                            "Platinum",
                            platinum.officialRate,
                            platinum.date.split("T")[0],
                            "https://i.postimg.cc/W37bLTJq/Platinum.jpg"
                    );
                    callback.onSuccess(info);
                } else {
                    callback.onError("Платина не найдена");
                }
            }

            @Override
            public void onFailure(Call<List<MetalPriceResponse>> call, Throwable t) {
                callback.onError("Ошибка сети: " + t.getMessage());
            }
        });
    }
}
