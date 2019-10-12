package com.Solver.Solver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordAtv extends AppCompatActivity {

    EditText forgotEmailEt;
    Button resetPassBtn;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_atv);
        firebaseAuth=FirebaseAuth.getInstance();
        forgotEmailEt=findViewById(R.id.forgetEmailEtId);
        resetPassBtn=findViewById(R.id.resetPasswordBtnId);

        resetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String forgotEmail=forgotEmailEt.getText().toString();
                if (forgotEmail.isEmpty()){
                    toast("Please Enter Correct Email..");
                }else {
                    firebaseAuth.sendPasswordResetEmail(forgotEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                toast("Please Check your Email..");
                                final AlertDialog.Builder alertDialog=new AlertDialog.Builder(ForgotPasswordAtv.this);
                                alertDialog.setTitle("Please Check your Email..");
                                alertDialog.setMessage("Reset Password Link has been sent your e-mail");
                                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                        dialogInterface.dismiss();
                                        finish();
                                    }
                                });
                                alertDialog.show();
                            }else {
                                toast(" Please enter Correct Email.");
                            }
                        }

                    });
                }
            }
        });
    }

    public void toast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
