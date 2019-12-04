package com.Solver.Solver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Solver.Solver.Adepter.ClientArrayAdapter;
import com.Solver.Solver.Adepter.EmployeeArrayAdapter;
import com.Solver.Solver.Adepter.FactoryAdapter;
import com.Solver.Solver.Adepter.JobAdapter;
import com.Solver.Solver.Adepter.JobCategorySpinnerAdapter;
import com.Solver.Solver.Adepter.JobGridViewAdapter;
import com.Solver.Solver.Adepter.JobSpinnerAdapter;
import com.Solver.Solver.Adepter.TagUserAdepter;
import com.Solver.Solver.Adepter.TagUserArrayAdapter;
import com.Solver.Solver.Adepter.TagUserBaseAdapter;
import com.Solver.Solver.Adepter.UserGridViewAdapter;

import com.Solver.Solver.ModelClass.Common_Resouces;
import com.Solver.Solver.ModelClass.Factories;
import com.Solver.Solver.ModelClass.JobC;
import com.Solver.Solver.ModelClass.JobCategory;
import com.Solver.Solver.ModelClass.Schedule;
import com.Solver.Solver.ModelClass.SignUp;
import com.Solver.Solver.ModelClass.UserNameAndID;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Write_Schedule extends AppCompatActivity implements UserGridViewAdapter.UserListener,JobGridViewAdapter.JobListener {

    private Button uploadBtn;
    private TextView tagEmployeeTv;
    private ListView jobLv;
    private Button addEmployeeBtn,addJobBtn;
    private EditText dateWsEt,jobDesEt;
    private TextView text;
    private SearchView userSV;
    private AutoCompleteTextView companyWsAt,employeeAt;
    private ListView tagUserLV;
    private ListView scheduleEmployeeLv;
    private Spinner jobCategorySp,jobTitleSp;
    private LinearLayout lltWsId;
    private ImageButton closeWsId;
    private GridView selectedUserGridView,selectedJobGridView;
    private CheckBox checkBox1,checkBox2,checkBox3,checkBox4,checkBox5,checkBox6,checkBox7,checkBox8,checkBox9,
            checkBox10,checkBox11,checkBox12,checkBox13;
    private EditText jodDesEt1,jodDesEt2,jodDesEt3,jodDesEt4,jodDesEt5,jodDesEt6,jodDesEt7,jodDesEt8,jodDesEt9,
            jodDesEt10,jodDesEt11,jodDesEt12,jodDesEt13;
    private Spinner spinner1,spinner2,spinner3,spinner4,spinner5,spinner6,spinner7,spinner8,spinner9,
            spinner10,spinner11,spinner12,spinner13;
    private TextView showTV;
    List<String> jobList;
    List<JobC> jList;
    ArrayList<SignUp> tagUserList;
    ArrayList<Factories> clientList;
    DatabaseReference clientRef;
    ArrayList<String> tagUserL=new ArrayList<>();
    ArrayList<String> scheduleEmployeeList=new ArrayList<>();
    ArrayList<JobC> jobCList=new ArrayList<>();
    ArrayList<JobCategory> jobCategoryList=new ArrayList<>();
    ArrayList<JobC> selectedJobList=new ArrayList<>();
    JobCategorySpinnerAdapter jobCategorySpinnerAdapter;
    JobAdapter jobAdapter;

    // All Adapter here ,,...
    ArrayAdapter<String> clientAdapter;
    DatabaseReference scheduleREf;
    DatabaseReference allScheduleREf;
    DatabaseReference jobRef;
    DatabaseReference userRef;
    DatabaseReference factoryRef;
    DatabaseReference jobListRef;
    DatabaseReference jobCategoryListRef;

    ArrayList<String> categoryList;
    ArrayAdapter<String> categorySpAdapter;
    ArrayAdapter<String> spinnerAdapter1,spinnerAdapter2,spinnerAdapter3,spinnerAdapter4,spinnerAdapter5,spinnerAdapter6,spinnerAdapter7,
                     spinnerAdapter8,spinnerAdapter9,spinnerAdapter10,spinnerAdapter11,spinnerAdapter12,spinnerAdapter13;
    ArrayAdapter scheduleEmployeeAdapter;
    TagUserAdepter tagUserAdepter;
    TagUserBaseAdapter tagUserBaseAdapter;
    TagUserArrayAdapter tagUserArrayAdapter;
    UserGridViewAdapter userGridViewAdapter;
    ClientArrayAdapter clientArrayAdapter;

    JobGridViewAdapter jobGridViewAdapter;

    UserNameAndID userNameAndID;
    String date;
    String tagUserName;
    String tagUserId;
    int companyId;
    StringBuffer jobBuilder;
    StringBuffer employee;

    String jobName1;
    String jobDes1;
    String jobName2;
    String jobDes2;
    String jobName3;
    String jobDes3;
    String jobName4;
    String jobDes4;
    String jobName5;
    String jobDes5;
    String jobName6;
    String jobDes6;
    String jobName7;
    String jobDes7;
    String jobName8;
    String jobDes8;
    Schedule schedule;
    String jobCategory_G;
     String userName;

    String jobId,jobCategoryId;

    LinearLayout jobLLt,userLLt;

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write__schedule);
        findId();
        lltWsId.setVisibility(View.GONE);
        tagUserLV.setVisibility(View.GONE);
        jobLLt.setVisibility(View.GONE);
        userLLt.setVisibility(View.GONE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Write Schedule");

        userNameAndID=new UserNameAndID();
        clientList=new ArrayList<>();
        jobList=new ArrayList<>();
        jList=new ArrayList<>();
        schedule=new Schedule();

        clientRef= FirebaseDatabase.getInstance().getReference().child("Client");
        factoryRef= FirebaseDatabase.getInstance().getReference().child("factories");
        scheduleREf=FirebaseDatabase.getInstance().getReference().child("Schedule").child("ScheduleTbl");
        allScheduleREf=FirebaseDatabase.getInstance().getReference().child("Schedule").child("All_ScheduleTbl");
        jobRef=FirebaseDatabase.getInstance().getReference().child("Schedule").child("Job");
        userRef=FirebaseDatabase.getInstance().getReference().child("User");
        jobListRef=FirebaseDatabase.getInstance().getReference("Utility").child("Job");
        jobCategoryListRef=FirebaseDatabase.getInstance().getReference("Utility").child("Job_Category");

        jobCList.clear();
        jobListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                   JobC jobC=data.getValue(JobC.class);
                   jobCList.add(jobC);
                }

                JobSpinnerAdapter jobSpinnerAdapter=new JobSpinnerAdapter(Write_Schedule.this,jobCList);
                jobTitleSp.setAdapter(jobSpinnerAdapter);
                jobSpinnerAdapter.notifyDataSetChanged();


              //  jobAdapter=new JobAdapter(Write_Schedule.this,jobCList);
              //  jobLv.setAdapter(jobAdapter);
               // jobAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tagUser();

        jobCategoryListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    JobCategory jobCategory=data.getValue(JobCategory.class);
                    jobCategoryList.add(jobCategory);
                }

                jobCategorySpinnerAdapter=new JobCategorySpinnerAdapter(Write_Schedule.this,jobCategoryList);
                jobCategorySp.setAdapter(jobCategorySpinnerAdapter);
                jobCategorySpinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        jobTitleSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 jobId=jobCList.get(i).getJobId();

                 text.setText(jobId+ "  ,  "+jobCategoryId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        jobCategorySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 jobCategoryId=jobCategoryList.get(i).getJobCategoryId();

                text.setText(jobId+ "  ,  "+jobCategoryId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

       // clientShow();
        clientList.clear();
        factoryRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Factories client=data.getValue(Factories.class);
                    //  String clientNam=client.getCompany_name();
                    clientList.add(client);
                }
                //R.layout.factory_sample_row,R.id.companyNameTSRTvId,
                FactoryAdapter factoryAdapter =new FactoryAdapter(Write_Schedule.this,clientList);
                companyWsAt.setAdapter(factoryAdapter);
                factoryAdapter.notifyDataSetChanged();


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        companyWsAt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                companyId=clientList.get(i).getCompany_id();
            }
        });

        categoryList=new ArrayList<>();
        categoryList.add("Chose category");
        categoryList.add("Warranty");
        categoryList.add("Non Warranty");
        categoryList.add("On Request");
        categoryList.add("Installation");
        initializeSpinner();

       // getCheckBoxAndDesText();

        dateWsEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call current date method
                date();
            }
        });

        userSV.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lltWsId.setVisibility(View.VISIBLE);
                tagUserLV.setVisibility(View.VISIBLE);
                tagUser();
            }
        });

        closeWsId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lltWsId.setVisibility(View.GONE);
            }
        });

        addJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jobDes=jobDesEt.getText().toString();
                /*if (jobDes.isEmpty()){
                    jobDesEt.setError("");
                }*/

                if(jobId.equals("-LvGMH6RusoC7SAbsjj9")){
                    Common_Resouces.toastS(Write_Schedule.this,"Please Select Job.");
                    return;
                }

                if (jobCategoryId.equals("-Lul1Oi-MmTV3R48Q815")){
                    Common_Resouces.toastS(Write_Schedule.this,"Please Select Job Category.");
                    return;
                }
                selectedJobList.add(new JobC(jobId,jobDes,jobCategoryId));

                jobGridViewAdapter=new JobGridViewAdapter(Write_Schedule.this,selectedJobList);
                selectedJobGridView.setAdapter(jobGridViewAdapter);
                jobGridViewAdapter.setRemoveJob(Write_Schedule.this);
                jobGridViewAdapter.notifyDataSetChanged();

                Common_Resouces.toastS(Write_Schedule.this,"Selected Job: "+selectedJobList.size());
                jobLLt.setVisibility(View.VISIBLE);
           jobId=null;
           jobCategoryId=null;
            }
        });

        addEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 employee=new StringBuffer();
              String tagEmployee=userSV.getQuery().toString();

              scheduleEmployeeList.add(tagUserId);
             // scheduleEmployeeAdapter=new ArrayAdapter(Write_Schedule.this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,scheduleEmployeeList);
              //scheduleEmployeeLv.setAdapter(scheduleEmployeeAdapter);
             // scheduleEmployeeAdapter.notifyDataSetChanged();
