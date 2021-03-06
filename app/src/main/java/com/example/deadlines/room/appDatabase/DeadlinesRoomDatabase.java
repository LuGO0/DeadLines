package com.example.deadlines.room.appdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.deadlines.room.dao.DeadlinesDao;
import com.example.deadlines.room.models.ProjectDeadline;

@Database(entities = {ProjectDeadline.class}, version = 1, exportSchema = false)
public abstract class DeadlinesRoomDatabase extends RoomDatabase {

    private static DeadlinesRoomDatabase INSTANCE;
    private DeadlinesDao deadlinesDao;

    public static DeadlinesRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DeadlinesRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DeadlinesRoomDatabase.class, "deadlines_table")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract DeadlinesDao deadlinesDao();
}