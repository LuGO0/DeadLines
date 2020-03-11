package com.example.deadlines.Views.Activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deadlines.R;

public class DetailedProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_project_activity);

        //inflate something from the passed in intent to make sure everything is working fine
//        Bundle bundle=getIntent().getExtras();
//
//        TextView tempView=(TextView)findViewById(R.id.current_project_list_item_index);
//        tempView.setText(bundle.getInt("currentProjectListItemIndex"));

    }
}
