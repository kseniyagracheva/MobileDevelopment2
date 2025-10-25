package ru.mirea.gracheva.data.storage.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ru.mirea.gracheva.data.DTO.RingEntity;

@Dao
public interface RingDao {

    @Query("SELECT * FROM rings")
    List<RingEntity> getAllRings();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRings(List<RingEntity> rings);

    @Query("DELETE FROM rings")
    void clearRings();
}

