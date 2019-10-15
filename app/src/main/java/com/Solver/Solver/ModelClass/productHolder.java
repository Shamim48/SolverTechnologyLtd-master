package com.Solver.Solver.ModelClass;

import android.widget.CheckBox;
import android.widget.TextView;

public class productHolder {

    private TextView productNameTv;
    private TextView desTv;
    private CheckBox checkBox;

    public productHolder(TextView productNameTv, TextView desTv, CheckBox checkBox) {
        this.productNameTv = productNameTv;
        this.desTv = desTv;
        this.checkBox = checkBox;
    }


    public productHolder() {
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
