package ru.mirea.gracheva.data.storage.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import ru.mirea.gracheva.data.DTO.RingEntity;
import ru.mirea.gracheva.data.storage.room.dao.RingDao;

@Database(entities = {RingEntity.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RingDao ringDao();
}
