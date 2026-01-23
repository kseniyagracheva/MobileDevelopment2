//package ru.mirea.gracheva.data.repository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import ru.mirea.gracheva.data.storage.favorites.FavoritesDataSource;
//import ru.mirea.gracheva.domain.models.Ring;
//import ru.mirea.gracheva.domain.repository.RingRepository;
//import ru.mirea.gracheva.domain.repository.favoriets.FavoriteRepository;
//
//public class FavoriteRepositoryImpl implements FavoriteRepository {
//    private final FavoritesDataSource favoritesDataSource;
//    private final RingRepository ringRepository;
//
//    public FavoriteRepositoryImpl(FavoritesDataSource favoritesDataSource, RingRepository ringRepository){
//        this.favoritesDataSource=favoritesDataSource;
//        this.ringRepository=ringRepository;
//    }
//
//    @Override
//    public void addToFavorites(String userId, String ringId, FavoriteCallback<Void> callback){
//        favoritesDataSource.addToFavorites(userId, ringId, new FavoritesDataSource.FavCallback<Void>() {
//            @Override
//            public void onSuccess(Void result) {
//                callback.onSuccess(result);
//            }
//
//            @Override
//            public void onError(Throwable error) {
//                callback.onError(error);
//            }
//        });
//    }
//
//    @Override
//    public void removeFromFavorites(String userId, String ringId, FavoriteCallback<Void> callback){
//        favoritesDataSource.removeFromFavorites(userId, ringId, new FavoritesDataSource.FavCallback<Void>() {
//            @Override
//            public void onSuccess(Void result) {
//                callback.onSuccess(result);
//            }
//
//            @Override
//            public void onError(Throwable error) {
//                callback.onError(error);
//            }
//        });
//    }
//
//    @Override
//    public void getFavoriteRings(String userId, FavoriteCallback<List<Ring>> callback){
//        favoritesDataSource.getFavoriteRings(userId, new FavoritesDataSource.FavCallback<List<String>>() {
//            @Override
//            public void onSuccess(List<String> favorites) {
//                if (favorites.isEmpty()){
//                    callback.onSuccess(new ArrayList<>());
//                    return;
//                }
//                ringRepository.getRingsByIds(favorites, new RingRepository.Callback<List<Ring>>() {
//                    @Override
//                    public void onSuccess(List<Ring> result) {
//                        callback.onSuccess(result);
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        callback.onError(t);
//                    }
//                });
//            }
//
//            @Override
//            public void onError(Throwable error) {
//                callback.onError(error);
//            }
//        });
//    }
//
//    @Override
//    public void isFavorite(String userId, String ringId, FavoriteCallback<Boolean> callback){
//        favoritesDataSource.isFavorite(userId, ringId, new FavoritesDataSource.FavCallback<Boolean>() {
//            @Override
//            public void onSuccess(Boolean result) {
//                callback.onSuccess(result);
//            }
//
//            @Override
//            public void onError(Throwable error) {
//                callback.onError(error);
//            }
//        });
//    }
//}
