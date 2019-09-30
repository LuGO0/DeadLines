package com.example.deadlines.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.deadlines.Activities.DetailedProjectActivity;
import com.example.deadlines.Adapters.ProjectAdapter;
import com.example.deadlines.Project;
import com.example.deadlines.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CurrentProjectsFragment extends Fragment {




    //Data Scraping
    public class getWebText extends AsyncTask<Void,Void,ArrayList<Project>>
    {

        String words;
        ArrayList<String> newData = new ArrayList<>();
        ArrayList<Project> dummyProjectData=new ArrayList<Project>(5);


        @Override
        protected ArrayList<Project> doInBackground(Void... strings) {


            try {

                String s="";
                Document doc= Jsoup.connect("https://dst.gov.in/archive-call-for-proposals?page=6").get();
                Element table = doc.select("table").get(0); //select the first table.
                Elements rows = table.select("tr");

                for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                    Element row = rows.get(i);
                    Elements cols = row.select("td");
                   /* for (Element p : cols)
                    {

                   s+=p.text()+'\n';
                    }
                    newData.add(s);
                    s="";*/



                    Log.i("info","data check 1"+cols.get(0).text());
                    dummyProjectData.add(new Project(cols.get(0).text(),"DST GOV",cols.get(3).text()));}

                words=doc.text();
                Log.i("info","sdfghgfhjhgh"+words);



               /* Handler handler =  new Handler(CurrentProjectsFragment.this.getMainLooper());
                handler.post( new Runnable(){
                    public void run(){
                        //Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                        Toast.makeText(CurrentProjectsFragment.this,"mnbvcx",Toast.LENGTH_LONG).show();

                    }
                });*/




            } catch (IOException e) {
                e.printStackTrace();
                //Log.i("info","sdfghgfhjhgh"+words);

            }

            //Adding elements to project array list


            return dummyProjectData;
        }

       /* @Override
        protected void onPostExecute(ArrayList<String> s) {
            super.onPostExecute(s);

            //textView.setText(s.toString());

        }*/
    }






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
        ArrayList<Project> projectData=new ArrayList<Project>(5);
        /*dummyProjectData.add(new Project("Project title 1","ERPC","21st Aug, 2020"));
        dummyProjectData.add(new Project("Project title 2","IISC","21st Aug, 2021"));
        dummyProjectData.add(new Project("Project title 3","AICTE","21st Aug, 2021"));
        dummyProjectData.add(new Project("Project title 4","NZCD","21st Aug, 2022"));
        dummyProjectData.add(new Project("Project title 5","ERPC","21st Aug, 2023"));
        dummyProjectData.add(new Project("Project title 6","UGC","21st Aug, 2025"));
        dummyProjectData.add(new Project("Project title 7","AICTE","21st Aug, 2026"));
        dummyProjectData.add(new Project("Project title 8","NZEC","21st Aug, 2027"));*/

        try {
            projectData=new getWebText().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        final ListView projectListView = (ListView) view.findViewById(R.id.list);

        final ProjectAdapter adapter=new ProjectAdapter(getActivity(),projectData);

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
