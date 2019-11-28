package com.Solver.Solver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Solver.Solver.ModelClass.JobC;
import com.Solver.Solver.ModelClass.JobCategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Utility_Setub extends AppCompatActivity implements View.OnClickListener {

    CardView jobCv;
    CardView jobCategoryCv;

    View view;

    //Fire base Database Ref..
    DatabaseReference utilityRef;
    private EditText titleDlEt;
    private  Button cancelDlBtn;
    private Button okDlBtn;

    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utility__setub);

        findId();
        utilityRef= FirebaseDatabase.getInstance().getReference("Utility");
      jobCv.setOnClickListener(this);
      jobCategoryCv.setOnClickListener(this);
    }

    public void findId(){
        jobCv=findViewById(R.id.addNewJodCvId);
        jobCategoryCv=findViewById(R.id.addJobCategoryCvId);
    }





    private void jobDialog() {

        AlertDialog.Builder alert=new AlertDialog.Builder(Utility_Setub.this);
        view =getLayoutInflater().inflate(R.layout.add_job_dialog,null);
        findIdByDialogViw(view);
        alert.setView(view);
        final AlertDialog alertDialogByClient=alert.create();
        alertDialogByClient.setCanceledOnTouchOutside(false);
        alertDialogByClient.show();

        okDlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text =titleDlEt.getText().toString().trim();

                if(text.isEmpty()){
                    titleDlEt.setError("Please Enter Jon Title");
                    return;
                }

                DatabaseReference jobREf=utilityRef.child("Job");
                String key=jobREf.push().getKey();
                JobC jobC=new JobC(key, text);
                jobREf.child(key).setValue(jobC).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Job added",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialogByClient.cancel();
            }
        });

        cancelDlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogByClient.dismiss();
            }
        });

    }

    private void jobCategoryDialog() {

        AlertDialog.Builder alert=new AlertDialog.Builder(Utility_Setub.this);
        view =getLayoutInflater().inflate(R.layout.job_category_dl,null);
        findIdByDialogViw(view);
        alert.setView(view);
        final AlertDialog alertDialogByClient=alert.create();
        alertDialogByClient.setCanceledOnTouchOutside(false);
        alertDialogByClient.show();

        okDlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text =titleDlEt.getText().toString().trim();

                if(text.isEmpty()){
                    titleDlEt.setError("Please Enter Jon Title");
                    return;
                }

                DatabaseReference subTblRef=utilityRef.child("Job_Category");
                String key=subTblRef.push().getKey();
                JobCategory jobC=new JobCategory(key, text);
                subTblRef.child(key).setValue(jobC).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Job added",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialogByClient.cancel();
            }
        });

        cancelDlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogByClient.dismiss();
            }
        });

    }


    private void findIdByDialogViw(View view) {

        titleDlEt= view.findViewById(R.id.titleNameDlEtId);
        cancelDlBtn= view.findViewById(R.id.cancelBtnDlId);
        okDlBtn= view.findViewById(R.id.okBtnDlId);


    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.addNewJodCvId:
                jobDialog();
                break;

            case  R.id.addJobCategoryCvId:

                jobCategoryDialog();
                break;
        }
    }
}
