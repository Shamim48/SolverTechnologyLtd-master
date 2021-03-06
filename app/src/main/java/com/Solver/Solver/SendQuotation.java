package com.Solver.Solver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.Solver.Solver.Adepter.ClientArrayAdapter;
import com.Solver.Solver.ModelClass.Client;
import com.Solver.Solver.ModelClass.Factories;
import com.Solver.Solver.ModelClass.Quotation_details;
import com.Solver.Solver.ModelClass.Quotation_masters;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SendQuotation extends AppCompatActivity {

    String productKey,productId,currentUserName,clientName;
    int quantity;
    SearchView clientSv;
    EditText quantityEt;
    Button sendQuantityBtn;
    ImageButton closeBtn;
    LinearLayout lnlt;
    ListView clientListView;
    FirebaseAuth auth;
    FirebaseUser user;
    String userId;
    DatabaseReference quotationMasterRef;
    DatabaseReference quotationDetailsRef;
    DatabaseReference clientRef;
    ArrayList<Factories> clientArrayList=new ArrayList<>();
    ClientArrayAdapter clientArrayAdapter;

    int customerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_quotation);
        clientSv=findViewById(R.id.clientSvId);
        quantityEt=findViewById(R.id.quantityEtId);
        sendQuantityBtn=findViewById(R.id.sendQuotationBtnId);
        closeBtn=findViewById(R.id.closeBtnSQId);
        lnlt=findViewById(R.id.lltASqLVId);
        clientListView=findViewById(R.id.clientTagLVId);


        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        quotationMasterRef= FirebaseDatabase.getInstance().getReference().child("quotation_req_masters");
        quotationDetailsRef= FirebaseDatabase.getInstance().getReference().child("quotation_req_details");

        clientRef=FirebaseDatabase.getInstance().getReference().child("factories");

        lnlt.setVisibility(View.GONE);
        currentUserName=user.getDisplayName();

        Intent intent=getIntent();
        productKey=intent.getStringExtra("productKey");
        productId=intent.getStringExtra("productId");

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnlt.setVisibility(View.GONE);
            }
        });

        clientSv.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnlt.setVisibility(View.VISIBLE);
            }
        });

        clientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Factories client=data.getValue(Factories.class);
                    clientArrayList.add(client);
                }

                clientArrayAdapter=new ClientArrayAdapter(SendQuotation.this,clientArrayList);

                clientListView.setAdapter(clientArrayAdapter);
               clientArrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        clientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clientName=clientArrayList.get(i).getCompany_name();
                customerId=clientArrayList.get(i).getCustomer_id();
                clientSv.setQuery(clientName,true);
                lnlt.setVisibility(View.GONE);
            }
        });

        clientSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                clientArrayAdapter.getFilter().filter(s);
                return false;
            }
        });

        sendQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientName=clientSv.getQuery().toString();
              String  qut=quantityEt.getText().toString();
              quantity=Integer.parseInt(qut);
             String qut_key= quotationMasterRef.push().getKey();
                String qur_details_key=quotationDetailsRef.push().getKey();
            /* public  Quotation_masters(String qut_key,String qut_no, int customer_id, int company_id, String attn_person, String attn_email, String cc_person, String cc_email, String date,
                        Double total_amount, Double discount, Double vat, Double tax, Double vat_amount, Double tax_amount, Double grand_amount, int user_id,
                String payment_option, String delivery_option, String checked_by, String authorized, String request_user, String request_status, int print, String created_at, String updated_at) {

*/
                    Quotation_masters quotation_masters=new Quotation_masters(qut_key,"Q-2019080501", customerId, 101, "Munsur Ali", "munsur@kktextile.com", "Prema Khan", "prema@kktextile.com", "2019-08", 30500.00, 500.00, 10.00, 10.00, 3000.00, 3000.00, 36000.00, 10012, "100% payment", "Delivery after 100% payment", "Sajol karmakar", "authorized", "Shamim","Pending",3, "2019-08-05 09:16:53", "2019-09-21 04:02:43");

                Quotation_details quotation_details=new Quotation_details(qur_details_key,"Q-2019080502", 101, customerId, productId, 550.00, quantity, "", 2200.00, "2019-08-05 09:17:44", "2019-08-05 09:17:44");

              quotationMasterRef.child(qut_key).setValue(quotation_masters).addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                      if (task.isSuccessful())
                      {
                          Toast.makeText(getApplicationContext(),"Quotation Master upload Success",Toast.LENGTH_SHORT).show();
                      }
                  }
              });
              quotationDetailsRef.child(qur_details_key).setValue(quotation_details).addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                      if(task.isSuccessful()){
                          Toast.makeText(getApplicationContext(),"Quotation Details upload Success",Toast.LENGTH_SHORT).show();

                          quantityEt.setText("");
                          clientSv.setQuery("",true);
                      }
                  }
              });

            }
        });

    }
}
