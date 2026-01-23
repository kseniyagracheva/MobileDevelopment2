package ru.mirea.gracheva.domain.repository.favoriets;

import java.util.List;

import ru.mirea.gracheva.domain.models.Ring;

public interface FavoriteRepository {
    interface FavoriteCallback<T>{
        public void onSuccess(T result);
        public void onError(Throwable e);
    }

    public  void addToFavorites(String userId, String ringId, FavoriteCallback<Void> callback);

    public void removeFromFavorites(String userId, String ringId, FavoriteCallback<Void> callback);

    public void getFavoriteRings(String userId, FavoriteCallback<List<Ring>> callback);

    public void isFavorite (String userId, String ringId, FavoriteCallback<Boolean> callback);
}