//              userSV.setQuery("",true);
             // userSV.clearFocus();
              Toast.makeText(getApplicationContext(),"selected Employee:"+scheduleEmployeeList.size(),Toast.LENGTH_SHORT).show();

              userGridViewAdapter=new UserGridViewAdapter(Write_Schedule.this,scheduleEmployeeList);
                selectedUserGridView.setAdapter(userGridViewAdapter);
                userGridViewAdapter.setRemoveUser(Write_Schedule.this);

                userGridViewAdapter.notifyDataSetChanged();

                userLLt.setVisibility(View.VISIBLE);
                employeeAt.setText(null);
                tagUserId=null;

              /*for (String tagEp:scheduleEmployeeList){

                  final DatabaseReference userdata=FirebaseDatabase.getInstance().getReference("User");
                  Query empNameRef=userdata.orderByChild("userId").equalTo(tagEp);
                  empNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          for(DataSnapshot data:dataSnapshot.getChildren()){
                              SignUp user=data.getValue(SignUp.class);
                              employee.append(user.getName()+" , ");
                              Common_Resouces.toastS(Write_Schedule.this,"Employee added");
                          }
                      }
                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {

                      }
                  });

              }
              tagEmployeeTv.setText(employee.toString());
*/
            }
        });
        uploadBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog=new ProgressDialog(Write_Schedule.this);
                progressDialog.setTitle("Please Wait..");
                progressDialog.setMessage("Uploading Data..");
                progressDialog.show();
                jList.clear();

                        String name= userSV.getQuery().toString();
                        String date=dateWsEt.getText().toString();
                        String comName=companyWsAt.getText().toString();
                       // String category=categorySp.getSelectedItem().toString();

                     //   showTV.setText("Dear Sir,\n"+date+" Work plan .\n"+name+"\n"+"Company: "+comName+"\nJob List: \n" );

                        StringBuilder builder=new StringBuilder();

                        if (checkBox1.isChecked()){
                            jodDesEt1.setVisibility(View.VISIBLE);
                            String checkBosText=checkBox1.getText().toString();
                            String jobDesText=jodDesEt1.getText().toString();
                            jobName1=checkBox1.getText().toString();
                            jobDes1=jodDesEt1.getText().toString();
                            String jobCategory=spinner1.getSelectedItem().toString();

                            jList.add(new JobC(checkBosText,jobDesText,jobCategory));

                        }else if(!checkBox1.isChecked()){
                            jodDesEt1.setText(null);
                            jodDesEt1.setVisibility(View.GONE);

                        }

                        if (checkBox2.isChecked()){
                            jodDesEt2.setVisibility(View.VISIBLE);
                            String checkBosText=checkBox2.getText().toString();
                            String jobDesText=jodDesEt2.getText().toString();
                            jobName2=checkBox2.getText().toString();
                            jobDes2=jodDesEt2.getText().toString();
                            String jobCategory=spinner2.getSelectedItem().toString();
                            jList.add(new JobC(checkBosText,jobDesText,jobCategory));

                        }else if(!checkBox2.isChecked()){
                            jodDesEt2.setText(null);
                            jodDesEt2.setVisibility(View.GONE);
                        }

                        if (checkBox3.isChecked()){
                            jodDesEt3.setVisibility(View.VISIBLE);
                            String checkBosText=checkBox3.getText().toString();
                            String jobDesText=jodDesEt3.getText().toString();
                            // jobList.add(checkBosText,jobDesText);
                            String jobCategory=spinner3.getSelectedItem().toString();

                            jList.add(new JobC(checkBosText,jobDesText,jobCategory));

                        }else if(!checkBox3.isChecked()){
                            jodDesEt3.setText(null);
                            jodDesEt3.setVisibility(View.GONE);

                        }

                if (checkBox4.isChecked()){
                            jodDesEt4.setVisibility(View.VISIBLE);
                            String checkBosText=checkBox4.getText().toString();
                            String jobDesText=jodDesEt4.getText().toString();
                            String jobCategory=spinner4.getSelectedItem().toString();
                            jList.add(new JobC(checkBosText,jobDesText,jobCategory));

                        }else if(!checkBox4.isChecked()){
                            jodDesEt4.setText(null);
                            jodDesEt4.setVisibility(View.GONE);

                        }

                 if (checkBox5.isChecked()){
                            jodDesEt5.setVisibility(View.VISIBLE);
                            String checkBosText=checkBox5.getText().toString();
                            String jobDesText=jodDesEt5.getText().toString();
                     String jobCategory=spinner5.getSelectedItem().toString();

                     jList.add(new JobC(checkBosText,jobDesText,jobCategory));

                 }else if(!checkBox5.isChecked()) {
                     jodDesEt5.setText(null);
                     jodDesEt5.setVisibility(View.GONE);

                 }

                if (checkBox6.isChecked()){
                            jodDesEt6.setVisibility(View.VISIBLE);
                            String checkBosText=checkBox6.getText().toString();
                            String jobDesText=jodDesEt6.getText().toString();
                            String jobCategory=spinner6.getSelectedItem().toString();

                            jList.add(new JobC(checkBosText,jobDesText,jobCategory));

                        }else if(!checkBox6.isChecked()){
                            jodDesEt6.setText(null);
                            jodDesEt6.setVisibility(View.GONE);

                        }

                        if (checkBox7.isChecked()){
                            jodDesEt7.setVisibility(View.VISIBLE);
                            String checkBosText=checkBox7.getText().toString();
                            String jobDesText=jodDesEt7.getText().toString();
                            String jobCategory=spinner7.getSelectedItem().toString();
                            jList.add(new JobC(checkBosText,jobDesText,jobCategory));

                        }else if(!checkBox7.isChecked()){
                            jodDesEt7.setText(null);
                            jodDesEt7.setVisibility(View.GONE);

                        }

                        if (checkBox8.isChecked()){
                            jodDesEt8.setVisibility(View.VISIBLE);
                            String checkBosText=checkBox8.getText().toString();
                            String jobDesText=jodDesEt8.getText().toString();
                            String jobCategory=spinner8.getSelectedItem().toString();

                            jList.add(new JobC(checkBosText,jobDesText,jobCategory));

                        }else if(!checkBox8.isChecked()){
                            jodDesEt8.setText(null);
                            jodDesEt8.setVisibility(View.GONE);

                        }

                        if (checkBox9.isChecked()){
                            jodDesEt9.setVisibility(View.VISIBLE);
                            String checkBosText=checkBox9.getText().toString();
                            String jobDesText=jodDesEt9.getText().toString();
                            String jobCategory=spinner9.getSelectedItem().toString();
                            jList.add(new JobC(checkBosText,jobDesText,jobCategory));

                        }else if(!checkBox9.isChecked()){
                            jodDesEt9.setText(null);
                            jodDesEt9.setVisibility(View.GONE);
                        }
                        if (checkBox10.isChecked()){
                            jodDesEt10.setVisibility(View.VISIBLE);
                            String checkBosText=checkBox10.getText().toString();
                            String jobDesText=jodDesEt10.getText().toString();
                            String jobCategory=spinner10.getSelectedItem().toString();
                            jList.add(new JobC(checkBosText,jobDesText,jobCategory));

                        }else if(!checkBox10.isChecked()){
                            jodDesEt10.setText(null);
                            jodDesEt10.setVisibility(View.GONE);
                        }

                        if (checkBox11.isChecked()){
                            jodDesEt11.setVisibility(View.VISIBLE);
                            String checkBosText=checkBox11.getText().toString();
                            String jobDesText=jodDesEt11.getText().toString();
                            String jobCategory=spinner11.getSelectedItem().toString();
                            jList.add(new JobC(checkBosText,jobDesText,jobCategory));

                        }else if(!checkBox11.isChecked()){
                            jodDesEt11.setText(null);
                            jodDesEt11.setVisibility(View.GONE);
                        }

                        if (checkBox12.isChecked()){
                            jodDesEt12.setVisibility(View.VISIBLE);
                            String checkBosText=checkBox12.getText().toString();
                            String jobDesText=jodDesEt12.getText().toString();
                            String jobCategory=spinner12.getSelectedItem().toString();
                            jList.add(new JobC(checkBosText,jobDesText,jobCategory));
                        }else if(!checkBox12.isChecked()){
                            jodDesEt12.setText(null);
                            jodDesEt12.setVisibility(View.GONE);
                        }

                        if (checkBox13.isChecked()){
                            jodDesEt13.setVisibility(View.VISIBLE);
                            String checkBosText=checkBox13.getText().toString();
                            String jobDesText=jodDesEt13.getText().toString();
                            String jobCategory=spinner13.getSelectedItem().toString();

                            jList.add(new JobC(checkBosText,jobDesText,jobCategory));

                        }else if(!checkBox13.isChecked()){
                            jodDesEt13.setText(null);
                            jodDesEt13.setVisibility(View.GONE);

                        }



             //   jobBuilder.append("Job Name: "+jobName1+".\nDes: "+jobDes1+".\n");
               // jobBuilder.append("Job Name: "+jobName1+".\nDes: "+jobDes1+".\n");
              ///  jobBuilder.append("Job Name: "+jobName1+".\nDes: "+jobDes1+".\n");
               // jobBuilder.append("Job Name: "+jobName1+".\nDes: "+jobDes1+".\n");
