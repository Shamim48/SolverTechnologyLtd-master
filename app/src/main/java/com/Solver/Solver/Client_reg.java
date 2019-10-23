package com.Solver.Solver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Client_reg extends AppCompatActivity {
//View Variable
    private EditText companyNameEt;
    private EditText consultantPEt;
    private EditText addressCp;
    private EditText clientPhone;
    private EditText clientEmail;
    private Button clientRegistrationBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_reg);
        findId();
        clientRegistrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String companyName=companyNameEt.getText().toString().trim();
                String address=addressCp.getText().toString().trim();
                String consultant=consultantPEt.getText().toString().trim();
                String phone=clientPhone.getText().toString().trim();
                String email=clientEmail.getText().toString().trim();
                if(companyName.isEmpty()){
                    companyNameEt.setError("Please Enter Client/Company Name..!");
                }
                if(address.isEmpty()){
                    addressCp.setError("Please Enter Company Address..!");
                }
                if(consultant.isEmpty()){
                    consultantPEt.setError("Please Enter Consultant person..!");
                }
                if(phone.isEmpty()){
                    clientPhone.setError("Please Enter Client phone number..!");
                }
                if(email.isEmpty()){
                    clientEmail.setError("Please Enter client email..!");
                }



            }
        });
    }

    private void findId() {
        companyNameEt=findViewById(R.id.companyNameEtId);
        consultantPEt=findViewById(R.id.consultantPNId);
        addressCp=findViewById(R.id.addressCompanyId);
        clientPhone=findViewById(R.id.phoneCompanyId);
        clientEmail=findViewById(R.id.emailCompanyId);
        clientRegistrationBtn=findViewById(R.id.clientRegisterBtnId);
    }
}
