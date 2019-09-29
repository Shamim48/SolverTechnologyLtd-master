package com.Solver.Solver.ModelClass;

public class Factories {

    private int customer_id;
    private int company_id;
    private String company_name;
    private String address;
    private String location;
    private String md_phone;
    private String attn_phone;
    private String cc_phone;
    private String md_name;
    private String attn_name;
    private String cc_name;
    private String customer_grade;
    private String md_email;
    private String attn_email;
    private String cc_email;
    private int user_id;
    private int status;
    private String created_at;
    private String updated_at;

    public Factories(int customer_id, int company_id, String company_name, String address, String location, String md_phone, String attn_phone, String cc_phone, String md_name, String attn_name, String cc_name, String customer_grade, String md_email, String attn_email, String cc_email, int user_id, int status, String created_at, String updated_at) {
        this.customer_id = customer_id;
        this.company_id = company_id;
        this.company_name = company_name;
        this.address = address;
        this.location = location;
        this.md_phone = md_phone;
        this.attn_phone = attn_phone;
        this.cc_phone = cc_phone;
        this.md_name = md_name;
        this.attn_name = attn_name;
        this.cc_name = cc_name;
        this.customer_grade = customer_grade;
        this.md_email = md_email;
        this.attn_email = attn_email;
        this.cc_email = cc_email;
        this.user_id = user_id;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Factories() {
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMd_phone() {
        return md_phone;
    }

    public void setMd_phone(String md_phone) {
        this.md_phone = md_phone;
    }

    public String getAttn_phone() {
        return attn_phone;
    }

    public void setAttn_phone(String attn_phone) {
        this.attn_phone = attn_phone;
    }

    public String getCc_phone() {
        return cc_phone;
    }

    public void setCc_phone(String cc_phone) {
        this.cc_phone = cc_phone;
    }

    public String getMd_name() {
        return md_name;
    }

    public void setMd_name(String md_name) {
        this.md_name = md_name;
    }

    public String getAttn_name() {
        return attn_name;
    }

    public void setAttn_name(String attn_name) {
        this.attn_name = attn_name;
    }

    public String getCc_name() {
        return cc_name;
    }

    public void setCc_name(String cc_name) {
        this.cc_name = cc_name;
    }

    public String getCustomer_grade() {
        return customer_grade;
    }

    public void setCustomer_grade(String customer_grade) {
        this.customer_grade = customer_grade;
    }

    public String getMd_email() {
        return md_email;
    }

    public void setMd_email(String md_email) {
        this.md_email = md_email;
    }

    public String getAttn_email() {
        return attn_email;
    }

    public void setAttn_email(String attn_email) {
        this.attn_email = attn_email;
    }

    public String getCc_email() {
        return cc_email;
    }

    public void setCc_email(String cc_email) {
        this.cc_email = cc_email;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
