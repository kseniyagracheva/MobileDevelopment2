package ru.mirea.gracheva.data.storage.rings;

import java.util.List;

import ru.mirea.gracheva.domain.models.Ring;

public interface RingDataSource {

    public interface RingCallback<T>{
        public void onSuccess(T result);
        public void onError(Throwable error);
    }

    public void getRings(RingCallback<List<Ring>> callback);
}
