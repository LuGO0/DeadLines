package com.example.deadlines;

public class Project {

    /*
     * private variables
     */
    private String projectTitle;
    private String sourceWebsite;

    //the data type of the date dependes on the type of data we get from the website
    //for now its string
    private String deadlineDate;
    private String detailedDescription;
    private String redirectingUrl;


    /*
     * constructor
     */
    public Project(String projectTitle, String sourceWebsite, String deadlineDate) {
        this.projectTitle = projectTitle;
        this.sourceWebsite = sourceWebsite;
        this.deadlineDate = deadlineDate;
    }

    /*
     * getter methods
     */
    public String getProjectTitle() {
        return projectTitle;
    }

    public String getDetailedDescription() {
        return detailedDescription;
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
