//package ru.mirea.gracheva.data.storage.favorites;
//
//import java.util.List;
//
//import ru.mirea.gracheva.domain.models.Ring;
//import ru.mirea.gracheva.domain.repository.favoriets.FavoriteRepository;
//
//public interface FavoritesDataSource {
//
//    public interface FavCallback<T>{
//        public void onSuccess(T result);
//        public void onError(Throwable error);
//    }
//
//    public void addToFavorites(String userId, String ringId, FavCallback<Void> callback);
//
//    public void removeFromFavorites(String userId, String ringId, FavCallback<Void> callback);
//
//    public void getFavoriteRings(String userId, FavCallback<List<String>> callback);
//
//    public void isFavorite (String userId, String ringId, FavCallback<Boolean> callback);
//}
