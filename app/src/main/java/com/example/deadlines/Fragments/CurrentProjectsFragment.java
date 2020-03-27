package com.example.deadlines.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deadlines.Utils.Utils;
import com.example.deadlines.Views.Adapters.ProjectDeadlineAdapter;
import com.example.deadlines.Views.ViewModels.DeadlinesViewModel;
import com.example.deadlines.models.ProjectDeadline;
import com.example.deadlines.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CurrentProjectsFragment extends Fragment {

    private DeadlinesViewModel deadlinesViewModel;

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
                    Log.i("yp",Utils.getDatefromString(dateString).toString());
                    int year = Integer.parseInt(dateString.substring(6, 10));

                    deadlinesViewModel.insert(new ProjectDeadline(cols.get(0).text(), "DST GOV/EPMS", cols.get(3).text(),"https://dst.gov.in"+links.attr("href")));
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
                    deadlinesViewModel.insert(new ProjectDeadline(cols.get(1).text(), "DBT", dateString,links.attr("href")));

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
        deadlinesViewModel= ViewModelProviders.of(this).get(DeadlinesViewModel.class);
        //dummy data for now
        ArrayList<ProjectDeadline> projectDeadlineData =new ArrayList<ProjectDeadline>();
        ArrayList<ProjectDeadline> extra;

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

        final RecyclerView projectListView = (RecyclerView) view.findViewById(R.id.list);

        final ProjectDeadlineAdapter adapter=new ProjectDeadlineAdapter(projectDeadlineData);

        projectListView.setAdapter(adapter);
        projectListView.setLayoutManager(new LinearLayoutManager(getContext()));


        deadlinesViewModel.get().observe(this, new Observer<List<ProjectDeadline>>() {
            @Override
            public void onChanged(List<ProjectDeadline> projectDeadlines) {
                adapter.setNotes(projectDeadlines);
                Log.i("Yo",projectDeadlines.toString());

            }
        });

        //setting up onClicklistener for listViewItem
//        projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                final ProjectDeadline currentProjectDeadline =adapter.getItem(i);
//
//
//                if (currentProjectDeadline.getSourceWebsite().equalsIgnoreCase("DBT"))
//                {
//
//                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
//                    builder.setTitle("You are being redirected to DBT download page").setMessage("Do you want to download").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent viewIntent=new Intent(getActivity(), DetailedProjectActivity.class);
//                            viewIntent =
//                                    new Intent("android.intent.action.VIEW",
//                                            Uri.parse(currentProjectDeadline.getRedirectingUrl()));
//                            startActivity(viewIntent);
//                        }
//                    }).setNegativeButton("No",null);
//                    AlertDialog alert=builder.create();
//                    alert.show();
//
//
//                }
//                else {
//                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
//                    builder.setTitle("You are being redirected to DST proposal site").setMessage("Do you want to proceed").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent viewIntent=new Intent(getActivity(), DetailedProjectActivity.class);
//                            viewIntent =
//                                    new Intent("android.intent.action.VIEW",
//                                            Uri.parse(currentProjectDeadline.getRedirectingUrl()));
//                            startActivity(viewIntent);
//                        }
//                    }).setNegativeButton("No",null);
//                    AlertDialog alert=builder.create();
//                    alert.show();
//
//                }
//            }
//
//        });

        return view;
    }

}

