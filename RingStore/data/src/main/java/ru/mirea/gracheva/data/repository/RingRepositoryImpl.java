package ru.mirea.gracheva.data.repository;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ru.mirea.gracheva.data.DTO.RingEntity;
import ru.mirea.gracheva.data.storage.rings.RingDataSource;
import ru.mirea.gracheva.data.storage.room.AppDatabase;
import ru.mirea.gracheva.data.storage.room.DatabaseProvider;
import ru.mirea.gracheva.data.storage.room.dao.RingDao;
import ru.mirea.gracheva.domain.models.Ring;
import ru.mirea.gracheva.domain.repository.RingRepository;

public class RingRepositoryImpl implements RingRepository {

    private final RingDao ringDao;
    //private final NetworkApi networkApi;
    private final RingDataSource ringDataSource;
    Executor executor = Executors.newSingleThreadExecutor();

    public RingRepositoryImpl(Context context, RingDataSource ringDataSource) {
        AppDatabase db = DatabaseProvider.getDatabase(context);
        ringDao = db.ringDao();
        this.ringDataSource = ringDataSource;

        loadRingsFromFireStore();//Загрузка из FireStore при условии, что Room пустой
    }

    @Override
    public void getRings(Callback callback) {
        executor.execute(()-> {
            try{
                List<RingEntity> ringEntities = ringDao.getAllRings();
                List<Ring> rings = new ArrayList<>();
                for (RingEntity entity : ringEntities) {
                    rings.add(new Ring(
                        entity.getRingId(),
                        entity.getName(),
                        entity.getMetal(),
                        entity.getPrice(),
                        entity.getImageUrl()
                    ));
                }
                callback.onSuccess(rings);
            }
            catch (Exception e){
                callback.onError(e);
            }
        });
    }

    @Override
    public void getRingById(String ringId, Callback<Ring> callback){
        executor.execute(() ->{
            try {
                RingEntity entity = ringDao.getRingById(ringId);
                if (entity != null){
                    Ring ring = new Ring(
                        entity.getRingId(),
                        entity.getName(),
                        entity.getMetal(),
                        entity.getPrice(),
                        entity.getImageUrl()
                    );
                    callback.onSuccess(ring);
                }
                else{
                    callback.onError(new Throwable("Кольцо не найдено!"));
                }
            } catch (Exception e){
                callback.onError(e);
            }
        });
    }


    private void loadRingsFromFireStore(){
        executor.execute(() ->{
            if (ringDao.getAllRings().isEmpty()){
                ringDataSource.getRings(new RingDataSource.RingCallback<List<Ring>>() {
                    @Override
                    public void onSuccess(List<Ring> rings) {
                        executor.execute(() ->{
                            ringDao.clearRings();
                            List<RingEntity> entities = new ArrayList<>();
                            for (Ring ring : rings){
                                entities.add(new RingEntity(
                                        ring.getRingId(),
                                        ring.getName(),
                                        ring.getMetal(),
                                        ring.getPrice(),
                                        ring.getImageUrl()
                                ));
                            }
                            ringDao.insertRings(entities);
                        });
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e("RingRepository", "Firestore error: " + error.getMessage());
                    }
                });
            }
        });

    }
}
