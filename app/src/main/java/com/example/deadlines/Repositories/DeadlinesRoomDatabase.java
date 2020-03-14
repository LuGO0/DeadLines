package com.example.deadlines.Repositories;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.deadlines.models.ProjectDeadline;

@Database(entities = {ProjectDeadline.class},version=1,exportSchema=false)
public abstract class DeadlinesRoomDatabase extends RoomDatabase {
    public abstract DeadlinesDao deadlinesDao();

    private DeadlinesDao mDao;
    //this whole code makes sure that the WordRoomDatabase is singleton
    // since there is a synchronised call to the databasebuilder function
    private static DeadlinesRoomDatabase INSTANCE;

    static DeadlinesRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {

            //usually we pass in an instance inside the synchronised call
            // or we make a method synchronised so that we can call that method using a single thread
            // we can do this also since only 1 class object in java VM per class
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


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);

            // this line deletes the data when the app restarts
            // but now we have refactored the code so it persists
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final DeadlinesDao mDao;

        PopulateDbAsync(DeadlinesRoomDatabase db) {
            mDao = db.deadlinesDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            //if no words then initialize
//            if(mDao.getAnyDeadline().length<1) {
//                //do nothing maybe just go up with a toast but do nothing
//                //toast saying that the database is empty!!
//            }
            return null;
        }
    }
}