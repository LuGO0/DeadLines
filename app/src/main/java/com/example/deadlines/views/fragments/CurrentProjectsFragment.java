package com.example.deadlines.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deadlines.adapters.ProjectDeadlineAdapter;
import com.example.deadlines.viewModels.DeadlinesViewModel;
import com.example.deadlines.room.models.ProjectDeadline;
import com.example.deadlines.R;

import java.util.List;

public class CurrentProjectsFragment extends Fragment {

    private DeadlinesViewModel deadlinesViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_current_projects, container, false);
        deadlinesViewModel= ViewModelProviders.of(this).get(DeadlinesViewModel.class);
        final RecyclerView projectListView = (RecyclerView) view.findViewById(R.id.list);

        final ProjectDeadlineAdapter adapter=new ProjectDeadlineAdapter(deadlinesViewModel.getDeadlines().getValue());

        projectListView.setAdapter(adapter);
        projectListView.setLayoutManager(new LinearLayoutManager(getContext()));


        deadlinesViewModel.getDeadlines().observe(this, new Observer<List<ProjectDeadline>>() {
            @Override
            public void onChanged(List<ProjectDeadline> projectDeadlines) {
                adapter.setNotes(projectDeadlines);
            }
        });

        return view;
    }

}

