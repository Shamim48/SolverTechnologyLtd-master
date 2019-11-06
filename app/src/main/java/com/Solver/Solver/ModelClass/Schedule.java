package com.Solver.Solver.ModelClass;

import java.util.List;

public class Schedule  {
    String scheduleId;
    String employeeName;
    String date;
    String companyName;
    String category;
    List<JobC> list;
    JobC jobc;


    public Schedule(String scheduleId, String employeeName, String date, String companyName, String category) {
        this.scheduleId = scheduleId;
        this.employeeName = employeeName;
        this.date = date;
        this.companyName = companyName;
        this.category = category;
    }

    public Schedule(String scheduleId, String employeeName, String date, String companyName, List<JobC> list) {
        this.scheduleId = scheduleId;
        this.employeeName = employeeName;
        this.date = date;
        this.companyName = companyName;
        this.category = category;
        this.list = list;
    }

    public Schedule(String companyName) {
        this.companyName = companyName;
    }

    public List<JobC> getList() {
        return list;
    }

    public void setList(List<JobC> list) {
        this.list = list;
    }

    public Schedule(String employeeName, String date, String companyName, String category) {
        this.employeeName = employeeName;
        this.date = date;
        this.companyName = companyName;
        this.category = category;

    }

    public Schedule() {
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public JobC getJobc() {
        return jobc;
    }

    public void setJobc(JobC jobc) {
        this.jobc = jobc;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId='" + scheduleId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", date='" + date + '\'' +
                ", companyName='" + companyName + '\'' +
                ", category='" + category + '\'' +
                ", list=" + list +
                ", jobc=" + jobc +
                '}';
    }
}