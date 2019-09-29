package com.Solver.Solver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.Solver.Solver.ModelClass.Brands;
import com.Solver.Solver.ModelClass.Categories;
import com.Solver.Solver.ModelClass.Factories;
import com.Solver.Solver.ModelClass.Product;
import com.Solver.Solver.ModelClass.Sub_categories;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddData extends AppCompatActivity {

    ArrayList<Factories> list =new ArrayList<>();
    DatabaseReference Ref;
    Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        Ref = FirebaseDatabase.getInstance().getReference().child("factories");
        product=new Product();
        try {
           /* list.add(new Categories(100, 101, 1000, "Inverter", 20170208, 1, "2019-07-30 06:24:05", "2019-07-30 06:24:05"));
            list.add(new Categories(101, 101, 1000, "PLC", 20170208, 1, "2019-07-30 06:24:25", "2019-07-30 06:24:25"));
            list.add(new Categories(102, 101, 1000, "Display", 20170208, 1, "2019-07-30 06:24:36", "2019-07-30 06:24:36"));
            list.add(new Categories(103, 101, 1000, "Analog  Card", 20170208, 1, "2019-07-30 06:25:15", "2019-07-30 06:25:15"));
            list.add(new Categories(104, 101, 1000, "Door Support Card", 20170208, 1, "2019-07-30 06:26:16", "2019-07-30 06:26:16"));
            list.add(new Categories(105, 101, 1000, "ORDEL", 20170208, 1, "2019-07-30 06:26:42", "2019-07-30 06:26:42"));
            list.add(new Categories(106, 101, 1000, "PT 100", 20170208, 1, "2019-07-30 06:26:56", "2019-07-30 06:26:56"));
            list.add(new Categories(107, 101, 1002, "Reactor", 20170208, 1, "2019-07-30 06:28:11", "2019-07-30 06:28:11"));
            list.add(new Categories(108, 101, 1000, "Reactor Card", 20170208, 1, "2019-07-30 06:28:32", "2019-07-30 06:28:32"));
            list.add(new Categories(109, 101, 1000, "MCB", 20170208, 1, "2019-07-30 06:28:57", "2019-07-30 06:28:57"));
           */

            list.add(new Factories(1, 101, "dfas", "sdf", "sdf", "45435", "543", "34543", "sdf", "sdf", "dsf", "D", "sf@gmail.com", "sda@gmail.com", "af@gmail.com", 0, 1, "2019-07-17 03:20:25", "2019-07-17 03:20:25"));
            list.add(new Factories(2, 101, "KK Textile", "5/S, Sector-7, Uttara, Dhaka-1206", "5/S, Sector-7, Uttara, Dhaka-1206", "01303109929", "01324567785", "01245786654", "Sajol karmakar", "Munsur Ali", "Prema Khan", "A", "sajolkk@kktextile.com", "munsur@kktextile.com", "prema@kktextile.com", 1, 1, "2019-07-22 05:35:41", "2019-09-18 06:17:51"));
            list.add(new Factories(3, 101, "Matri Textile", "A. k. Khan, Olankar, Chattagram", "A. k. Khan, Olankar, Chattagram", "01865427785" , "01854672579", "01957843254", "Joy das", "Piyal karmakar", "Manik Chowdhuray", "B", "joy@matri.textile.com", "piyal@matri.textile.com", "manik@matri.textile.com", 2, 1, "2019-07-22 05:39:02", "2019-07-22 05:39:02"));

        }catch (Exception e){

        }


    }

    public void addBtn(View view) {
        Ref.setValue(list).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(),"Data Upload Successful",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Data Upload unSuccessful:"+e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}
