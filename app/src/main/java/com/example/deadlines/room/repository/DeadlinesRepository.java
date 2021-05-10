package com.example.deadlines.room.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.deadlines.room.appDatabase.DeadlinesRoomDatabase;
import com.example.deadlines.room.models.ProjectDeadline;
import com.example.deadlines.room.dao.DeadlinesDao;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.deadlines.utils.ApplicationsConstants.CUSTOM_THREAD_POOL_SIZE;

public class DeadlinesRepository {
    private DeadlinesDao deadlinesDao;
    private LiveData<List<ProjectDeadline>> allDeadlines;

    /**
     * constructor initializing the member variables and
     * getting a handle to the database
     */
    public DeadlinesRepository(Application application) {
        DeadlinesRoomDatabase db = DeadlinesRoomDatabase.getDatabase(application);
        deadlinesDao = db.deadlinesDao();
        allDeadlines = deadlinesDao.getAllDeadlines();
    }

    //returning all the deadline cached in the database as livedata
    public LiveData<List<ProjectDeadline>> getAllDeadlines() {
        scrapeDeadlines();
        allDeadlines = deadlinesDao.getAllDeadlines();
        return allDeadlines;
    }

    //inserting query
    public void insert(ProjectDeadline deadline) {
        new InsertAsyncTask(deadlinesDao).execute(deadline);
    }

    //deleteAll query
    public void deleteAll() {
        new DeleteAllDeadlinesAsyncTask(deadlinesDao).execute();
    }

    public void scrapeDeadlines() {
        String words;
        ArrayList<String> newData = new ArrayList<>();
        ArrayList<ProjectDeadline> dummyProjectDeadlineData = new ArrayList<ProjectDeadline>();

        ExecutorService executorService = Executors.newFixedThreadPool(CUSTOM_THREAD_POOL_SIZE);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                scrapeDst();
                scrapeDbt();
            }
        });
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

    private void scrapeDbt() {
        try {

            String s = "";
            Document doc = Jsoup.connect("http://dbtindia.gov.in/whats-new/call-for-proposals").get();
            Element table = doc.select("table").get(0); //select the first table.
            Elements rows = table.select("tr");

            for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                Element row = rows.get(i);

                Elements cols = row.select("td");
                Elements links = cols.select("a");

                String dateString = cols.get(2).text().split(" ")[2];
                int year = Integer.parseInt(dateString.substring(6, 10));
                deadlinesDao.insert(new ProjectDeadline(cols.get(1).text(), "DBT", dateString, links.attr("href")));

            }

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    private void scrapeDst() {
        try {

            String s = "";
            Document doc = Jsoup.connect("https://dst.gov.in/call-for-proposals").get();
            Element table = doc.select("table").get(0); //select the first table.
            Elements rows = table.select("tr");

            for (int i = 1; i < rows.size(); i++) {
                //first row is the col names so skip it.
                Element row = rows.get(i);

                Elements cols = row.select("td");
                Elements links = cols.select("a");

                String dateString = cols.get(3).text();
                int year = Integer.parseInt(dateString.substring(6, 10));

                deadlinesDao.insert(new ProjectDeadline(cols.get(0).text(), "DST GOV/EPMS", cols.get(3).text(), "https://dst.gov.in" + links.attr("href")));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
