package com.example.deadlines.models;

import androidx.room.Entity;

@Entity
public class ProjectDeadline {

    private String projectTitle;
    private String sourceWebsite;
    private String deadlineDate;
    private String redirectingUrl;


    public ProjectDeadline(String projectTitle, String sourceWebsite, String deadlineDate, String redirectingUrl) {
        this.projectTitle = projectTitle;
        this.sourceWebsite = sourceWebsite;
        this.deadlineDate = deadlineDate;
        this.redirectingUrl=redirectingUrl;
    }

    /*
     * getter methods
     */
    public String getProjectTitle() {
        return projectTitle;
    }

    public String getRedirectingUrl() {
        return redirectingUrl;
    }

    public String getSourceWebsite() {
        return sourceWebsite;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }
}
