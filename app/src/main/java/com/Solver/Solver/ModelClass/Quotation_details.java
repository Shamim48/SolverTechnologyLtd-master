package com.Solver.Solver.ModelClass;

public class Quotation_details {

    private String qut_no;
    private int company_id;
    private int customer_id;
    private String product_id;
    private Double unit_price;
    private int qty;
    private String warranty;
    private Double total_price;
    private String created_at;
    private String updated_at;


    public Quotation_details(String qut_no, int company_id, int customer_id, String product_id, Double unit_price, int qty, String warranty, Double total_price, String created_at, String updated_at) {
        this.qut_no = qut_no;
        this.company_id = company_id;
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.unit_price = unit_price;
        this.qty = qty;
        this.warranty = warranty;
        this.total_price = total_price;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Quotation_details() {

    }

    public String getQut_no() {
        return qut_no;
    }

    public void setQut_no(String qut_no) {
        this.qut_no = qut_no;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
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
