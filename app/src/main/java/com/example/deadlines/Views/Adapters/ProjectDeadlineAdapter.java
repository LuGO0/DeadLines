package com.example.deadlines.Views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deadlines.R;
import com.example.deadlines.models.ProjectDeadline;

import java.util.ArrayList;
import java.util.List;

public class ProjectDeadlineAdapter extends RecyclerView.Adapter<ProjectDeadlineAdapter.ProjectDeadlineHolder> {
    private List<ProjectDeadline> mDeadlines = new ArrayList<>();
    private onItemClickListener listener;

    public ProjectDeadlineAdapter(List<ProjectDeadline> deadlines){
        mDeadlines=deadlines;
    }

    @NonNull
    @Override
    public ProjectDeadlineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //parent.getContext() =recyclerView

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_list_item, parent, false);
        return new ProjectDeadlineHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectDeadlineHolder holder, int position) {

        ProjectDeadline currentNote = mDeadlines.get(position);

        holder.sourceWebsiteView.setText(currentNote.getSourceWebsite());
        holder.deadlineTitleView.setText(String.valueOf(currentNote.getProjectTitle()));
        holder.deadlineDateView.setText(currentNote.getDeadlineDate());

    }

    public ProjectDeadline getNoteAt(int position) {
        return mDeadlines.get(position);
    }

    @Override
    public int getItemCount() {
        return mDeadlines.size();
    }

    public void setNotes(List<ProjectDeadline> notes) {
        this.mDeadlines = notes;
        notifyDataSetChanged();
    }



    class ProjectDeadlineHolder extends RecyclerView.ViewHolder {
        private TextView sourceWebsiteView;
        private TextView deadlineTitleView;
        private TextView deadlineDateView;


        public ProjectDeadlineHolder(@NonNull View itemView) {
            super(itemView);
            sourceWebsiteView = itemView.findViewById(R.id.source_website);
            deadlineTitleView = itemView.findViewById(R.id.project_title);
            deadlineDateView = itemView.findViewById(R.id.deadline_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(listener!=null && position!=RecyclerView.NO_POSITION)
                    {
                        listener.onItemClick(mDeadlines.get(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener
    {
        void onItemClick(ProjectDeadline deadline);
    }

    public void setOnItemClickListener(onItemClickListener listener){

        this.listener=listener;
    }

}
