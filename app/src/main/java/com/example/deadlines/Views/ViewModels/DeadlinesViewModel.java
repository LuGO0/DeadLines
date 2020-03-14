package com.example.deadlines.Views.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.deadlines.Repositories.DeadlinesRepository;
import com.example.deadlines.models.ProjectDeadline;

import java.util.List;

public class DeadlinesViewModel extends AndroidViewModel {

    private DeadlinesRepository mRepository;

    private LiveData<List<ProjectDeadline>> mAllDeadlines;

    public DeadlinesViewModel (Application application) {
        super(application);
        mRepository = new DeadlinesRepository(application);
        mAllDeadlines = mRepository.getAllDeadlines();
    }

    //get all the words
    public LiveData<List<ProjectDeadline>> get() { return mAllDeadlines; }

    // creting a wrapper insert method which calls the repositories insert method
    // thus hiding the insert logic from the UI
    // ie from the UI we'll just use the data cached in the ViewModel
    // and not care about the logic at the end of it
    public void insert(ProjectDeadline deadline) { mRepository.insert(deadline); }

    //delAll
    public void deleteAll() {mRepository.deleteAll();}




}
