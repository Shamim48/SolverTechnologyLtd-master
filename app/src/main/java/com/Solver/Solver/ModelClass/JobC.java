package com.Solver.Solver.ModelClass;

import java.util.List;

public class JobC {

    private String jobId;
    private String jobTitle;
    private String jobDes;
    private String category;

    public JobC() {
    }

    public JobC(String jobId, String jobTitle) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
    }

    public JobC(String jobId, String jobTitle, String jobDes, String category) {
        this.jobTitle = jobTitle;
        this.jobDes = jobDes;
        this.category=category;
        this.jobId=jobId;
    }
    public JobC(String jobTitle, String jobDes, String category) {
        this.jobTitle = jobTitle;
        this.jobDes = jobDes;
        this.category=category;

    }





    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDes() {
        return jobDes;
    }

    public void setJobDes(String jobDes) {
        this.jobDes = jobDes;
    }

    @Override
    public String toString() {
        return "JobC{" +
                "jobId='" + jobId + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobDes='" + jobDes + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}