//package ru.mirea.gracheva.data.storage.favorites.firestore;
//
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import ru.mirea.gracheva.data.storage.favorites.FavoritesDataSource;
//
//public class FirestoreFavDataSource implements FavoritesDataSource {
//    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//
//    @Override
//    public void addToFavorites(String userId, String ringId, FavoritesDataSource.FavCallback<Void> callback){
//        firestore.collection("users").document(userId)
//                .collection("favorites").document(ringId)
//                .set(new HashMap<>())
//                .addOnSuccessListener(aVoid -> callback.onSuccess(null))
//                .addOnFailureListener(callback::onError);
//
//    }
//
//    @Override
//    public void removeFromFavorites(String userId, String ringId, FavoritesDataSource.FavCallback<Void> callback){
//        firestore.collection("users").document(userId)
//                .collection("favorites").document(ringId)
//                .delete()
//                .addOnSuccessListener(aVoid -> callback.onSuccess(null))
//                .addOnFailureListener(callback::onError);
//    }
//
//    @Override
//    public void getFavoriteRings(String userId, FavoritesDataSource.FavCallback<List<String>> callback){
//        firestore.collection("users").document(userId)
//                .collection("favorites")
//                .get()
//                .addOnSuccessListener(querySnapshot -> {
//                    List<String> favorites = new ArrayList<>();
//                    for (DocumentSnapshot doc : querySnapshot){
//                        favorites.add(doc.getId());
//                    }
//                    callback.onSuccess(favorites);
//                })
//                .addOnFailureListener(callback::onError);
//    }
//
//    @Override
//    public void isFavorite(String userId, String ringId, FavoritesDataSource.FavCallback<Boolean> callback){
//        firestore.collection("users").document(userId)
//                .collection("favorites")
//                .get()
//                .addOnSuccessListener(doc -> callback.onSuccess(doc.isEmpty()))
//                .addOnFailureListener(callback::onError);
//
//    }
//}
