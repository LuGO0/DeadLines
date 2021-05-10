package com.example.deadlines.room.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "deadlines_table")
public class ProjectDeadline {
    @ColumnInfo
    private String projectTitle;
    @ColumnInfo
    private String sourceWebsite;
    @ColumnInfo
    private String deadlineDate;
    @PrimaryKey
    @NonNull
    private String redirectingUrl;

    public ProjectDeadline(String projectTitle, String sourceWebsite, String deadlineDate, String redirectingUrl) {
        this.projectTitle = projectTitle;
        this.sourceWebsite = sourceWebsite;
        this.deadlineDate = deadlineDate;
        this.redirectingUrl = redirectingUrl;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getRedirectingUrl() {
        return redirectingUrl;
    }

    public void setRedirectingUrl(@NonNull String redirectingUrl) {
        this.redirectingUrl = redirectingUrl;
    }

    public String getSourceWebsite() {
        return sourceWebsite;
    }

    public void setSourceWebsite(String sourceWebsite) {
        this.sourceWebsite = sourceWebsite;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }
}
