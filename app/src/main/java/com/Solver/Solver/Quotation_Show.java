package com.Solver.Solver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.Solver.Solver.Adepter.QuotationAdapter;
import com.Solver.Solver.Adepter.QuotationArrayAdapter;
import com.Solver.Solver.ModelClass.Common_Resouces;
import com.Solver.Solver.ModelClass.Quotation_masters;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Quotation_Show extends AppCompatActivity {

    RecyclerView quotationRv;
    ListView quotationLV;
    List<Quotation_masters> quotation_mastersList;
    QuotationAdapter quotationAdapter;
    QuotationArrayAdapter quotationArrayAdapter;
    DatabaseReference quotationRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation__show);
        quotationRv=findViewById(R.id.quotationRvId);
        quotationLV=findViewById(R.id.quotationListId);
        quotation_mastersList=new ArrayList<>();

        quotationRef= FirebaseDatabase.getInstance().getReference().child("quotation_req_masters");


        quotationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Quotation_masters quotation_masters=data.getValue(Quotation_masters.class);
                    quotation_mastersList.add(quotation_masters);
                    Common_Resouces.toastS(getApplicationContext(),"Data retrieving");
                }

               // quotationAdapter=new QuotationAdapter(Quotation_Show.this,quotation_mastersList);
                Collections.reverse(quotation_mastersList);
                quotationArrayAdapter=new QuotationArrayAdapter(Quotation_Show.this,quotation_mastersList);
                quotationLV.setAdapter(quotationArrayAdapter);
                quotationArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
