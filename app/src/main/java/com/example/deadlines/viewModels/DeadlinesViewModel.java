package com.example.deadlines.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.deadlines.room.repository.DeadlinesRepository;
import com.example.deadlines.room.models.ProjectDeadline;

import java.util.List;

public class DeadlinesViewModel extends AndroidViewModel {
    private DeadlinesRepository mRepository;
    private LiveData<List<ProjectDeadline>> mAllDeadlines;

    public DeadlinesViewModel(Application application) {
        super(application);
        mRepository = new DeadlinesRepository(application);
        mAllDeadlines = mRepository.getAllDeadlines();
    }

    public LiveData<List<ProjectDeadline>> get() {
        return mAllDeadlines;
    }

    public void insert(ProjectDeadline deadline) {
        mRepository.insert(deadline);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}
