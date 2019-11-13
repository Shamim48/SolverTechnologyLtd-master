package com.Solver.Solver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Solver.Solver.Adepter.GroupMsgAdapter;
import com.Solver.Solver.ModelClass.Client;
import com.Solver.Solver.ModelClass.Common_Resouces;
import com.Solver.Solver.ModelClass.Factories;
import com.Solver.Solver.ModelClass.GroupMessage;
import com.Solver.Solver.ModelClass.JobC;
import com.Solver.Solver.ModelClass.Schedule;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GroupChatAtv extends AppCompatActivity {

    public final static int uid=432;
    private static final String TAG = "GroupChatAtv";
    NotificationCompat.Builder notification;
    Uri imageUri;
    Uri cropImageUri;
    Uri downloadUri;
    String imagePathDL;
    CheckBox factoryCb;
    CheckBox spearPartsCb;
    LinearLayout jobSpLt;


    public static final int REQUEST_CODE = 1;
    private ImageButton SendMessageButton;
    private ImageButton selectImageIBnt;
    private ImageView imageGC;
    private ImageButton shareLocationIBtn;
    private Spinner imageTypeSp;
    private EditText userMessageInput;
    private EditText spare_PartEt;
    private AutoCompleteTextView subEt;
    private Spinner jobSp;
    private LinearLayout tagClientAt;
    private Spinner factorySp;
    private ScrollView mScrollView;
    private TextView displayTextMessages;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, GroupNameRef, GroupMessageKeyRef, clientRef, jobRef, scheduleREf, allScheduleREf;
    private String currentGroupName, currentUserID, currentUserName, currentDate,currentSceDate, currentTime;
    private LinearLayout replyLot,imageLot;
    private ImageButton closeBtn,closeImgBtn;
    private String name_reply,comName_reply,job_reply,msg_reply,spearPart_reply,msg_type,imageUri_reply,msg_TypeImage;
    private TextView nameTv_Rp,clientTv_Rp,subTv_Rp,msgTv_Rp,spearPartTv_Rp;
    private Button addFactoryBtn;
    private View viewClientForm;
    private AutoCompleteTextView factoryAt;
    private Button cancelDlBtn;
    private Button addDlBtn;

    List<GroupMessage> groupMessagesList;
    List<String> factoryList=new ArrayList<>();
    GroupMsgAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<String> clientList;
    ArrayList<String> jobList = new ArrayList<>();
    ArrayAdapter<String> factoryAdapter;
    ArrayAdapter<String> factoryAdapter_At;
    ArrayAdapter<String> jobAdapter;
    Common_Resouces common_resouces;
    String[] clientNameArray;
    List<Schedule> yourStringArray;
    ArrayList<String> imageTypeAL;
    ArrayAdapter<String> imageTypeApter;
    UploadTask imageUploadTask;
    StorageReference groupImageRef;
    DatabaseReference factoryRef;

     String tagClientName="";

    String imageType="image";
    String messageIMGkEY;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat_atv);
        groupMessagesList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);

        common_resouces = new Common_Resouces();
        clientList = new ArrayList<>();

        if (getIntent() != null){
            currentGroupName = getIntent().getStringExtra("GroupName");
            msg_type=getIntent().getStringExtra("msgType");
            name_reply=getIntent().getStringExtra("userName");
            comName_reply=getIntent().getStringExtra("comName");
            job_reply=getIntent().getStringExtra("jobName");
            msg_reply=getIntent().getStringExtra("message");
            spearPart_reply=getIntent().getStringExtra("spearParts");
            try {

                imageUri_reply=getIntent().getStringExtra("image");
                msg_TypeImage=getIntent().getStringExtra("msgTypeImage");

            }catch (NullPointerException e){

            }

        }

        Toast.makeText(GroupChatAtv.this, currentGroupName, Toast.LENGTH_SHORT).show();

        // tagClientAt.setText("Abir Fation");
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("User");
        GroupNameRef = FirebaseDatabase.getInstance().getReference().child("Group").child(currentGroupName);
        clientRef = FirebaseDatabase.getInstance().getReference().child("Client");
        factoryRef= FirebaseDatabase.getInstance().getReference().child("factories");

        scheduleREf = FirebaseDatabase.getInstance().getReference().child("Schedule").child("ScheduleTbl");
        allScheduleREf = FirebaseDatabase.getInstance().getReference().child("Schedule").child("All_ScheduleTbl");
        jobRef = FirebaseDatabase.getInstance().getReference().child("Schedule").child("Job");
        groupImageRef = FirebaseStorage.getInstance().getReference().child("Group").child(currentGroupName);


        notification=new NotificationCompat.Builder(GroupChatAtv.this);
        InitializeFields();
        dateTime();
        clientShow();

        imageLot.setVisibility(View.GONE);
        replyLot.setVisibility(View.GONE);

        imageTypeAL=new ArrayList<>();
      /*  imageTypeAL.add("Chose Image Type");
        imageTypeAL.add("Log_Book");
        imageTypeAL.add("Machine Image");
        imageTypeAL.add("Spear parts Image");
        imageTypeAL.add("Bill challan");
        imageTypeAL.add("Money receipt");
        imageTypeAL.add("screen shot");
        imageTypeAL.add("Others");
*/

        DatabaseReference imageTypeRef=FirebaseDatabase.getInstance().getReference("Others").child("ImageType");

        //imageTypeRef.setValue(imageTypeAL);
        imageTypeAL.clear();
        imageTypeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    String imageType=data.getValue(String.class);
                    imageTypeAL.add(imageType);
                }
                imageTypeApter=new ArrayAdapter<>(GroupChatAtv.this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,imageTypeAL);
                imageTypeSp.setAdapter(imageTypeApter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        shareLocationIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ShareLocationAct.class);
                startActivity(intent);
            }
        });

        factoryCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(factoryCb.isChecked()){
                    tagClientAt.setVisibility(View.VISIBLE);
                    jobSpLt.setVisibility(View.VISIBLE);
                    addFactoryBtn.setVisibility(View.VISIBLE);
                }

                if (!factoryCb.isChecked()){
                    tagClientAt.setVisibility(View.GONE);
                    jobSpLt.setVisibility(View.GONE);
                    addFactoryBtn.setVisibility(View.GONE);
                }
            }
        });

        spearPartsCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spearPartsCb.isChecked()){
                    spare_PartEt.setVisibility(View.VISIBLE);

                }

                if(!spearPartsCb.isChecked()){
                    spare_PartEt.setVisibility(View.GONE);

                }
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replyLot.setVisibility(View.GONE);
                msg_type=null;
            }
        });

        closeImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageUri=null;
                imageLot.setVisibility(View.GONE);
            }
        });
        try {
            if(!(imageUri_reply.equals(""))){
                imageLot.setVisibility(View.VISIBLE);
                Glide.with(this).load(imageUri_reply).into(imageGC);
                return;
            }

        }catch (NullPointerException e){

        }
        try {
            if (msg_type.equals("Reply")) {
                replyLot.setVisibility(View.VISIBLE);
                nameTv_Rp.setText(name_reply);
                clientTv_Rp.setText(comName_reply);
                subTv_Rp.setText(job_reply);
                msgTv_Rp.setText(msg_reply);
                spearPartTv_Rp.setText(spearPart_reply);

            }

        }catch (Exception e){

           // Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }



        DatabaseReference clientRef = FirebaseDatabase.getInstance().getReference().child("Client");
        clientList.clear();
        clientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Client client = data.getValue(Client.class);
                    String comName = client.getCompanyName();
                    clientList.add(comName);
                }

                // clientAdapter=new ArrayAdapter<String>(GroupChatAtv.this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,clientList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        GetUserInfo();


        factoryList.clear();
       Query lastQuery= scheduleREf.child(currentUserID).child(currentSceDate);

        lastQuery.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for (DataSnapshot data:dataSnapshot.getChildren()){
                  Schedule schedule=data.getValue(Schedule.class);
                  try {
                      factoryList.add(schedule.getCompanyName());
                  }catch (Exception e){

                  }

              }
              factoryAdapter=new ArrayAdapter<String>(GroupChatAtv.this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,factoryList);
              factorySp.setAdapter(factoryAdapter);
              factoryAdapter.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });



        /* clientList.clear();
         *//*
        scheduleREf.child(currentUserID).child("companyName").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    // Schedule schedule=data.getValue(Schedule.class);
                    String comName=data.getValue(String.class);
                    tagClientAt.setText(comName);
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/

        addFactoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert=new AlertDialog.Builder(GroupChatAtv.this);
                viewClientForm=getLayoutInflater().inflate(R.layout.addfactory_for_schedule,null);
                findIdByClientDialogViw();
                alert.setView(viewClientForm);
                final AlertDialog alertDialogByClient=alert.create();
                alertDialogByClient.setCanceledOnTouchOutside(false);
                alertDialogByClient.show();

                factoryAdapter_At=new ArrayAdapter<String>(GroupChatAtv.this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,clientList);
                factoryAt.setAdapter(factoryAdapter_At);

                addDlBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        factoryList.clear();
                        String companyName = factoryAt.getText().toString().trim();
                        //companyNameEt.setText(bloodGroups);

                        if(companyName.isEmpty()){
                            factoryAt.setError("Please Enter Factory Name..!");
                            return;
                        }

                        Schedule schedule=new Schedule(companyName);
                        scheduleREf.child(currentUserID).child(currentSceDate).push().setValue(schedule).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Upload Successful",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),"Exception:"+task.getException().getMessage(),Toast.LENGTH_LONG).show();

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
        });

        scheduleREf.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    //  Schedule schedule=data.getValue(Schedule.class);
                    // clientList.add(schedule.getCompanyName());
                }
               /*
                try {
                    Iterator<DataSnapshot> items=dataSnapshot.getChildren().iterator();
                    clientList.clear();
                    HashMap<Schedule ,Object> user=null;
                    while (items.hasNext()){
                        DataSnapshot item=items.next();
                        user=(HashMap<Schedule,Object>) item.getValue(Schedule.class);
                        tagClientAt.setText(user.get("companyName").toString());
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"exception:"+e.getMessage().trim(),Toast.LENGTH_LONG).show();

                }*/

