package ru.mirea.gracheva.domain.domain.repository;

import ru.mirea.gracheva.domain.domain.models.MetalPriceInfo;

public interface MetalPriceInfoRepository {
    void getMetalPriceInfo(MetalPriceCallback callback); ///метод запрашивает данные с сервера, данные в колбэке интерфейса для асинхронности, ничего не возвращает

    interface MetalPriceCallback {
        void onSuccess(MetalPriceInfo metalPriceInfo);
        void onError(String errorMessage);
    }///вложенный интерфейс, успех - возвращает данные, ошибка - сообщение
}
