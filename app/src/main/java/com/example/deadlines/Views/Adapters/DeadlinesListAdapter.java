package com.example.deadlines.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.deadlines.models.ProjectDeadline;
import com.example.deadlines.R;

import java.util.ArrayList;

public class DeadlinesListAdapter extends ArrayAdapter<ProjectDeadline> {

    public DeadlinesListAdapter(Context context, ArrayList<ProjectDeadline> dummyProjectDeadlineData) {
        super(context, 0, dummyProjectDeadlineData);

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
        ProjectDeadline currentProjectDeadline = getItem(position);

        //extract attributes from the position attribute
        String sourceWebsite = currentProjectDeadline.getSourceWebsite();
        String projectTitle = currentProjectDeadline.getProjectTitle();
        String deadlineDate = currentProjectDeadline.getDeadlineDate();

        TextView sourceWebsiteView = (TextView) listItemView.findViewById(R.id.source_website);
        sourceWebsiteView.setText(sourceWebsite);

        TextView projectTitleView = (TextView) listItemView.findViewById(R.id.project_title);
        projectTitleView.setText(projectTitle);

        TextView deadlineDateView = (TextView) listItemView.findViewById(R.id.deadline_date);
        deadlineDateView.setText(deadlineDate);

        return listItemView;


    }
}