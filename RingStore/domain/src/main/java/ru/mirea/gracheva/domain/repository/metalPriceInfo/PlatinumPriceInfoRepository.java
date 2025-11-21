package ru.mirea.gracheva.domain.repository.metalPriceInfo;

import ru.mirea.gracheva.domain.models.MetalPriceInfo;

public interface PlatinumPriceInfoRepository {
    void getPlatinumPriceInfo(MetalPriceCallback callback); ///метод запрашивает данные с сервера, данные в колбэке интерфейса для асинхронности, ничего не возвращает

    interface MetalPriceCallback {
        void onSuccess(MetalPriceInfo metalPriceInfo);
        void onError(String errorMessage);
    }///вложенный интерфейс, успех - возвращает данные, ошибка - сообщение
}
