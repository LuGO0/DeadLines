package com.example.deadlines;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //for now well use the dummy data




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Project> dummyProjectData=new ArrayList<Project>(5);
        dummyProjectData.add(new Project("Project title 1","ERPC","21st Aug, 2020"));
        dummyProjectData.add(new Project("Project title 2","IISC","21st Aug, 2021"));
        dummyProjectData.add(new Project("Project title 3","AICTE","21st Aug, 2021"));
        dummyProjectData.add(new Project("Project title 4","NZCD","21st Aug, 2022"));
        dummyProjectData.add(new Project("Project title 5","ERPC","21st Aug, 2023"));
        dummyProjectData.add(new Project("Project title 6","UGC","21st Aug, 2025"));
        dummyProjectData.add(new Project("Project title 7","AICTE","21st Aug, 2026"));
        dummyProjectData.add(new Project("Project title 8","NZEC","21st Aug, 2027"));


        ListView projectListView = (ListView) findViewById(R.id.list);

        ProjectAdapter adapter=new ProjectAdapter(this,dummyProjectData);

        projectListView.setAdapter(adapter);
    }
}
