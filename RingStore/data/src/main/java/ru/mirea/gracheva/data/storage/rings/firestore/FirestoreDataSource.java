package ru.mirea.gracheva.data.storage.rings.firestore;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.gracheva.data.storage.rings.RingDataSource;
import ru.mirea.gracheva.domain.models.Ring;

public class FirestoreDataSource implements RingDataSource {
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    public void getRings(RingDataSource.RingCallback<List<Ring>> callback){
        firestore.collection("rings")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Ring> rings = new ArrayList<>();
                    for (DocumentSnapshot doc : querySnapshot){
                        String ringId = doc.getId();
                        String name = doc.getString("name");
                        String metal = doc.getString("metal");
                        Long priceLong =  doc.getLong("price");
                        Integer price = priceLong != null ? priceLong.intValue() : 0;
                        String imageUrl = doc.getString("imageUrl");

                        rings.add(new Ring(ringId, name, metal, price, imageUrl));
                    }
                    callback.onSuccess(rings);
                })
                .addOnFailureListener(e -> callback.onError(e));
    }
}