/*
                        List<JobC> list=new ArrayList<>();
                        for (List<JobC> list: jList){
                            builder.append(data);
                        }
                        String job= TextUtils.join(", ",jList);
                        showTV.setText(job);
*/
                if(scheduleEmployeeList.size()==0){
                    if(tagUserId.equals("")){
                        Common_Resouces.toastS(Write_Schedule.this,"Please Select Employee.");
                        return;
                    }if(companyId==0){
                        Common_Resouces.toastS(Write_Schedule.this,"Please Select Factory.");
                        return;
                    }
                    String sdlKey=scheduleREf.push().getKey();
                    Schedule  schedule=new Schedule(sdlKey,tagUserId,date,companyId,jobCList);
                    scheduleREf.child(tagUserId).child(date).child(sdlKey).setValue(schedule).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Upload Successful",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(),"Exception:"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else {
                    String tagEmp="";

                       for(String tagEmp_id:scheduleEmployeeList) {

                           if(tagEmp_id.equals("")){
                               Common_Resouces.toastS(Write_Schedule.this,"Please Select Employee.");
                               return;
                           }if(companyId==0){
                               Common_Resouces.toastS(Write_Schedule.this,"Please Select Factory.");
                               return;
                           }
                           String sdlKey = scheduleREf.push().getKey();
                           Schedule schedule = new Schedule(sdlKey, tagEmp_id, date,companyId, jobCList);
                           scheduleREf.child(tagUserId).child(date).child(sdlKey).setValue(schedule).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()) {
                                       Toast.makeText(getApplicationContext(), "Upload Successful", Toast.LENGTH_SHORT).show();
                                   } else {
                                       Toast.makeText(getApplicationContext(), "Exception:" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                   }
                               }
                           });

                       }
                }

                scheduleREf.child("ComName").child(tagUserId).setValue(comName);

                if(scheduleEmployeeList.size()==0){

              String scheduleKey = allScheduleREf.push().getKey();
                Schedule scheduleAll=new Schedule(scheduleKey,name,date,companyId,jList);
                allScheduleREf.child(scheduleKey).setValue(scheduleAll).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Upload Successful",Toast.LENGTH_SHORT).show();
                        }else {

                            Toast.makeText(getApplicationContext(),"Exception:"+task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    }
                });}else {
                    for(String tagEpName:scheduleEmployeeList){
                        String scheduleKey = allScheduleREf.push().getKey();
                        Schedule scheduleAll=new Schedule(scheduleKey,tagEpName,date,companyId,jList);
                        allScheduleREf.child(scheduleKey).setValue(scheduleAll).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Upload Successful",Toast.LENGTH_SHORT).show();
                                }else {

                                    Toast.makeText(getApplicationContext(),"Exception:"+task.getException().getMessage(),Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                    }
                }

                for (JobC jobC:jList){

                jobRef.child(tagUserId).child(date).push().setValue(jobC).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"jod list Upload Successful",Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(getApplicationContext(),"Exception:"+task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }

                    }
                });}
                jList.clear();
                clearData();

                progressDialog.dismiss();
                }

        });

       // showTV.setText(jobBuilder);
    }

    private void clearData() {
        companyWsAt.setText(null);
        userSV.clearFocus();
        dateWsEt.setText(null);
        userSV.setQuery("",false);
        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
        checkBox4.setChecked(false);
        checkBox5.setChecked(false);
        checkBox6.setChecked(false);
        checkBox7.setChecked(false);
        checkBox8.setChecked(false);
        checkBox9.setChecked(false);
        checkBox10.setChecked(false);
        checkBox11.setChecked(false);
        checkBox12.setChecked(false);
        checkBox13.setChecked(false);
        jodDesEt1.setText(null);
        jodDesEt2.setText(null);
        jodDesEt3.setText(null);
        jodDesEt4.setText(null);
        jodDesEt5.setText(null);
        jodDesEt6.setText(null);
        jodDesEt7.setText(null);
        jodDesEt8.setText(null);
        jodDesEt9.setText(null);
        jodDesEt10.setText(null);
        jodDesEt11.setText(null);
        jodDesEt12.setText(null);
        jodDesEt13.setText(null);

        spinner1.setSelection(0);
        spinner2.setSelection(0);
        spinner3.setSelection(0);
        spinner4.setSelection(0);
        spinner5.setSelection(0);
        spinner6.setSelection(0);
        spinner7.setSelection(0);
        spinner8.setSelection(0);
        spinner9.setSelection(0);
        spinner10.setSelection(0);
        spinner11.setSelection(0);
        spinner12.setSelection(0);
        spinner13.setSelection(0);

         jodDesEt1.setVisibility(View.GONE);
         jodDesEt2.setVisibility(View.GONE);
         jodDesEt3.setVisibility(View.GONE);
         jodDesEt4.setVisibility(View.GONE);
         jodDesEt5.setVisibility(View.GONE);
         jodDesEt6.setVisibility(View.GONE);
         jodDesEt7.setVisibility(View.GONE);
         jodDesEt8.setVisibility(View.GONE);
         jodDesEt9.setVisibility(View.GONE);
         jodDesEt10.setVisibility(View.GONE);
         jodDesEt11.setVisibility(View.GONE);
         jodDesEt12.setVisibility(View.GONE);
         jodDesEt13.setVisibility(View.GONE);


        spinner1.setVisibility(View.GONE);
        spinner2.setVisibility(View.GONE);
        spinner3.setVisibility(View.GONE);
        spinner4.setVisibility(View.GONE);
        spinner5.setVisibility(View.GONE);
        spinner6.setVisibility(View.GONE);
        spinner7.setVisibility(View.GONE);
        spinner8.setVisibility(View.GONE);
        spinner9.setVisibility(View.GONE);
        spinner10.setVisibility(View.GONE);
        spinner11.setVisibility(View.GONE);
        spinner2.setVisibility(View.GONE);
        spinner13.setVisibility(View.GONE);





    }

    private void initializeSpinner() {
       /* categorySpAdapter=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,categoryList);
        categorySp.setAdapter(categorySpAdapter);
*/
         spinnerAdapter1=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,categoryList);
        spinner1.setAdapter(spinnerAdapter1);

         spinnerAdapter2=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,categoryList);
        spinner2.setAdapter(spinnerAdapter2);

         spinnerAdapter3=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,categoryList);
        spinner3.setAdapter(spinnerAdapter3);

         spinnerAdapter1=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,categoryList);
        spinner1.setAdapter(spinnerAdapter1);

         spinnerAdapter4=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,categoryList);
        spinner4.setAdapter(spinnerAdapter4);

         spinnerAdapter5=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,categoryList);
        spinner5.setAdapter(spinnerAdapter5);

         spinnerAdapter6=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,categoryList);
        spinner6.setAdapter(spinnerAdapter6);

         spinnerAdapter7=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,categoryList);
        spinner7.setAdapter(spinnerAdapter7);

         spinnerAdapter8=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,categoryList);
        spinner8.setAdapter(spinnerAdapter8);

         spinnerAdapter9=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,categoryList);
        spinner9.setAdapter(spinnerAdapter9);

        spinnerAdapter10=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,categoryList);
        spinner10.setAdapter(spinnerAdapter10);

        spinnerAdapter11=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,categoryList);
        spinner11.setAdapter(spinnerAdapter11);

        spinnerAdapter12=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,categoryList);
        spinner12.setAdapter(spinnerAdapter12);

        spinnerAdapter13=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,categoryList);
        spinner13.setAdapter(spinnerAdapter13);




    }

    private void tagUser() {

        tagUserList=new ArrayList<>();

        tagUserList.clear();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    SignUp signUp=data.getValue(SignUp.class);
                    tagUserList.add(signUp);
                }

               // tagUserAdepter=new TagUserAdepter(Write_Schedule.this,tagUserList);

               // tagUserBaseAdapter=new TagUserBaseAdapter(Write_Schedule.this,tagUserList);
              //  userSV.setAdapter(tagUserBaseAdapter);
               // tagUserArrayAdapter=new TagUserArrayAdapter(Write_Schedule.this,tagUserList);
               // tagUserLV.setAdapter(tagUserArrayAdapter);
              //  tagUserArrayAdapter.notifyDataSetChanged();
               // userSV.setAdapter(tagUserArrayAdapter);

                EmployeeArrayAdapter  employeeArrayAdapter=new EmployeeArrayAdapter(Write_Schedule.this,tagUserList);
                employeeAt.setAdapter(employeeArrayAdapter);
                employeeArrayAdapter.notifyDataSetChanged();
                getId();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        employeeAt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tagUserId=tagUserList.get(i).getUserId();
            }
        });
        userSV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {



                tagUserArrayAdapter.getFilter().filter(newText);

                return false;
            }
        });


        tagUserLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tagUserName=tagUserList.get(i).getName();
                tagUserId=tagUserList.get(i).getUserId();
                userSV.setQuery(tagUserName,true);
               // showTV.setText(tagUserName+"\n ID: "+tagUserId);
                lltWsId.setVisibility(View.GONE);
                tagUserLV.setVisibility(View.GONE);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();






      /*  if(userSV.getQuery().equals(null)){
            tagUserLV.setVisibility(View.VISIBLE);
        }else {
            tagUserLV.setVisibility(View.GONE);
        }
*/
       // showTV.setText(tagUserName+"\n Id:"+tagUserId);
        //get Check Box And Description Text and Store a List
        getCheckBoxAndDesText();
    }

    public void getCheckBoxAndDesText(){

        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        if (checkBox1.isChecked()){
            jodDesEt1.setVisibility(View.VISIBLE);
            spinner1.setVisibility(View.VISIBLE);
           /* String checkBosText=checkBox1.getText().toString();
            String jobDesText=jodDesEt1.getText().toString();
             jobName1=checkBox1.getText().toString();
            jobDes1=jodDesEt1.getText().toString();
*/
           // jobList.add(checkBosText,jobDesText);

          //  jList.add(new JobC(checkBosText,jobDesText));


        }else if(!checkBox1.isChecked()){
            jodDesEt1.setText(null);
            jodDesEt1.setVisibility(View.GONE);
            spinner1.setVisibility(View.GONE);

        }
            }
        });

         checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        if (checkBox2.isChecked()){
            jodDesEt2.setVisibility(View.VISIBLE);
            spinner2.setVisibility(View.VISIBLE);
           /* String checkBosText=checkBox2.getText().toString();
            String jobDesText=jodDesEt2.getText().toString();
             jobName1=checkBox2.getText().toString();
            jobDes2=jodDesEt2.getText().toString();
*/
           // jobList.add(checkBosText,jobDesText);
           // jList.add(new JobC(checkBosText,jobDesText));
           // jobBuilder.append(checkBosText+",\nDes: "+jobDesText);


        }else if(!checkBox2.isChecked()){
            jodDesEt2.setText(null);
            jodDesEt2.setVisibility(View.GONE);
            spinner2.setVisibility(View.GONE);

        }
            }
        });

         checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        if (checkBox3.isChecked()){
            jodDesEt3.setVisibility(View.VISIBLE);
            spinner3.setVisibility(View.VISIBLE);
           /* String checkBosText=checkBox3.getText().toString();
            String jobDesText=jodDesEt3.getText().toString();
           // jobList.add(checkBosText,jobDesText);
            jList.add(new JobC(checkBosText,jobDesText));
          //  jobBuilder.append(checkBosText+",\nDes: "+jobDesText);
*/

        }else if(!checkBox3.isChecked()){
            jodDesEt3.setText(null);
            jodDesEt3.setVisibility(View.GONE);
            spinner3.setVisibility(View.GONE);

        }
            }
        });

         checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        if (checkBox4.isChecked()){
            jodDesEt4.setVisibility(View.VISIBLE);
            spinner4.setVisibility(View.VISIBLE);
           /* String checkBosText=checkBox4.getText().toString();
            String jobDesText=jodDesEt4.getText().toString();
           // jobList.add(checkBosText,jobDesText);
           // jList.add(new JobC(checkBosText,jobDesText));
*/

        }else if(!checkBox4.isChecked()){
            jodDesEt4.setText(null);
            jodDesEt4.setVisibility(View.GONE);
            spinner4.setVisibility(View.GONE);

        }
            }
        });

         checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        if (checkBox5.isChecked()){
            jodDesEt5.setVisibility(View.VISIBLE);
            spinner5.setVisibility(View.VISIBLE);
           /* String checkBosText=checkBox5.getText().toString();
            String jobDesText=jodDesEt5.getText().toString();
           // jobList.add(checkBosText,jobDesText);
           // jList.add(new JobC(checkBosText,jobDesText));
*/

        }else if(!checkBox5.isChecked()){
            jodDesEt5.setText(null);
            jodDesEt5.setVisibility(View.GONE);
            spinner5.setVisibility(View.GONE);

        }
            }
        });

         checkBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        if (checkBox6.isChecked()){
            jodDesEt6.setVisibility(View.VISIBLE);
            spinner6.setVisibility(View.VISIBLE);
           /* String checkBosText=checkBox6.getText().toString();
            String jobDesText=jodDesEt6.getText().toString();
           // jobList.add(checkBosText,jobDesText);
          //  jList.add(new JobC(checkBosText,jobDesText));
*/

        }else if(!checkBox6.isChecked()){
            jodDesEt6.setText(null);
            jodDesEt6.setVisibility(View.GONE);
            spinner6.setVisibility(View.GONE);

        }
            }
        });

         checkBox7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        if (checkBox7.isChecked()){
            jodDesEt7.setVisibility(View.VISIBLE);
            spinner7.setVisibility(View.VISIBLE);
           /* String checkBosText=checkBox7.getText().toString();
            String jobDesText=jodDesEt7.getText().toString();
           // jobList.add(checkBosText,jobDesText);
           // jList.add(new JobC(checkBosText,jobDesText));
*/

        }else if(!checkBox7.isChecked()){
            jodDesEt7.setText(null);
            jodDesEt7.setVisibility(View.GONE);
            spinner7.setVisibility(View.GONE);

        }
            }
        });

         checkBox8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        if (checkBox8.isChecked()){
            jodDesEt8.setVisibility(View.VISIBLE);
            spinner8.setVisibility(View.VISIBLE);
         /*   String checkBosText=checkBox8.getText().toString();
            String jobDesText=jodDesEt8.getText().toString();
           // jobList.add(checkBosText,jobDesText);
          //  jList.add(new JobC(checkBosText,jobDesText));
*/

        }else if(!checkBox8.isChecked()){
            jodDesEt8.setText(null);
            jodDesEt8.setVisibility(View.GONE);
            spinner8.setVisibility(View.GONE);

        }
            }
        });

         checkBox9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        if (checkBox9.isChecked()){
            jodDesEt9.setVisibility(View.VISIBLE);
            spinner9.setVisibility(View.VISIBLE);
           /* String checkBosText=checkBox9.getText().toString();
            String jobDesText=jodDesEt9.getText().toString();
           // jobList.add(checkBosText,jobDesText);
           // jList.add(new JobC(checkBosText,jobDesText));
*/

        }else if(!checkBox9.isChecked()){
            jodDesEt9.setText(null);
            jodDesEt9.setVisibility(View.GONE);
            spinner9.setVisibility(View.GONE);

        }
            }
        });

         checkBox10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        if (checkBox10.isChecked()){
            jodDesEt10.setVisibility(View.VISIBLE);
            spinner10.setVisibility(View.VISIBLE);
          /*  String checkBosText=checkBox10.getText().toString();
            String jobDesText=jodDesEt10.getText().toString();
           // jobList.add(checkBosText,jobDesText);
           // jList.add(new JobC(checkBosText,jobDesText));

*/
        }else if(!checkBox10.isChecked()){
            jodDesEt10.setText(null);
            jodDesEt10.setVisibility(View.GONE);
            spinner10.setVisibility(View.GONE);

        }
            }
        });

         checkBox11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        if (checkBox11.isChecked()){
            jodDesEt11.setVisibility(View.VISIBLE);
            spinner11.setVisibility(View.VISIBLE);
          /*  String checkBosText=checkBox11.getText().toString();
            String jobDesText=jodDesEt11.getText().toString();
           // jobList.add(checkBosText,jobDesText);
           // jList.add(new JobC(checkBosText,jobDesText));
*/

        }else if(!checkBox11.isChecked()){
            jodDesEt11.setText(null);
            jodDesEt11.setVisibility(View.GONE);
            spinner11.setVisibility(View.GONE);

        }
            }
        });

         checkBox12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        if (checkBox12.isChecked()){
            jodDesEt12.setVisibility(View.VISIBLE);
            spinner12.setVisibility(View.VISIBLE);
          /*  String checkBosText=checkBox12.getText().toString();
            String jobDesText=jodDesEt12.getText().toString();
           // jobList.add(checkBosText,jobDesText);
          //  jList.add(new JobC(checkBosText,jobDesText));
*/
        }else if(!checkBox12.isChecked()){
            jodDesEt12.setText(null);
            jodDesEt12.setVisibility(View.GONE);
            spinner12.setVisibility(View.GONE);

        }
            }
        });

         checkBox13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        if (checkBox13.isChecked()){
            jodDesEt13.setVisibility(View.VISIBLE);
            spinner13.setVisibility(View.VISIBLE);
          /*  String checkBosText=checkBox13.getText().toString();
            String jobDesText=jodDesEt13.getText().toString();
           // jobList.add(checkBosText,jobDesText);
           // jList.add(new JobC(checkBosText,jobDesText));
*/

        }else if(!checkBox13.isChecked()){
            jodDesEt13.setText(null);
            jodDesEt13.setVisibility(View.GONE);
            spinner13.setVisibility(View.GONE);

        }
            }
        });

      /*   jobBuilder.append("Job Name: "+jobName1+".\nDes: "+jobDes1+".\n");
         jobBuilder.append("Job Name: "+jobName1+".\nDes: "+jobDes1+".\n");
         jobBuilder.append("Job Name: "+jobName1+".\nDes: "+jobDes1+".\n");
         jobBuilder.append("Job Name: "+jobName1+".\nDes: "+jobDes1+".\n");
*/
        /*

        if (checkBox2.isChecked()){
            jodDesEt2.setVisibility(View.VISIBLE);
            String checkBosText=checkBox2.getText().toString();
            String jobDesText=jodDesEt2.getText().toString();
            jobList.add(checkBox2.getText().toString());
        }

        if (checkBox3.isChecked()){
            jobList.add(checkBox3.getText().toString());
        }

        if (checkBox4.isChecked()){
            jobList.add(checkBox4.getText().toString());
        }

        if (checkBox5.isChecked()){
            jobList.add(checkBox5.getText().toString());
        }

        if (checkBox6.isChecked()){
            jobList.add(checkBox6.getText().toString());
        }

        if (checkBox7.isChecked()){
            jobList.add(checkBox7.getText().toString());
        }

        if (checkBox8.isChecked()){
            jobList.add(checkBox8.getText().toString());
        }

        if (checkBox9.isChecked()){
            jobList.add(checkBox9.getText().toString());
        }

        if (checkBox10.isChecked()){
            jobList.add(checkBox10.getText().toString());
        }

        if (checkBox11.isChecked()){
            jobList.add(checkBox11.getText().toString());
        }

        if (checkBox12.isChecked()){
            jobList.add(checkBox12.getText().toString());
        }

        if (checkBox13.isChecked()){
            jobList.add(checkBox13.getText().toString());
        }*/
    }

    private void clientShow() {

    }

    private void findId() {
        uploadBtn=findViewById(R.id.uploadBtnWsId);
        dateWsEt=findViewById(R.id.date_WSId);
        jobDesEt=findViewById(R.id.jobDesEtId);
        addJobBtn=findViewById(R.id.addJobBtnId);
        userSV =(SearchView)findViewById(R.id.employeeName_WSId);
        companyWsAt=findViewById(R.id.companyName_WSId);
        employeeAt=findViewById(R.id.employeeAtId);

        text=findViewById(R.id.textId);

        checkBox1=findViewById(R.id.checkbox1Id);
        checkBox2=findViewById(R.id.checkbox2Id);
        checkBox3=findViewById(R.id.checkbox3Id);
        checkBox4=findViewById(R.id.checkbox4Id);
        checkBox5=findViewById(R.id.checkbox5Id);
        checkBox6=findViewById(R.id.checkbox6Id);
        checkBox7=findViewById(R.id.checkbox7Id);
        checkBox9=findViewById(R.id.checkbox9Id);
        checkBox10=findViewById(R.id.checkbox10Id);
        checkBox8=findViewById(R.id.checkbox8Id);
        checkBox11=findViewById(R.id.checkboxI11d);
        checkBox12=findViewById(R.id.checkbox12Id);
        checkBox13=findViewById(R.id.checkbox13Id);
       // showTV=findViewById(R.id.showTxtId);
        jodDesEt1=findViewById(R.id.jobDesId1);
        jodDesEt2=findViewById(R.id.jobDesId2);
        jodDesEt3=findViewById(R.id.jobDesId3);
        jodDesEt4=findViewById(R.id.jobDesId4);
        jodDesEt5=findViewById(R.id.jobDesId5);
        jodDesEt6=findViewById(R.id.jobDesId6);
        jodDesEt7=findViewById(R.id.jobDesId7);
        jodDesEt8=findViewById(R.id.jobDesId8);
        jodDesEt9=findViewById(R.id.jobDesId9);
        jodDesEt10=findViewById(R.id.jobDesId10);
        jodDesEt11=findViewById(R.id.jobDesId11);
        jodDesEt12=findViewById(R.id.jobDesId12);
        jodDesEt13=findViewById(R.id.jobDesId13);
       // categorySp=findViewById(R.id.categorySpId);
        tagUserLV=findViewById(R.id.userTagLVId);
        lltWsId=findViewById(R.id.lltWsLVId);
        closeWsId=findViewById(R.id.closeBtnWsId);
        addEmployeeBtn=findViewById(R.id.addEmployeeBtnId);
        scheduleEmployeeLv=findViewById(R.id.tagEmployeeListId);

        tagEmployeeTv=findViewById(R.id.tagEmployeeTvId);

        jobLv=findViewById(R.id.jobLVId);


        selectedUserGridView=findViewById(R.id.selectedUserGridViewId);
        selectedJobGridView=findViewById(R.id.selectedJobGridViewId);

        // Spinner Id
        jobCategorySp=findViewById(R.id.jobCategorySpId);
        jobTitleSp=findViewById(R.id.jobTitleSp);

        spinner1=findViewById(R.id.spinner1);
         spinner2=findViewById(R.id.spinner2);
         spinner3=findViewById(R.id.spinner3);
         spinner4=findViewById(R.id.spinner4);
         spinner5=findViewById(R.id.spinner5);
         spinner6=findViewById(R.id.spinner6);
         spinner7=findViewById(R.id.spinner7);
         spinner8=findViewById(R.id.spinner8);
         spinner1=findViewById(R.id.spinner1);
         spinner9=findViewById(R.id.spinner9);
         spinner10=findViewById(R.id.spinner10);
         spinner11=findViewById(R.id.spinner11);
         spinner12=findViewById(R.id.spinner12);
         spinner13=findViewById(R.id.spinner13);

         // Layout Id
        jobLLt=findViewById(R.id.jobtetLit);
        userLLt=findViewById(R.id.tetLit);

     //   userSV.setThreshold(1);
    }
