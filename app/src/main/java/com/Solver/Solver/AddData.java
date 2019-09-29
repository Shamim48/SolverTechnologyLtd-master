package com.Solver.Solver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.Solver.Solver.ModelClass.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddData extends AppCompatActivity {

    ArrayList<Product> productList=new ArrayList<>();
    DatabaseReference productRef;
    Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        productRef= FirebaseDatabase.getInstance().getReference().child("product");
        product=new Product();
        try {
           /* productList.add(new Product(1, "E00001", 101, 10001, "Piece", 5, 100, 111, 20170208, "CDE505-4T030",
                    "COUNTRY : CHINA",  3, 1, "2019-08-20 06:43:26", "null"));
            productList.add(new Product(2, "E00002", 101, 10002, "Piece", 5, 100, 100, 20170208, "CDE360-4T1R5G/2R2L",
                    "COUNTRY : CHINA",  3, 1, "2019-08-20 06:49:50", "null"));
            productList.add(new Product(3, "E00003", 101, 10003, "Piece", 5, 100, 101, 20170208, "CDE501-2S0R4", "COUNTRY : CHINA", 3, 1, "2019-08-20 06:52:14", "null"));


            productRef.setValue(productList);*/
        }catch (Exception e){

        }


    }
}
