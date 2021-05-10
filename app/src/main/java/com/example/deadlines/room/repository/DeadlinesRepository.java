package com.example.deadlines.room.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.deadlines.room.appDatabase.DeadlinesRoomDatabase;
import com.example.deadlines.room.models.ProjectDeadline;
import com.example.deadlines.room.dao.DeadlinesDao;

import java.util.List;

public class DeadlinesRepository {
    private DeadlinesDao mDeadlinesDao;
    private LiveData<List<ProjectDeadline>> mAllDeadlines;

    /**
     * constructor initializing the member variables and
     * getting a handle to the database
     */
    public DeadlinesRepository(Application application) {
        DeadlinesRoomDatabase db = DeadlinesRoomDatabase.getDatabase(application);
        mDeadlinesDao = db.deadlinesDao();
        mAllDeadlines = mDeadlinesDao.getAllDeadlines();
    }

    //returning all the deadline cached in the database as livedata
    public LiveData<List<ProjectDeadline>> getAllDeadlines() {
        return mAllDeadlines;
    }

    //inserting query
    public void insert(ProjectDeadline deadline) {
        new InsertAsyncTask(mDeadlinesDao).execute(deadline);
    }

    //deleteAll query
    public void deleteAll() {
        new DeleteAllDeadlinesAsyncTask(mDeadlinesDao).execute();
    }

    // insertAsyncTask for inserting query to run in the background thread!!
    private static class InsertAsyncTask extends AsyncTask<ProjectDeadline, Void, Void> {
        private DeadlinesDao mAsyncTaskDao;

        InsertAsyncTask(DeadlinesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ProjectDeadline... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class DeleteAllDeadlinesAsyncTask extends AsyncTask<Void, Void, Void> {
        private DeadlinesDao mAsyncTaskDao;

        DeleteAllDeadlinesAsyncTask(DeadlinesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class DeleteDeadlineAsyncTask extends AsyncTask<ProjectDeadline, Void, Void> {
        private DeadlinesDao mAsyncTaskDao;

        DeleteDeadlineAsyncTask(DeadlinesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ProjectDeadline... params) {
            mAsyncTaskDao.deleteDeadline(params[0]);
            return null;
        }
    }
}
