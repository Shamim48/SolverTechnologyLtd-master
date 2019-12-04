package com.Solver.Solver.ModelClass;

import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class JobC {

    private String jobId;
    private String jobTitle;
    private String jobDes;
    private String category;
    private String categoryId;
    private boolean checked=false;
    LinearLayout jobDesLLt;
    CheckBox checkBox;
    TextView jobTitleTv;

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

    public JobC(String jobId, String jobDes, String categoryId) {
        this.jobId = jobId;
        this.jobDes = jobDes;
        this.categoryId = categoryId;
    }

    public JobC(LinearLayout jobDesLLt, CheckBox checkBox, TextView jobTitleTv) {
        this.jobDesLLt = jobDesLLt;
        this.checkBox = checkBox;
        this.jobTitleTv = jobTitleTv;
    }

    public TextView getJobTitleTv() {
        return jobTitleTv;
    }

    public void setJobTitleTv(TextView jobTitleTv) {
        this.jobTitleTv = jobTitleTv;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public LinearLayout getJobDesLLt() {

        return jobDesLLt;
    }

    public void setJobDesLLt(LinearLayout jobDesLLt) {
        this.jobDesLLt = jobDesLLt;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
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