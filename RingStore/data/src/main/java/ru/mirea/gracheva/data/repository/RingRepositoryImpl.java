package ru.mirea.gracheva.data.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ru.mirea.gracheva.data.DTO.RingEntity;
import ru.mirea.gracheva.data.storage.room.AppDatabase;
import ru.mirea.gracheva.data.storage.room.DatabaseProvider;
import ru.mirea.gracheva.data.storage.room.dao.RingDao;
import ru.mirea.gracheva.domain.models.Ring;
import ru.mirea.gracheva.domain.repository.NetworkApi;
import ru.mirea.gracheva.domain.repository.RingRepository;

public class RingRepositoryImpl implements RingRepository {

    private final RingDao ringDao;
    private final NetworkApi networkApi;

    Executor executor = Executors.newSingleThreadExecutor();

    public RingRepositoryImpl(Context context, NetworkApi networkApi) {
        AppDatabase db = DatabaseProvider.getDatabase(context);
        ringDao = db.ringDao();
        this.networkApi = networkApi;

        new Thread(() -> {
            if (ringDao.getAllRings().isEmpty()) {
                List<Ring> ringsFromNetwork = networkApi.fetchRings();

                List<RingEntity> entities = new ArrayList<>();
                for (Ring ring : ringsFromNetwork) {
                    entities.add(new RingEntity(ring.getRingId(), ring.getMetal(), ring.getPrice()));
                }

                ringDao.insertRings(entities);
            }
        }).start();
    }

    @Override
    public void getRings(Callback callback) {
        executor.execute(()-> {
            try{
                List<RingEntity> ringEntities = ringDao.getAllRings();
                List<Ring> rings = new ArrayList<>();
                for (RingEntity entity : ringEntities) {
                    rings.add(new Ring(entity.getRingId(), entity.getMetal(), entity.getPrice()));
                }
                callback.onSuccess(rings);
            }
            catch (Exception e){
                callback.onError(e);
            }
        });
    }

}
