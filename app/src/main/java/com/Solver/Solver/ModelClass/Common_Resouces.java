package com.Solver.Solver.ModelClass;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Common_Resouces {

    public Common_Resouces() {
    }

     public  static List<Factories> common_clientList=new ArrayList<>();
      List<String> common_jobList=new ArrayList<>();

    public List<Factories> getCommon_clientList() {
        return common_clientList;
    }

    public void setCommon_clientList(List<Factories> common_clientList) {
        this.common_clientList = common_clientList;
    }

    public List<String> getCommon_jobList() {
        return common_jobList;
    }

    public void setCommon_jobList(List<String> common_jobList) {
        this.common_jobList = common_jobList;
    }

    public static void  toastS(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    public static void  toastL(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
