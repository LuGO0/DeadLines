package com.example.deadlines.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.deadlines.Utils.Utils;
import com.example.deadlines.Views.Activities.DetailedProjectActivity;
import com.example.deadlines.Views.Adapters.ProjectAdapter;
import com.example.deadlines.models.ProjectDeadline;
import com.example.deadlines.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class CurrentProjectsFragment extends Fragment {


    //Data Scraping
    public class getWebText extends AsyncTask<Void, Void, ArrayList<ProjectDeadline>> {

        String words;
        ArrayList<String> newData = new ArrayList<>();
        ArrayList<ProjectDeadline> dummyProjectDeadlineData = new ArrayList<ProjectDeadline>();


        @Override
        protected ArrayList<ProjectDeadline> doInBackground(Void... strings) {


            try {

                String s = "";
                Document doc = Jsoup.connect("https://dst.gov.in/call-for-proposals").get();
                Element table = doc.select("table").get(0); //select the first table.
                Elements rows = table.select("tr");

                for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                    Element row = rows.get(i);
                    Elements cols = row.select("td");
                    Elements links=cols.select("a");

                    String dateString = cols.get(3).text();
                    Log.i("yp",Utils.getDatefromString(dateString).toString());                    int year = Integer.parseInt(dateString.substring(6, 10));
                    if (year > 2019)
                        continue;

                    if ((Integer.parseInt(dateString.substring(3, 5)) <= 11)) {
                        //Log.i("info", "data check 1" + cols.get(3).text());
                        dummyProjectDeadlineData.add(new ProjectDeadline(cols.get(0).text(), "DST GOV/EPMS", cols.get(3).text(),"https://dst.gov.in"+links.attr("href")));

                    }
                }

                words = doc.text();

            } catch (IOException e) {
                e.printStackTrace();

            } catch (ParseException e) {
                e.printStackTrace();
            }

            return dummyProjectDeadlineData;
        }
    }

    public class getWebText1 extends AsyncTask<Void, Void, ArrayList<ProjectDeadline>> {
        String words;
        ArrayList<String> newData = new ArrayList<>();
        ArrayList<ProjectDeadline> dummyProjectDeadlineData = new ArrayList<ProjectDeadline>(5);

        @Override
        protected ArrayList<ProjectDeadline> doInBackground(Void... voids) {

            try {

                String s = "";
                Document doc = Jsoup.connect("http://dbtindia.gov.in/whats-new/call-for-proposals").get();
                Element table = doc.select("table").get(0);
                //select the first table.
                Elements rows = table.select("tr");

                for (int i = 1; i < rows.size(); i++) {
                    //first row is the col names so skip it.
                    Element row = rows.get(i);
                    Elements cols = row.select("td");
                    Elements links=cols.select("a");
                    String dateString = cols.get(2).text().split(" ")[2];
                    Log.i("yp",Utils.getDatefromString(dateString).toString());

                    int year = Integer.parseInt(dateString.substring(6, 10));

                    if (year > 2019)
                        continue;
                    if ((Integer.parseInt(dateString.substring(3, 5)) <= 11)) {
                        dummyProjectDeadlineData.add(new ProjectDeadline(cols.get(1).text(), "DBT", dateString,links.attr("href")));
                        Log.i("URL", dummyProjectDeadlineData.toString());
                    }
                }

                words = doc.text();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return dummyProjectDeadlineData;
        }
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
        ArrayList<ProjectDeadline> projectDeadlineData =new ArrayList<ProjectDeadline>();
        ArrayList<ProjectDeadline> extra=new ArrayList<ProjectDeadline>();
        /*dummyProjectData.add(new Project("Project title 1","ERPC","21st Aug, 2020"));
        dummyProjectData.add(new Project("Project title 2","IISC","21st Aug, 2021"));
        dummyProjectData.add(new Project("Project title 3","AICTE","21st Aug, 2021"));
        dummyProjectData.add(new Project("Project title 4","NZCD","21st Aug, 2022"));
        dummyProjectData.add(new Project("Project title 5","ERPC","21st Aug, 2023"));
        dummyProjectData.add(new Project("Project title 6","UGC","21st Aug, 2025"));
        dummyProjectData.add(new Project("Project title 7","AICTE","21st Aug, 2026"));
        dummyProjectData.add(new Project("Project title 8","NZEC","21st Aug, 2027"));*/

        try {
            projectDeadlineData =new getWebText().execute().get();
            extra=new getWebText1().execute().get();
            for(int i=0;i<extra.size();i++)
            {
                projectDeadlineData.add(extra.get(i));
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        final ListView projectListView = (ListView) view.findViewById(R.id.list);
        Log.i("dataIfLoaded", projectDeadlineData.toString());
        final ProjectAdapter adapter=new ProjectAdapter(getActivity(), projectDeadlineData);

        projectListView.setAdapter(adapter);

        //setting up onClicklistener for listViewItem
        projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ProjectDeadline currentProjectDeadline =adapter.getItem(i);


                if (currentProjectDeadline.getSourceWebsite().equalsIgnoreCase("DBT"))
                {

                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle("You are being redirected to DBT download page").setMessage("Do you want to download").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent viewIntent=new Intent(getActivity(), DetailedProjectActivity.class);
                            viewIntent =
                                    new Intent("android.intent.action.VIEW",
                                            Uri.parse(currentProjectDeadline.getRedirectingUrl()));
                            startActivity(viewIntent);
                        }
                    }).setNegativeButton("No",null);
                    AlertDialog alert=builder.create();
                    alert.show();


                }
                else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle("You are being redirected to DST proposal site").setMessage("Do you want to proceed").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent viewIntent=new Intent(getActivity(), DetailedProjectActivity.class);
                            viewIntent =
                                    new Intent("android.intent.action.VIEW",
                                            Uri.parse(currentProjectDeadline.getRedirectingUrl()));
                            startActivity(viewIntent);
                        }
                    }).setNegativeButton("No",null);
                    AlertDialog alert=builder.create();
                    alert.show();

                }
            }

        });

        return view;
    }

}

