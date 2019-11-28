package com.Solver.Solver.ModelClass;

public class JobCategory {
    private String jobCategoryId;
   private String jobCategoryName;

    public JobCategory(String jobCategoryId, String jobCategoryName) {
        this.jobCategoryId = jobCategoryId;
        this.jobCategoryName = jobCategoryName;
    }

    public String getJobCategoryId() {
        return jobCategoryId;
    }

    public void setJobCategoryId(String jobCategoryId) {
        this.jobCategoryId = jobCategoryId;
    }

    public String getJobCategoryName() {
        return jobCategoryName;
    }

    public void setJobCategoryName(String jobCategoryName) {
        this.jobCategoryName = jobCategoryName;
    }
}
