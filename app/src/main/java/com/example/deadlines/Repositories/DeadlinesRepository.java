package com.example.deadlines.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.deadlines.models.ProjectDeadline;

import java.util.List;

public class DeadlinesRepository {

    private DeadlinesDao mDeadlinesDao;
    private LiveData<List<ProjectDeadline>> mAllDeadlines;


    //constructor intializing the member variables and
    // getting a handle to the database
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
    public void insert (ProjectDeadline deadline) {
        new insertAsyncTask(mDeadlinesDao).execute(deadline);
    }

    //deleteAll query
    public void deleteAll()  {
        new deleteAllDeadlinesAsyncTask(mDeadlinesDao).execute();
    }


    // insertAsyncTask for inserting query to run in the background thread!!
    private static class insertAsyncTask extends AsyncTask<ProjectDeadline, Void, Void> {

        private DeadlinesDao mAsyncTaskDao;

        insertAsyncTask(DeadlinesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ProjectDeadline... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllDeadlinesAsyncTask extends AsyncTask<Void, Void, Void> {
        private DeadlinesDao mAsyncTaskDao;

        deleteAllDeadlinesAsyncTask(DeadlinesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    //deleting a single deadline async task
    private static class deleteDeadlineAsyncTask extends AsyncTask<ProjectDeadline, Void, Void> {
        private DeadlinesDao mAsyncTaskDao;

        deleteDeadlineAsyncTask(DeadlinesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ProjectDeadline... params) {
            mAsyncTaskDao.deleteDeadline(params[0]);
            return null;
        }
    }


}
