package ru.mirea.gracheva.domain.repository;

import java.util.List;

import ru.mirea.gracheva.domain.models.Ring;

public interface RingRepository {
    public void getRings(Callback callback);
    public void getRingById(String ringId, Callback<Ring> callback);
    //void getRingsByIds(List<String> ringIds, Callback<List<Ring>> callback);

    public interface Callback<T>{
        public void onSuccess(T result);
        public void onError(Throwable t);
    }
}
