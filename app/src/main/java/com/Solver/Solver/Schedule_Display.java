package com.Solver.Solver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.Solver.Solver.Adepter.ScheduleAdapter;
import com.Solver.Solver.ModelClass.Common_Resouces;
import com.Solver.Solver.ModelClass.Factories;
import com.Solver.Solver.ModelClass.Schedule;
import com.Solver.Solver.ModelClass.SignUp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Schedule_Display extends AppCompatActivity {

    RecyclerView schedule_Recycler;
    List<Schedule> scheduleList;
    ScheduleAdapter scheduleAdapter;
    DatabaseReference allScheduleREf;
    DatabaseReference allScheduleREf_2;
    int factoryId ;
    String emp_Id;
    String companyName;
    String empName;
    String scheduleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule__display);
        schedule_Recycler=findViewById(R.id.schedule_RecyclerId);
        scheduleList=new ArrayList<>();

      //  getActionBar().setDisplayHomeAsUpEnabled(true);
      // getActionBar().setTitle("Schedule");

        allScheduleREf= FirebaseDatabase.getInstance().getReference().child("Schedule").child("All_ScheduleTbl");
        allScheduleREf_2= FirebaseDatabase.getInstance().getReference().child("All_ScheduleTbl");
      scheduleList.clear();

        Query allScheduleQuery=allScheduleREf_2.limitToLast(100);
        allScheduleQuery.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Schedule schedule=data.getValue(Schedule.class);
                    scheduleList.add(schedule);

                }

               Collections.reverse(scheduleList);
                scheduleAdapter=new ScheduleAdapter(Schedule_Display.this,scheduleList);
                LinearLayoutManager llm=new LinearLayoutManager(Schedule_Display.this);
               // llm.setOrientation(RecyclerView.VERTICAL);
                //llm.setReverseLayout(true);
               // llm.findFirstVisibleItemPosition();
                schedule_Recycler.setLayoutManager(llm);
               // schedule_Recycler.findViewHolderForAdapterPosition(0);
                schedule_Recycler.setAdapter(scheduleAdapter);
                scheduleAdapter.notifyDataSetChanged();


/*
                for(Schedule schedule:scheduleList){
                  //  Schedule schedule2 = new Schedule(scheduleId, emp_Id, scheduleList.get(i).getDate(), factoryId, scheduleList.get(i).getList());
                    allScheduleREf_2.child(schedule.getScheduleId()).setValue(schedule);

                }*/


/*
                for (int i=0; i<=scheduleList.size(); i++) {
                    try{

                    String companyName = scheduleList.get(i).getCompanyName();

                    String empName = scheduleList.get(i).getEmployeeName();
                    String scheduleId = scheduleList.get(i).getScheduleId();

                    DatabaseReference factoryRef = FirebaseDatabase.getInstance().getReference("factories");
                    final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User");

                    Query fact = factoryRef.orderByChild("company_name").equalTo(companyName);
                    fact.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                Factories factories = data.getValue(Factories.class);
                                factoryId = factories.getCustomer_id();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Query employeeRef = userRef.orderByChild("name").equalTo(empName);
                    employeeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                SignUp user = data.getValue(SignUp.class);
                                emp_Id = user.getUserId();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    // String scheduleId, String emp_id, String date, int factoryId, List<JobC> list
                    Schedule schedule2 = new Schedule(scheduleId, emp_Id, scheduleList.get(i).getDate(), factoryId, scheduleList.get(i).getList());
                    allScheduleREf_2.child(scheduleId).setValue(schedule2);

                   // Log.d("data",schedule2.toString());
                    }catch (IndexOutOfBoundsException e){
                        e.printStackTrace();
                    }
                }
*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

/*

    for (int i=0;i<=scheduleList.size();i++) {
    String companyName = scheduleList.get(i).getCompanyName();
    String empName = scheduleList.get(i).getEmployeeName();
    String scheduleId = scheduleList.get(i).getScheduleId();

    DatabaseReference factoryRef=FirebaseDatabase.getInstance().getReference("factories");
    final DatabaseReference userRef=FirebaseDatabase.getInstance().getReference("User");

    Query fact=factoryRef.orderByChild("company_name").equalTo(companyName);
    fact.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot data:dataSnapshot.getChildren()){
            Factories factories=data.getValue(Factories.class);
            factoryId =factories.getCustomer_id();
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
    });

    Query employeeRef=userRef.orderByChild("name").equalTo(empName);
    employeeRef.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        for (DataSnapshot data:dataSnapshot.getChildren()){
            SignUp user=data.getValue(SignUp.class);
            emp_Id=user.getUserId();
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
    });

   // String scheduleId, String emp_id, String date, int factoryId, List<JobC> list
Schedule schedule=new Schedule(scheduleId,emp_Id,scheduleList.get(i).getDate(),factoryId,scheduleList.get(i).getList());
    allScheduleREf.child(scheduleId).setValue(schedule);

}
*/


    }
}
