package com.Solver.Solver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.Solver.Solver.Adepter.ScheduleAdapter;
import com.Solver.Solver.ModelClass.Schedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule__display);
        schedule_Recycler=findViewById(R.id.schedule_RecyclerId);
        scheduleList=new ArrayList<>();
      //  getActionBar().setDisplayHomeAsUpEnabled(true);
       // getActionBar().setTitle("Schedule");
        allScheduleREf= FirebaseDatabase.getInstance().getReference().child("Schedule").child("All_ScheduleTbl");
      scheduleList.clear();
        allScheduleREf.addValueEventListener(new ValueEventListener() {

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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
