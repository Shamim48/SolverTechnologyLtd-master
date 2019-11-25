package com.Solver.Solver.ModelClass;

public class Product {

    private String product_key;
    private int id;
    private String product_id;
    private int company_id;
    private int barcode;
    private String product_unit;
    private int sell_percentage;
    private int brand_id;
    private int sub_category_id;
    private int user_id;
    private String product_name;
    private String description;
    private int reorder;
    private int status;
    private String created_at;
    private String updated_at;
    private String name_type;
   private boolean checked=false;
   private double quantity;
   private double unitPrice;
   private double totalPrice;
   private String warranty;


    public Product(String product_id, int company_id, int barcode, String product_unit, int sell_percentage, int brand_id, int sub_category_id, int user_id, String product_name, String description, int reorder, int status, String created_at, String updated_at, String name_type) {
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
        this.name_type = name_type;
    }
public Product(String product_id, int company_id, int barcode, String product_unit, int sell_percentage, int brand_id, int sub_category_id, int user_id, String product_name, String description, int reorder, int status, String created_at, String updated_at, String name_type,int quantity) {
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
        this.name_type = name_type;
        this.quantity=quantity;
    }


    public Product(String product_key, String product_id, double quantity, double unitPrice, double totalPrice, String warranty) {
        this.product_key = product_key;
        this.product_id = product_id;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.warranty = warranty;
    }

    public Product() {
    }


    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }





    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public void toggleChecked() {
        checked = !checked;
    }

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }

    public String getName_type() {
        return name_type;
    }

    public void setName_type(String name_type) {
        this.name_type = name_type;
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

    public int getSell_percentage() {
        return sell_percentage;
    }

    public void setSell_percentage(int sell_percentage) {
        this.sell_percentage = sell_percentage;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
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

    public int getReorder() {
        return reorder;
    }

    public void setReorder(int reorder) {
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
