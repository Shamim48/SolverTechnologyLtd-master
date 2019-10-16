package com.Solver.Solver.ModelClass;

import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class productHolder {

    private TextView productNameTv;
    private TextView desTv;
    private CheckBox checkBox;
    private LinearLayout quantityLLt;



    public productHolder(TextView productNameTv, TextView desTv, CheckBox checkBox, LinearLayout quantityLLt) {
        this.productNameTv = productNameTv;
        this.desTv = desTv;
        this.checkBox = checkBox;
        this.quantityLLt = quantityLLt;
    }

    public productHolder() {
    }

    public LinearLayout getQuantityLLt() {
        return quantityLLt;
    }

    public void setQuantityLLt(LinearLayout quantityLLt) {
        this.quantityLLt = quantityLLt;
    }

    public TextView getProductNameTv() {
        return productNameTv;
    }

    public void setProductNameTv(TextView productNameTv) {
        this.productNameTv = productNameTv;
    }

    public TextView getDesTv() {
        return desTv;
    }

    public void setDesTv(TextView desTv) {
        this.desTv = desTv;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
