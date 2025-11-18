package ru.mirea.gracheva.data.storage.room;

import android.content.Context;

import androidx.room.Room;

public class DatabaseProvider {
    private static AppDatabase instance;

    private DatabaseProvider() {
        // приватный конструктор, чтобы запретить создание экземпляров
    }

    public static synchronized AppDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .build();
            new Thread(() -> instance.clearAllTables()).start();
        }
        return instance;
    }
}
