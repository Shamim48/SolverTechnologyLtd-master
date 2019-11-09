package com.Solver.Solver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.Solver.Solver.Adepter.ScheduleAdapter;
import com.Solver.Solver.Adepter.TagUserArrayAdapter;
import com.Solver.Solver.ModelClass.Client;
import com.Solver.Solver.ModelClass.Schedule;
import com.Solver.Solver.ModelClass.SignUp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Daily_Report extends AppCompatActivity {

   private CheckBox dateCb;
   private CheckBox factoryCb;
   private CheckBox categoryCb;
   private CheckBox employeeCb;

   private EditText dateEt;
   private AutoCompleteTextView factoryAt;
   private Spinner categorySp;
   private SearchView employeeSV;
   private TextView dateTv;
   private ListView tagUserLvDr;
   private Button queryBtn;
   private RecyclerView scheduleQueryRv;
   private TextView emptyTv;

    List<Schedule> scheduleList;
    ScheduleAdapter scheduleAdapter;
    DatabaseReference allScheduleREf;

    DatabaseReference clientRef;
    DatabaseReference userRef;


   ArrayAdapter<String> factoryAdapter;
    ArrayList<String> clientList;
    ArrayList<String> categoryList;
    ArrayAdapter<String> categoryAdapter;
    List<SignUp> tagUserList;
    TagUserArrayAdapter tagUserArrayAdapter;
    String date;
    String tagUserName;
    String tagUserId;
    LinearLayout lltDr;
    ImageButton closeBtnDr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daly_report);

        findId();


        clientRef= FirebaseDatabase.getInstance().getReference().child("Client");
        userRef=FirebaseDatabase.getInstance().getReference().child("User");
        allScheduleREf= FirebaseDatabase.getInstance().getReference().child("Schedule").child("All_ScheduleTbl");


        clientList=new ArrayList<>();
        tagUserLvDr.setVisibility(View.GONE);
        lltDr.setVisibility(View.GONE);
        scheduleList=new ArrayList<>();
        emptyTv.setVisibility(View.GONE);

        clientShow();

       // CategoryAdapter=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,categoryList);
       // categorySp.setAdapter(CategoryAdapter);
        dateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                date();
            }
        });
        employeeSV.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lltDr.setVisibility(View.VISIBLE);
                tagUserLvDr.setVisibility(View.VISIBLE);
                tagUser();
            }
        });


        closeBtnDr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lltDr.setVisibility(View.GONE);
            }
        });
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String date=dateEt.getText().toString();
                final String factory=factoryAt.getText().toString();
               // final String category=categorySp.getSelectedItem().toString();
                String selectedEmployee=employeeSV.getQuery().toString();


                ValueEventListener valueEventListener=new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        scheduleList.clear();

                        for (DataSnapshot data:dataSnapshot.getChildren()){
                            Schedule schedule=data.getValue(Schedule.class);
                            scheduleList.add(schedule);

                        }

                        Collections.reverse(scheduleList);
                        scheduleAdapter=new ScheduleAdapter(Daily_Report.this,scheduleList);
                        LinearLayoutManager llm=new LinearLayoutManager(Daily_Report.this);
                        // llm.setOrientation(RecyclerView.VERTICAL);
                        //llm.setReverseLayout(true);
                        // llm.findFirstVisibleItemPosition();
                        scheduleQueryRv.setLayoutManager(llm);
                        // schedule_Recycler.findViewHolderForAdapterPosition(0);
                        scheduleQueryRv.setAdapter(scheduleAdapter);
                        scheduleAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                ValueEventListener valueEventListenerDate=new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        scheduleList.clear();

                        for (DataSnapshot data:dataSnapshot.getChildren()){

                            Schedule schedule=data.getValue(Schedule.class);
                            if (schedule.getDate().equals(date)){
                                scheduleList.add(schedule);
                            }


                        }

                        Collections.reverse(scheduleList);
                        scheduleAdapter=new ScheduleAdapter(Daily_Report.this,scheduleList);
                        LinearLayoutManager llm=new LinearLayoutManager(Daily_Report.this);
                        // llm.setOrientation(RecyclerView.VERTICAL);
                        //llm.setReverseLayout(true);
                        // llm.findFirstVisibleItemPosition();
                        scheduleQueryRv.setLayoutManager(llm);
                        // schedule_Recycler.findViewHolderForAdapterPosition(0);
                        scheduleQueryRv.setAdapter(scheduleAdapter);
                        scheduleAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                ValueEventListener valueEventListenerFactory=new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        scheduleList.clear();

                        for (DataSnapshot data:dataSnapshot.getChildren()){

                            Schedule schedule=data.getValue(Schedule.class);
                            if (schedule.getCompanyName().equals(factory)){
                                scheduleList.add(schedule);
                            }


                        }

                        Collections.reverse(scheduleList);
                        scheduleAdapter=new ScheduleAdapter(Daily_Report.this,scheduleList);
                        LinearLayoutManager llm=new LinearLayoutManager(Daily_Report.this);
                        // llm.setOrientation(RecyclerView.VERTICAL);
                        //llm.setReverseLayout(true);
                        // llm.findFirstVisibleItemPosition();
                        scheduleQueryRv.setLayoutManager(llm);
                        // schedule_Recycler.findViewHolderForAdapterPosition(0);
                        scheduleQueryRv.setAdapter(scheduleAdapter);
                        scheduleAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };

                ValueEventListener valueEventListenerDateEmployee=new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        scheduleList.clear();

                        for (DataSnapshot data:dataSnapshot.getChildren()){

                            Schedule schedule=data.getValue(Schedule.class);
                            if (schedule.getDate().equals(date) && schedule.getCompanyName().equals(factory)){
                                scheduleList.add(schedule);
                            }


                        }

                        Collections.reverse(scheduleList);
                        scheduleAdapter=new ScheduleAdapter(Daily_Report.this,scheduleList);
                        LinearLayoutManager llm=new LinearLayoutManager(Daily_Report.this);
                        // llm.setOrientation(RecyclerView.VERTICAL);
                        //llm.setReverseLayout(true);
                        // llm.findFirstVisibleItemPosition();
                        scheduleQueryRv.setLayoutManager(llm);
                        // schedule_Recycler.findViewHolderForAdapterPosition(0);
                        scheduleQueryRv.setAdapter(scheduleAdapter);
                        scheduleAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                ValueEventListener valueEventListenerCategory=new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        scheduleList.clear();

                        for (DataSnapshot data:dataSnapshot.getChildren()){

                            Schedule schedule=data.getValue(Schedule.class);
                            int i;
                            for( i=0;i<=schedule.getList().size();i++) {
                                if (schedule.getList().get(i).getCategory().equals("Warranty.")) {
                                    scheduleList.add(schedule);
                                }

                            }

                        }

                        Collections.reverse(scheduleList);
                        scheduleAdapter=new ScheduleAdapter(Daily_Report.this,scheduleList);
                        LinearLayoutManager llm=new LinearLayoutManager(Daily_Report.this);
                        // llm.setOrientation(RecyclerView.VERTICAL);
                        //llm.setReverseLayout(true);
                        // llm.findFirstVisibleItemPosition();
                        scheduleQueryRv.setLayoutManager(llm);
                        // schedule_Recycler.findViewHolderForAdapterPosition(0);
                        scheduleQueryRv.setAdapter(scheduleAdapter);
                        scheduleAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                if(dateCb.isChecked() && factoryCb.isChecked() && employeeCb.isChecked()){
                    // Query query=allScheduleREf.orderByChild("date").equalTo(date);
                    Query query1=allScheduleREf.orderByChild("employeeName").equalTo(selectedEmployee);
                    query1.addValueEventListener(valueEventListenerDateEmployee);

                    if(!(date.equals(""))) {

                        dateTv.setText(date);
                    }
                    if(scheduleList.isEmpty()){
                        emptyTv.setVisibility(View.VISIBLE);
                    }
                    return;
                }

                if(dateCb.isChecked() && factoryCb.isChecked()){
                   // Query query=allScheduleREf.orderByChild("date").equalTo(date);
                    Query query1=allScheduleREf.orderByChild("companyName").equalTo(factory);
                    query1.addValueEventListener(valueEventListenerDate);
                    if(!(date.equals(""))) {

                        dateTv.setText(date);
                    }
                    if(scheduleList.isEmpty()){
                        emptyTv.setVisibility(View.VISIBLE);
                    }
                    return;
                }
                if(dateCb.isChecked() && employeeCb.isChecked()){
                    Query query=allScheduleREf.orderByChild("employeeName").equalTo(selectedEmployee);
                    query.addValueEventListener(valueEventListenerDate);
                    if(!(date.equals(""))) {

                        dateTv.setText(date);
                    }
                    if(scheduleList.isEmpty()){
                        emptyTv.setVisibility(View.VISIBLE);
                    }
                    return;
                }
                    if(factoryCb.isChecked() && employeeCb.isChecked()){
                    Query query=allScheduleREf.orderByChild("employeeName").equalTo(selectedEmployee);
                    query.addValueEventListener(valueEventListenerFactory);
                        if(scheduleList.isEmpty()){
                            emptyTv.setVisibility(View.VISIBLE);
                        }
                    return;
                }

                if(dateCb.isChecked()){
                    Query query=allScheduleREf.orderByChild("date").equalTo(date);
                    query.addValueEventListener(valueEventListener);

                    if(!(date.equals(""))) {

                        dateTv.setText(date);
                    }
                    if(scheduleList.isEmpty()){
                        emptyTv.setVisibility(View.VISIBLE);
                    }
                    return;
                }
                if (factoryCb.isChecked()){
                    Query query=allScheduleREf.orderByChild("companyName").equalTo(factory);
                    query.addValueEventListener(valueEventListener);
                    if(scheduleList.isEmpty()){
                        emptyTv.setVisibility(View.VISIBLE);
                    }
                    return;
                }
               /* if(categoryCb.isChecked()){
                    allScheduleREf.addValueEventListener(valueEventListenerCategory);
                    return;
                }
*/
                if (employeeCb.isChecked()){
                    Query query=allScheduleREf.orderByChild("employeeName").equalTo(selectedEmployee);
                    query.addValueEventListener(valueEventListener);
                    if(scheduleList.isEmpty()){
                        emptyTv.setVisibility(View.VISIBLE);
                    }
                    return;
                }
               /* if (categoryCb.isChecked()){
                    Query query=allScheduleREf.child("list").orderByChild("category").equalTo(category);
                    query.addValueEventListener(valueEventListener);
                    return;
                }
*/
              /*  allScheduleREf.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data:dataSnapshot.getChildren()){
                            Schedule schedule=data.getValue(Schedule.class);
                            scheduleList.add(schedule);

                        }

                        Collections.reverse(scheduleList);
                        scheduleAdapter=new ScheduleAdapter(Daily_Report.this,scheduleList);
                        LinearLayoutManager llm=new LinearLayoutManager(Daily_Report.this);
                        // llm.setOrientation(RecyclerView.VERTICAL);
                        //llm.setReverseLayout(true);
                        // llm.findFirstVisibleItemPosition();
                        scheduleQueryRv.setLayoutManager(llm);
                        // schedule_Recycler.findViewHolderForAdapterPosition(0);
                        scheduleQueryRv.setAdapter(scheduleAdapter);
                        scheduleAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

*/              if(!(date.equals(""))) {

                    dateTv.setText(date);
                }

            }
        });


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
                tagUserArrayAdapter=new TagUserArrayAdapter(Daily_Report.this,tagUserList);
                tagUserLvDr.setAdapter(tagUserArrayAdapter);
                tagUserArrayAdapter.notifyDataSetChanged();
                // userSV.setAdapter(tagUserArrayAdapter);

                getId();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        employeeSV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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


        tagUserLvDr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tagUserName=tagUserList.get(i).getName();
                tagUserId=tagUserList.get(i).getUserId();
                employeeSV.setQuery(tagUserName,true);
                // showTV.setText(tagUserName+"\n ID: "+tagUserId);
                lltDr.setVisibility(View.GONE);
                tagUserLvDr.setVisibility(View.GONE);

            }
        });
    }

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
       // tagUserName=userNameAndID.getUserName();
        // tagUserId=tagUserL.toString();
        // Intent intent=getIntent();
        // tagUserId= intent.getStringExtra("UserId");
        // userSV.setText(tagUserName);
        //  showTV.setText(tagUserName+"\n ID:"+tagUserId);
    }


    private void clientShow() {
      //  clientList.clear();
        clientRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Client client = data.getValue(Client.class);
                    //    String clientNam = client.getCompanyName();
                        clientList.add(client.getCompanyName());
                    }
                    clientList.add("Shamim Fation");
                    factoryAdapter = new ArrayAdapter<String>(Daily_Report.this, R.layout.spennersamplelayout, R.id.showTestSpinnerId, clientList);
                    factoryAt.setAdapter(factoryAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
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

             date = simpleDateFormat.format(calendar.getTime());
            dateEt.setText(date);

        }
    };
    private void findId() {
        dateCb=findViewById(R.id.checkboxDateDRId);
        //categoryCb=findViewById(R.id.categoryCBDRId);
        factoryCb=findViewById(R.id.factoryCBDRId);
        employeeCb=findViewById(R.id.employeeCbDRId);
        dateEt=findViewById(R.id.dateETDRId);
        factoryAt=findViewById(R.id.factoryATDRId);
       // categorySp=findViewById(R.id.categorySpDRId);
        employeeSV=findViewById(R.id.employeeSVDRID);
        dateTv=findViewById(R.id.dateTvDRId);
        tagUserLvDr=findViewById(R.id.userTagLVDRId);
        queryBtn=findViewById(R.id.queryBtnId);
        scheduleQueryRv=findViewById(R.id.dailyReport_RecyclerId);
        emptyTv=findViewById(R.id.emptyTvId);
        lltDr=findViewById(R.id.lltDrLVId);
        closeBtnDr=findViewById(R.id.closeBtnDrId);

        categoryList=new ArrayList<>();
        categoryList.add("Chose category.");
        categoryList.add("Warranty.");
        categoryList.add("Non Warranty.");
        categoryList.add("On Request.");
        categoryList.add("Installation.");


    }
}
