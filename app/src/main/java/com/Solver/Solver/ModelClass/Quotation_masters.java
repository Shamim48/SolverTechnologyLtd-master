package com.Solver.Solver.ModelClass;

public class Quotation_masters {

    private String qut_key;
    private String qut_no;
    private int customer_id;
    private int company_id;
    private String attn_person;
    private String attn_email;
    private String cc_person;
    private String cc_email;
    private String date;
    private Double total_amount;
    private Double discount;
    private Double vat;
    private Double tax;
    private Double vat_amount;
    private Double tax_amount;
    private Double grand_amount;
    private int user_id;
    private String payment_option;
    private String delivery_option;
    private String checked_by;
    private String authorized;
    private String request_user;
    private String request_status;
    private int print;
    private String created_at;
    private String updated_at;


    public  Quotation_masters(String qut_key,String qut_no, int customer_id, int company_id, String attn_person, String attn_email, String cc_person, String cc_email, String date,
                             Double total_amount, Double discount, Double vat, Double tax, Double vat_amount, Double tax_amount, Double grand_amount, int user_id,
                             String payment_option, String delivery_option, String checked_by, String authorized, String request_user, String request_status, int print, String created_at, String updated_at) {

        this.qut_key = qut_key;
        this.qut_no = qut_no;
        this.customer_id = customer_id;
        this.company_id = company_id;
        this.attn_person = attn_person;
        this.attn_email = attn_email;
        this.cc_person = cc_person;
        this.cc_email = cc_email;
        this.date = date;
        this.total_amount = total_amount;
        this.discount = discount;
        this.vat = vat;
        this.tax = tax;
        this.vat_amount = vat_amount;
        this.tax_amount = tax_amount;
        this.grand_amount = grand_amount;
        this.user_id = user_id;
        this.payment_option = payment_option;
        this.delivery_option = delivery_option;
        this.checked_by = checked_by;
        this.authorized = authorized;
        this.request_user = request_user;
        this.request_status = request_status;
        this.print = print;
        this.created_at = created_at;
        this.updated_at = updated_at;
        
    }

    public Quotation_masters() {
    }


    public String getQut_key() {
        return qut_key;
    }

    public void setQut_key(String qut_key) {
        this.qut_key = qut_key;
    }

    public String getQut_no() {
        return qut_no;
    }

    public void setQut_no(String qut_no) {
        this.qut_no = qut_no;
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

    public String getAttn_person() {
        return attn_person;
    }

    public void setAttn_person(String attn_person) {
        this.attn_person = attn_person;
    }

    public String getAttn_email() {
        return attn_email;
    }

    public void setAttn_email(String attn_email) {
        this.attn_email = attn_email;
    }

    public String getCc_person() {
        return cc_person;
    }

    public void setCc_person(String cc_person) {
        this.cc_person = cc_person;
    }

    public String getCc_email() {
        return cc_email;
    }

    public void setCc_email(String cc_email) {
        this.cc_email = cc_email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getVat_amount() {
        return vat_amount;
    }

    public void setVat_amount(Double vat_amount) {
        this.vat_amount = vat_amount;
    }

    public Double getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(Double tax_amount) {
        this.tax_amount = tax_amount;
    }

    public Double getGrand_amount() {
        return grand_amount;
    }

    public void setGrand_amount(Double grand_amount) {
        this.grand_amount = grand_amount;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPayment_option() {
        return payment_option;
    }

    public void setPayment_option(String payment_option) {
        this.payment_option = payment_option;
    }

    public String getDelivery_option() {
        return delivery_option;
    }

    public void setDelivery_option(String delivery_option) {
        this.delivery_option = delivery_option;
    }

    public String getChecked_by() {
        return checked_by;
    }

    public void setChecked_by(String checked_by) {
        this.checked_by = checked_by;
    }

    public String getAuthorized() {
        return authorized;
    }

    public void setAuthorized(String authorized) {
        this.authorized = authorized;
    }

    public String getRequest_user() {
        return request_user;
    }

    public void setRequest_user(String request_user) {
        this.request_user = request_user;
    }

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }

    public int getPrint() {
        return print;
    }

    public void setPrint(int print) {
        this.print = print;
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