// current date method
    public void date( ) {
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog=new DatePickerDialog(this,listener,year,month,dayOfMonth);
        datePickerDialog.show();
    }
    // date set Listener...
    public DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            Calendar calendar=Calendar.getInstance();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyy");

            calendar.set(year,month,dayOfMonth);

            date=simpleDateFormat.format(calendar.getTime());
            dateWsEt.setText(date);

        }
    };
//get checkBox value uses this method..

 public  void getId(){
    DatabaseReference tagUserIdRef= FirebaseDatabase.getInstance().getReference().child("TagUserId");
    //tagUserL.clear();
    tagUserIdRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot data: dataSnapshot.getChildren()){
                String userId=data.getValue(String.class);
                //  tagUserL.add(userId);
                tagUserId=userId;
            }
            // tagUserL.notifyAll();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    //get tag User name and UId
    // and Store this variable
    tagUserName=userNameAndID.getUserName();
    // tagUserId=tagUserL.toString();
    // Intent intent=getIntent();
    // tagUserId= intent.getStringExtra("UserId");
    // userSV.setText(tagUserName);
  //  showTV.setText(tagUserName+"\n ID:"+tagUserId);
}

    @Override
    public void removeGridUser(int position) {
        scheduleEmployeeList.remove(position);
        Common_Resouces.toastS(Write_Schedule.this,"Delete selected Employee");
        userGridViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeGridJob(int position) {
        selectedJobList.remove(position);
        Common_Resouces.toastS(Write_Schedule.this,"Delete selected job.\nnow selected "+selectedJobList.size()+" Job . ");
        jobGridViewAdapter.notifyDataSetChanged();
    }
}
