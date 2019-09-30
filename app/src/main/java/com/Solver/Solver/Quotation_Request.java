package com.Solver.Solver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.Solver.Solver.Adepter.ClientAdepter;
import com.Solver.Solver.Adepter.ProductAdapter;
import com.Solver.Solver.ModelClass.Client;
import com.Solver.Solver.ModelClass.Common_Resouces;
import com.Solver.Solver.ModelClass.Product;
import com.Solver.Solver.ModelClass.Quotation_masters;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Quotation_Request extends AppCompatActivity {

    DatabaseReference productRef;
    List<Product> productList;
    ProductAdapter adepter;
    RecyclerView productRv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation__request);
        productRv=findViewById(R.id.productRvId);

        productRef= FirebaseDatabase.getInstance().getReference().child("product");
        productList=new ArrayList<>();

        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    Product product=data.getValue(Product.class);
                    productList.add(product);




                }
                adepter=new ProductAdapter(Quotation_Request.this,productList);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Quotation_Request.this);
                productRv.setLayoutManager(linearLayoutManager);
                productRv.setAdapter(adepter);
               /* Common_Resouces common_resouces=new Common_Resouces();
                common_resouces.setCommon_clientList(productList);*/
                adepter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"Error:"+databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
