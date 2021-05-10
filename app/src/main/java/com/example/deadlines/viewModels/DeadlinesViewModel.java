package com.example.deadlines.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.deadlines.room.repository.DeadlinesRepository;
import com.example.deadlines.room.models.ProjectDeadline;

import java.util.List;

public class DeadlinesViewModel extends AndroidViewModel {
    private DeadlinesRepository deadlinesRepository;
    private LiveData<List<ProjectDeadline>> deadlines;

    public DeadlinesViewModel(Application application) {
        super(application);
        deadlinesRepository = new DeadlinesRepository(application);
        deadlines = deadlinesRepository.getAllDeadlines();
    }

    public LiveData<List<ProjectDeadline>> get() {
        return deadlines;
    }

    public void insert(ProjectDeadline deadline) {
        deadlinesRepository.insert(deadline);
    }

    public void deleteAll() {
        deadlinesRepository.deleteAll();
    }
}
