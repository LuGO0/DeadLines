package com.example.deadlines.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.deadlines.Activities.DetailedProjectActivity;
import com.example.deadlines.Adapters.ProjectAdapter;
import com.example.deadlines.Project;
import com.example.deadlines.R;

import java.util.ArrayList;

public class UpcomingProjectsFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_current_projects, container, false);

        //dummy data for now
        ArrayList<Project> dummyProjectData=new ArrayList<Project>(5);
        dummyProjectData.add(new Project("Project title 1","ERPC","21st Aug, 2020"));
        dummyProjectData.add(new Project("Project title 2","IISC","21st Aug, 2021"));
        dummyProjectData.add(new Project("Project title 3","AICTE","21st Aug, 2021"));
        dummyProjectData.add(new Project("Project title 4","NZCD","21st Aug, 2022"));
        dummyProjectData.add(new Project("Project title 5","ERPC","21st Aug, 2023"));
        dummyProjectData.add(new Project("Project title 6","UGC","21st Aug, 2025"));
        dummyProjectData.add(new Project("Project title 7","AICTE","21st Aug, 2026"));
        dummyProjectData.add(new Project("Project title 8","NZEC","21st Aug, 2027"));


        final ListView projectListView = (ListView) view.findViewById(R.id.list);

        final ProjectAdapter adapter=new ProjectAdapter(getActivity(),dummyProjectData);

        projectListView.setAdapter(adapter);

        //setting up onClicklistener for listViewItem
        projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //the position here i signifies the index of the item clicked
                Project currentProject=adapter.getItem(i);

                //create an intent to a new activity which displays
                //project title , detailed description , findOutMore button
                Intent intent=new Intent(getActivity(), DetailedProjectActivity.class);

                Bundle bundle=new Bundle();
                bundle.putInt("currentProjectListItemIndex",i);
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });

        return view;
    }

}