/*
                Map<String, String> clientName=new HashMap<>();
              clientName.get("comName");

                List<String> list = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String title = ds.child("companyName").getValue(String.class);
                    list.add(title);
                    tagClientAt.setText(title);

                }*/
/*

                    GenericTypeIndicator<Schedule> t = new GenericTypeIndicator<Schedule>(){
                    };
                  //  List<Schedule> client=data.getValue(t);
                   // String clientName=client.getCompanyName();

                    GenericTypeIndicator<List<Schedule>> tt = new GenericTypeIndicator<List<Schedule>>() {};

                    yourStringArray = dataSnapshot.getValue(tt);

                for (int i=0;i<=yourStringArray.size();i++){
                    String name=yourStringArray.get(i).getCompanyName();
                    clientList.add(name);

                }
*/
/*
                Object comName="";
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Schedule schedule=data.getValue(Schedule.class);
                     comName=schedule.getCompanyName();
                }

                tagClientAt.setText(comName.toString());

*/
              /* clientAdapter=new ArrayAdapter<>(GroupChatAtv.this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,clientList);
                tagClientAt.setAdapter(clientAdapter);
                clientAdapter.notifyDataSetChanged();
*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // jobList=getResources().getStringArray(R.array.jobList);
        jobList.add("");

        try {
            jobRef.child(currentUserID).child(currentSceDate).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        JobC job = data.getValue(JobC.class);
                        String jobTitle = job.getJobTitle();
                        // tagClientAt.setText(job.getJobTitle());
                        jobList.add(jobTitle);
                    }

                    jobList.add("New Job");
                    jobAdapter = new ArrayAdapter<>(GroupChatAtv.this, R.layout.spennersamplelayout, R.id.showTestSpinnerId, jobList);
                    jobSp.setAdapter(jobAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {

           // Toast.makeText(getApplicationContext(), "Exception: " + e.getMessage().trim(), Toast.LENGTH_LONG).show();

        }


        selectImageIBnt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String action;
                Intent imageIntent = new Intent();
                imageIntent.setAction(Intent.ACTION_GET_CONTENT);
                imageIntent.setType("image/*");
                startActivityForResult(imageIntent, REQUEST_CODE);
                imageLot.setVisibility(View.VISIBLE);

                // uploadImage();
            }
        });

        SendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(!(imageUri_reply.equals(""))){
                        saveReplyWithImageData();
                        return;
                    }
                }catch (NullPointerException e){

                }
                try {
                    if(msg_type.equals("Reply")) {

                        SaveReplyMessageInfoToDatabase();

                    }
                }catch (NullPointerException e){

                }
                if((imageUri!=null)){
                    uploadImage();
                }

              else{
                        SaveMessageInfoToDatabase();

                }

                //  userMessageInput.setText("");
                // mScrollView.fullScroll(ScrollView.FOCUS_DOWN);

                imageUri=null;

            }
        });
    }

    private void toast(String msg) {

        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

    }

    private void findIdByClientDialogViw() {
        factoryAt=viewClientForm.findViewById(R.id.addFactoryAtId);
        cancelDlBtn=viewClientForm.findViewById(R.id.cancelFactoryDlBtnId);
        addDlBtn=viewClientForm.findViewById(R.id.addFactoryDlBtnId);
    }

    private void saveReplyWithImageData() {
        String message = userMessageInput.getText().toString();
        // String subMSG = subEt.getText().toString();
        String subMSG = jobSp.getSelectedItem().toString();
          if(factoryCb.isChecked()){
            tagClientAt.setVisibility(View.VISIBLE);
            jobSpLt.setVisibility(View.VISIBLE);
              addFactoryBtn.setVisibility(View.VISIBLE);
            tagClientName = factorySp.getSelectedItem().toString();
        }

        if (!factoryCb.isChecked()){
            tagClientAt.setVisibility(View.GONE);
            jobSpLt.setVisibility(View.GONE);
            addFactoryBtn.setVisibility(View.GONE);
        }
        String sparePart = spare_PartEt.getText().toString();
        String messagekEY = GroupNameRef.push().getKey();
        String currentUserId = mAuth.getCurrentUser().getUid();

        if (TextUtils.isEmpty(message)) {
            Toast.makeText(GroupChatAtv.this, "Please write message first...", Toast.LENGTH_SHORT).show();
        } else {

/*
            HashMap<String, Object> groupMessageKey = new HashMap<>();
            GroupNameRef.updateChildren(groupMessageKey);

            GroupMessageKeyRef = GroupNameRef.child(messagekEY);
1
            HashMap<String, Object> messageInfoMap = new HashMap<>();
            messageInfoMap.put("userId", currentUserId);
            messageInfoMap.put("name", currentUserName);
            messageInfoMap.put("sub", subMSG);
            messageInfoMap.put("message", message);
            messageInfoMap.put("date", currentDate);
            messageInfoMap.put("time", currentTime);
            GroupMessageKeyRef.updateChildren(messageInfoMap);

            */

            GroupMessage groupMessageRep =new GroupMessage(currentUserName,currentUserId,subMSG,message, currentTime, currentDate, tagClientName, sparePart, msg_TypeImage, messagekEY, currentGroupName, name_reply,imageUri_reply) ;

            GroupNameRef.child(messagekEY).setValue(groupMessageRep);
            //subEt.setText(null);
            userMessageInput.setText(null);

            spare_PartEt.setText(null);
            msg_type=null;
            name_reply=null;
            comName_reply=null;
            job_reply=null;
            msg_reply=null;
            spearPart_reply=null;
            currentGroupName=null;
            imageUri_reply=null;
            imageLot.setVisibility(View.GONE);
            sendNotification(currentGroupName,message);

        }
        finish();
    }

    private void SaveReplyMessageInfoToDatabase() {
        String message = userMessageInput.getText().toString();
        // String subMSG = subEt.getText().toString();
        String subMSG = jobSp.getSelectedItem().toString();

        if(factoryCb.isChecked()){
            tagClientAt.setVisibility(View.VISIBLE);
            jobSpLt.setVisibility(View.VISIBLE);
            addFactoryBtn.setVisibility(View.VISIBLE);
            tagClientName = factorySp.getSelectedItem().toString();
        }

        if (!factoryCb.isChecked()){
            tagClientAt.setVisibility(View.GONE);
            jobSpLt.setVisibility(View.GONE);
            addFactoryBtn.setVisibility(View.GONE);
        }
        String sparePart = spare_PartEt.getText().toString();
        String messagekEY = GroupNameRef.push().getKey();
        String currentUserId = mAuth.getCurrentUser().getUid();

        if (TextUtils.isEmpty(message)) {
            Toast.makeText(GroupChatAtv.this, "Please write message first...", Toast.LENGTH_SHORT).show();
        } else {

/*
            HashMap<String, Object> groupMessageKey = new HashMap<>();
            GroupNameRef.updateChildren(groupMessageKey);

            GroupMessageKeyRef = GroupNameRef.child(messagekEY);

            HashMap<String, Object> messageInfoMap = new HashMap<>();
            messageInfoMap.put("userId", currentUserId);
            messageInfoMap.put("name", currentUserName);
            messageInfoMap.put("sub", subMSG);
            messageInfoMap.put("message", message);
            messageInfoMap.put("date", currentDate);
            messageInfoMap.put("time", currentTime);
            GroupMessageKeyRef.updateChildren(messageInfoMap);

            */

            GroupMessage groupMessageRep =new GroupMessage(currentUserName, currentUserId, subMSG, message, currentTime, currentDate, tagClientName, sparePart, msg_type, messagekEY, currentGroupName, name_reply, comName_reply, job_reply, msg_reply, spearPart_reply) ;

                        GroupNameRef.child(messagekEY).setValue(groupMessageRep);
                //subEt.setText(null);
                    userMessageInput.setText(null);
                   // tagClientAt.setText(null);
                    spare_PartEt.setText(null);
                    msg_type=null;
                    name_reply=null;
                    comName_reply=null;
                    job_reply=null;
                    msg_reply=null;
                    spearPart_reply=null;
                    currentGroupName=null;
                    imageUri_reply=null;
                    replyLot.setVisibility(View.GONE);

                sendNotification(currentGroupName,message);

            }
        finish();


    }

    private void uploadImage() {
        Context context;
        final ProgressDialog progressDialog=new ProgressDialog(GroupChatAtv.this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Image Sending");
        progressDialog.show();
        DatabaseReference imageKeyRef = GroupNameRef.push();
        String imagePushId = imageKeyRef.getKey();
        final String imageSub=imageTypeSp.getSelectedItem().toString();
        msg = userMessageInput.getText().toString();
         messageIMGkEY = GroupNameRef.push().getKey();
        StorageReference imagePath = groupImageRef.child(imagePushId + ".jpg");
        imagePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                downloadUri = uriTask.getResult();

                GroupMessage groupImage=new GroupMessage(currentUserName,msg,currentUserID,currentTime,currentDate,downloadUri.toString(),imageType,currentGroupName,imageSub);
                GroupNameRef.child(messageIMGkEY).setValue(groupImage);

                imageUri=null;
                userMessageInput.setText("");
                imageLot.setVisibility(View.GONE);
                sendNotification(currentGroupName,msg);
                progressDialog.dismiss();
            }
        });

               /* .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    String downloadImageUri=task.getResult().toString();
                    imagePathDL =downloadImageUri.toString();
                    Toast.makeText(getApplicationContext(),"Image Upload Successful",Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        /*imageUploadTask.continueWith(new Continuation() {
            @Override
            public Object then(@NonNull Task task) throws Exception {
                if (!task.isSuccessful()){
                    throw  task.getException();
                }
                return imagePath.getDownloadUrl();
            }
        }).*/


    }

    @Override
    protected void onStart() {
        super.onStart();

       // .orderByChild("date").equalTo(currentDate)
        GroupNameRef.limitToLast(50).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupMessagesList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    GroupMessage groupMessage = data.getValue(GroupMessage.class);
                    groupMessagesList.add(groupMessage);
                }
                adapter = new GroupMsgAdapter(GroupChatAtv.this, groupMessagesList);
                //recyclerView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GroupChatAtv.this);
                linearLayoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(linearLayoutManager);
               recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void InitializeFields() {
        getSupportActionBar().setTitle(currentGroupName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        SendMessageButton = (ImageButton) findViewById(R.id.send_message_button);
        selectImageIBnt = (ImageButton) findViewById(R.id.selectImageIBtnId);
        shareLocationIBtn = (ImageButton) findViewById(R.id.selectLocationIBtnId);
        // subEt = (AutoCompleteTextView) findViewById(R.id.subEtId);
        jobSp = findViewById(R.id.jobSpId);
        userMessageInput = (EditText) findViewById(R.id.input_group_message);
        spare_PartEt = (EditText) findViewById(R.id.spareParts_group_message);
        tagClientAt = (LinearLayout) findViewById(R.id.tagClientAtId);
        factorySp=findViewById(R.id.factorySpId);
        imageGC = findViewById(R.id.imageGCId);
        replyLot=findViewById(R.id.replyLotId);
        imageLot=findViewById(R.id.imageLotId);
        closeBtn=findViewById(R.id.closeBtnId);
        imageTypeSp=findViewById(R.id.imageTypeSpId);
        closeImgBtn=findViewById(R.id.closeImgBtnId);
        nameTv_Rp=findViewById(R.id.nameTv_RpId);
        clientTv_Rp=findViewById(R.id.clientTv_RpId);
        subTv_Rp=findViewById(R.id.subTv_RpId);
        msgTv_Rp=findViewById(R.id.msgTv_RpId);
        spearPartTv_Rp=findViewById(R.id.sparePartTv_RpId);

        addFactoryBtn=findViewById(R.id.addFactoryBtnId);
        // displayTextMessages =findViewById(R.id.group_chat_textId);
        // mScrollView = (ScrollView) findViewById(R.id.my_scroll_view);

        factoryCb=findViewById(R.id.factoryCbId);
        spearPartsCb=findViewById(R.id.spearPartsCbId);
        jobSpLt=findViewById(R.id.jobSpLtId);

    }

  public void dateTime(){
    Calendar calForDate = Calendar.getInstance();
    SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
    currentDate = currentDateFormat.format(calForDate.getTime());

     Calendar calForSce = Calendar.getInstance();
    SimpleDateFormat currentDateFormatSce = new SimpleDateFormat("dd-MM-yyy");
    currentSceDate = currentDateFormatSce.format(calForSce.getTime());



    Calendar calForTime = Calendar.getInstance();
    SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
    currentTime = currentTimeFormat.format(calForTime.getTime());
}
    private void GetUserInfo() {
        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    currentUserName = dataSnapshot.child("name").getValue().toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void clientShow() {
        clientList.clear();
        factoryRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Factories client=data.getValue(Factories.class);
                    String clientNam=client.getCompany_name();
                    clientList.add(clientNam);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void SaveMessageInfoToDatabase() {

        String message = userMessageInput.getText().toString();
        // String subMSG = subEt.getText().toString();
        String subMSG = jobSp.getSelectedItem().toString();

               if(factoryCb.isChecked()){
                   tagClientAt.setVisibility(View.VISIBLE);
                   jobSpLt.setVisibility(View.VISIBLE);
                   addFactoryBtn.setVisibility(View.VISIBLE);
                   tagClientName = factorySp.getSelectedItem().toString();
               }

               if (!factoryCb.isChecked()){
                   tagClientAt.setVisibility(View.GONE);
                   jobSpLt.setVisibility(View.GONE);
                   addFactoryBtn.setVisibility(View.GONE);
               }

        String sparePart = spare_PartEt.getText().toString();
        String messagekEY = GroupNameRef.push().getKey();
        String currentUserId = mAuth.getCurrentUser().getUid();

        if (TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Please write message first...", Toast.LENGTH_SHORT).show();
        } else {

/*
            HashMap<String, Object> groupMessageKey = new HashMap<>();
            GroupNameRef.updateChildren(groupMessageKey);
            GroupMessageKeyRef = GroupNameRef.child(messagekEY);
            HashMap<String, Object> messageInfoMap = new HashMap<>();
            messageInfoMap.put("userId", currentUserId);
            messageInfoMap.put("name", currentUserName);
            messageInfoMap.put("sub", subMSG);
            messageInfoMap.put("message", message);
            messageInfoMap.put("date", currentDate);
            messageInfoMap.put("time", currentTime);
            GroupMessageKeyRef.updateChildren(messageInfoMap);
            */
            GroupMessage groupMessage = new GroupMessage(currentUserName, currentUserId, subMSG, message, currentTime, currentDate, tagClientName, sparePart,messagekEY,currentGroupName,"text");
            GroupNameRef.child(messagekEY).setValue(groupMessage);
            //subEt.setText(null);
            userMessageInput.setText(null);
           // tagClientAt.setText(null);
            tagClientName.equals("");
            spare_PartEt.setText(null);

            sendNotification(currentGroupName,message);

        }
    }
    private void DisplayMessages(DataSnapshot dataSnapshot) {
        Iterator iterator = dataSnapshot.getChildren().iterator();

        while (iterator.hasNext()) {
            String chatDate = (String) ((DataSnapshot) iterator.next()).getValue();
            String chatMessage = (String) ((DataSnapshot) iterator.next()).getValue();
            String chatName = (String) ((DataSnapshot) iterator.next()).getValue();
            String chatTime = (String) ((DataSnapshot) iterator.next()).getValue();

            displayTextMessages.append(chatName + " :\n" + chatMessage + "\n" + chatTime + "     " + chatDate + "\n\n\n");

            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null && data.getData() != null) {
            imageUri = data.getData();

            // Picasso.get().load(imageUri).rotate(270).into(profileIV);
            //this is file compress method
            // setPic();
/*
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(2, 2)
                    .setAutoZoomEnabled(true)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                cropImageUri = result.getUri();*/

            Glide.with(imageGC).load(imageUri)
                    .placeholder(R.drawable.ic_touch_app_black_24dp)
                    .into(imageGC);

        }

    }

    public void sendNotification(String title,String message){
        notification.setSmallIcon(R.drawable.solverlogo);
        notification.setContentTitle(title);
        notification.setContentText(message);
        notification.setWhen(System.currentTimeMillis());
       // notification.setAutoCancel(true);
        String action;
        Intent intent=new Intent(this,GroupChatAtv.class);

        TaskStackBuilder taskStackBuilder= TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(GroupChatAtv.this);
        taskStackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent=taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(uid,notification.build());

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem menuItem=menu.findItem(R.id.subscribe_group_id);
        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("isNotificationActive").child(currentUserID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()){
                  String val= dataSnapshot.child("isActive").getValue().toString();
                  if (val.equals("true")){
                      menuItem.setIcon(R.drawable.ic_add_notification2);
                      Toast.makeText(GroupChatAtv.this, "Notification on", Toast.LENGTH_SHORT).show();
                  }else if (val.equals("false")){
                      menuItem.setIcon(R.drawable.ic_add_group);
                      Toast.makeText(GroupChatAtv.this, "Notification off", Toast.LENGTH_SHORT).show();

                  }
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.notification,menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.subscribe_group_id) {
            //subscribe topic for group Notification
            final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("isNotificationActive").child(currentUserID);
           ref.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                   if (dataSnapshot.exists()){
                       String val=dataSnapshot.child("isActive").getValue().toString();
                       if (val.equals("true")){
                           ref.child("isActive").setValue("false");
                           unSubscribeToTopic();
                       }else if (val.equals("false")){
                           ref.child("isActive").setValue("true");
                           subscribeToTopic();
                       }
                   }else {
                       Map<String,String> data=new HashMap();
                       data.put("isActive","true");
                       ref.setValue(data).addOnSuccessListener(GroupChatAtv.this, new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                               Toast.makeText(GroupChatAtv.this, "Subscribe Successful", Toast.LENGTH_SHORT).show();
                           }
                       });
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });
        }
if (item.getItemId()==android.R.id.home){
    onBackPressed();

}

        return true;
    }

    private void unSubscribeToTopic() {
        //UnSubscribe topic for group Notification
        FirebaseMessaging.getInstance().unsubscribeFromTopic(currentGroupName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG,"Error to Subscribe Topic");
                        }
                        Log.i(TAG, "UnSubscribe  Successful");
                    }
                });
    }

    private void subscribeToTopic() {
        //subscribe topic for group Notification
        FirebaseMessaging.getInstance().subscribeToTopic(currentGroupName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG,"Error to Subscribe Topic");
                        }
                        Log.i(TAG, "Subscribe  Successful");

                    }
                });
    }

}
