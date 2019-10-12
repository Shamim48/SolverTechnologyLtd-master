package com.Solver.Solver.ModelClass;

public class Product {

    private String product_key;
    private int id;
    private String product_id;
    private int company_id;
    private int barcode;
    private String product_unit;
    private String sell_percentage;
    private String brand_id;
    private int sub_category_id;
    private int user_id;
    private String product_name;
    private String description;
    private String reorder;
    private int status;
    private String created_at;
    private String updated_at;


    public Product(String product_id, int company_id, int barcode, String product_unit, String sell_percentage, String brand_id, int sub_category_id, int user_id, String product_name, String description, String reorder, int status, String created_at, String updated_at) {
        this.product_id = product_id;
        this.company_id = company_id;
        this.barcode = barcode;
        this.product_unit = product_unit;
        this.sell_percentage = sell_percentage;
        this.brand_id = brand_id;
        this.sub_category_id = sub_category_id;
        this.user_id = user_id;
        this.product_name = product_name;
        this.description = description;
        this.reorder = reorder;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Product() {

    }

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public String getProduct_unit() {
        return product_unit;
    }

    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }

    public String getSell_percentage() {
        return sell_percentage;
    }

    public void setSell_percentage(String sell_percentage) {
        this.sell_percentage = sell_percentage;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public int getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(int sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReorder() {
        return reorder;
    }

    public void setReorder(String reorder) {
        this.reorder = reorder;
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
