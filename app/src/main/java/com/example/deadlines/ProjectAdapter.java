package com.example.deadlines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProjectAdapter extends ArrayAdapter<Project> {

    public ProjectAdapter(Context context, ArrayList<Project> dummyProjectData) {
        super(context, 0, dummyProjectData);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.project_list_item, parent, false);
        }

        //get item is an inbuilt class which will extract the required info from the
        //arraylist at the required position
        Project currentProject = getItem(position);

        //extract attributes from the position attribute
        String sourceWebsite = currentProject.getSourceWebsite();
        String projectTitle = currentProject.getProjectTitle();
        String deadlineDate = currentProject.getDeadlineDate();

        TextView sourceWebsiteView = (TextView) listItemView.findViewById(R.id.source_website);
        sourceWebsiteView.setText(sourceWebsite);

        TextView projectTitleView = (TextView) listItemView.findViewById(R.id.project_title);
        projectTitleView.setText(projectTitle);

        TextView deadlineDateView = (TextView) listItemView.findViewById(R.id.deadline_date);
        deadlineDateView.setText(deadlineDate);

        return listItemView;


    }
}