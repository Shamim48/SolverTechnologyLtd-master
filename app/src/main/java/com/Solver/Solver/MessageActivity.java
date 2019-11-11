package com.Solver.Solver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Solver.Solver.Adepter.MessageAdapter;
import com.Solver.Solver.Fragment.APIService;
import com.Solver.Solver.ModelClass.Chat;
import com.Solver.Solver.ModelClass.SignUp;
import com.Solver.Solver.Notifications.Client;
import com.Solver.Solver.Notifications.Data;
import com.Solver.Solver.Notifications.MyResponse;
import com.Solver.Solver.Notifications.Sender;
import com.Solver.Solver.Notifications.Token;
import com.bumptech.glide.Glide;
import com.developers.imagezipper.ImageZipper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MessageActivity extends AppCompatActivity {


    public final static int uid=432;
    private static final String TAG = "GroupChatAtv";
    NotificationCompat.Builder notification;
    Uri imageUri;
    Uri cropImageUri;
    Uri downloadUri;
    String imagePathDL;
    String msg_Type="image";
    public static final int REQUEST_CODE = 1;
    private ImageButton SendMessageButton;
    private ImageButton selectImageIBnt;
    private ImageView imageGC;
    private LinearLayout replyLot,imageLot;
    private ImageButton closeBtn,closeImgBtn;
    private String msg_type,imageUri_reply,msg_reply,msg_TypeImage;

    CircleImageView profile_image;
    TextView username,msgTv_Rp;

    byte[] image_Final;
    Bitmap compressedImage;

    FirebaseUser fuser;
    DatabaseReference reference;
    DatabaseReference imageKeyRef;
    String sender;
    String msg;
    String currentUserName;
    String currentDate, currentTime;
    DatabaseReference userRef;

    ImageButton btn_send;
    EditText text_send;

    MessageAdapter messageAdapter;
    List<Chat> mchat;

    RecyclerView recyclerView;

    Intent intent;

    ValueEventListener seenListener;

    String userid;
    String replyTextSender,msg_type_Reply;
  //  private String name_reply,comName_reply,job_reply,msg_reply,spearPart_reply,msg_type,imageUri_reply,msg_TypeImage;
   // private TextView nameTv_Rp,clientTv_Rp,subTv_Rp,msgTv_Rp,spearPartTv_Rp;

    APIService apiService;

    boolean notify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      reference=FirebaseDatabase.getInstance().getReference();
        imageKeyRef = reference.child("Chats");
        userRef = FirebaseDatabase.getInstance().getReference().child("User");

        InitializeFields();
        dateTime();

        if (getIntent() != null){
            msg_type_Reply=getIntent().getStringExtra("msgType");
            msg_reply=getIntent().getStringExtra("message");
            replyTextSender=getIntent().getStringExtra("senderName");
            //msg_Type=getIntent().getStringExtra("msgType");
            //msg_TypeImage=getIntent().getStringExtra("msgTypeImage");

            try {

                imageUri_reply=getIntent().getStringExtra("image");
                msg_TypeImage=getIntent().getStringExtra("msgTypeImage");

            }catch (NullPointerException e){

            }

        }


        // setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // and this
                startActivity(new Intent(MessageActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        intent = getIntent();
        userid = intent.getStringExtra("userid");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
          sender=fuser.getUid();

        userRef.child(sender).addValueEventListener(new ValueEventListener() {
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

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify = true;
                 msg = text_send.getText().toString();
                try {
                    if(!(imageUri_reply.equals(""))){
                        sentReplyWithImage(fuser.getUid(),userid,msg);
                        return;
                    }
                }catch (NullPointerException e){

                }
                try {
                    if(msg_type_Reply.equals("msgReply")) {

                       sendReplyMessage(fuser.getUid(),userid,msg);
                       return;

                    }
                }catch (NullPointerException e){

                }
                if(imageUri!=null){
                    uploadImage(sender,userid,msg);

                }else {
                if (!msg.equals("")){
                    sendMessage(fuser.getUid(), userid, msg);
                  //  Toast.makeText(MessageActivity.this, "This is test Mode", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
                }
                imageUri=null;
                imageUri_reply=null;
                msg_type=null;
                msg_reply=null;
                msg_TypeImage=null;
                text_send.setText("");
                imageLot.setVisibility(View.GONE);
            }
        });

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
        imageLot.setVisibility(View.GONE);
        replyLot.setVisibility(View.GONE);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replyLot.setVisibility(View.GONE);
                msg_type_Reply=null;
            }
        });

        try {
            if(msg_type_Reply.equals("msgReply")){
                replyLot.setVisibility(View.VISIBLE);
                msgTv_Rp.setText(msg_reply);

            }
        }catch (Exception e){

        }

        try {
            if(msg_TypeImage.equals("imageReply")){
                imageLot.setVisibility(View.VISIBLE);
                Glide.with(imageGC).load(imageUri_reply).into(imageGC);
            }
        }catch (Exception e){

        }


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


            reference = FirebaseDatabase.getInstance().getReference("User").child(userid);

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    SignUp user = dataSnapshot.getValue(SignUp.class);
                    username.setText(user.getName());
                    if (user.getImageUrl().equals("default")) {
                        profile_image.setImageResource(R.mipmap.ic_launcher);
                    } else {
                        //and this
                        Glide.with(getApplicationContext()).load(user.getImageUrl()).into(profile_image);
                    }

                    readMesagges(fuser.getUid(), userid, user.getImageUrl());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            seenMessage(userid);
        }catch (Exception e){

        }


    }


    private void sentReplyWithImage(final String sender, final String receiver, final String message){
        //String message =text_send.getText().toString();
        String textRep =msgTv_Rp.getText().toString();

        if (TextUtils.isEmpty(message)) {
            Toast.makeText(MessageActivity.this, "Please write message first...", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Chats");
/*
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);*/
            String msgKey=reference.push().getKey();
            //  public Chat(String msgId,String senderName,String receiverName,String sender, String receiver, String message,String replyText,String msg_Type,String time,String date, boolean isseen) {

            Chat chat=new Chat(msgKey,currentUserName,replyTextSender,sender,receiver,message,textRep,imageUri_reply,msg_TypeImage,currentTime,currentDate,false);

            reference.child(msgKey).setValue(chat).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                      //  Toast.makeText(getApplicationContext(),"msg Reply successfully sended",Toast.LENGTH_LONG).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });

            // add user to chat fragment
            final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                    .child(fuser.getUid())
                    .child(userid);

            chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()){
                        chatRef.child("id").setValue(userid);
                    }
                }

                //SDSFSDAFAS
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            final DatabaseReference chatRefReceiver = FirebaseDatabase.getInstance().getReference("Chatlist")
                    .child(userid)
                    .child(fuser.getUid());
            chatRefReceiver.child("id").setValue(fuser.getUid());

            final String msg = message;

            reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    SignUp user = dataSnapshot.getValue(SignUp.class);
                    if (notify) {
                        sendNotifiaction(userid, user.getName(), msg);


                    }
                    notify = false;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            text_send.setText(null);
            msg_type=null;
            msgTv_Rp.setText("");
            replyLot.setVisibility(View.GONE);
            msg_reply=null;
            imageUri_reply=null;
            replyLot.setVisibility(View.GONE);
            imageUri_reply=null;
            imageLot.setVisibility(View.GONE);

            //  sendMessage(fuser.getUid(),userid,message);

            seenMessage(userid);
        }
        finish();
    }

    private void sendReplyMessage(String sender, final String receiver, String message) {
        //String message =text_send.getText().toString();
        String textRep =msgTv_Rp.getText().toString();

        if (TextUtils.isEmpty(message)) {
            Toast.makeText(MessageActivity.this, "Please write message first...", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Chats");
/*
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);*/
            String msgKey=reference.push().getKey();
           //  public Chat(String msgId,String senderName,String receiverName,String sender, String receiver, String message,String replyText,String msg_Type,String time,String date, boolean isseen) {

                Chat chat=new Chat(msgKey,currentUserName,replyTextSender,sender,receiver,message,textRep,msg_type_Reply,currentTime,currentDate,false);

            reference.child(msgKey).setValue(chat).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                       // Toast.makeText(getApplicationContext(),"msg Reply successfully sended",Toast.LENGTH_LONG).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });

            // add user to chat fragment
            final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                    .child(fuser.getUid())
                    .child(userid);

            chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()){
                        chatRef.child("id").setValue(userid);
                    }
                }

                //SDSFSDAFAS
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            final DatabaseReference chatRefReceiver = FirebaseDatabase.getInstance().getReference("Chatlist")
                    .child(userid)
                    .child(fuser.getUid());
            chatRefReceiver.child("id").setValue(fuser.getUid());

            final String msg = message;

            reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    SignUp user = dataSnapshot.getValue(SignUp.class);
                    if (notify) {
                        sendNotifiaction(userid, user.getName(), msg);


                    }
                    notify = false;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            text_send.setText(null);
            msg_type=null;
            msgTv_Rp.setText("");
            replyLot.setVisibility(View.GONE);
            msg_reply=null;
            imageUri_reply=null;
            replyLot.setVisibility(View.GONE);

          //  sendMessage(fuser.getUid(),userid,message);


            seenMessage(userid);
        }
        finish();

    }

    private void seenMessage(final String userid){
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(fuser.getUid()) && chat.getSender().equals(userid)){
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void uploadImage(final String sender, final String receiver, final String msg) {
        Context context;
       /* final ProgressDialog progressDialog=new ProgressDialog(MessageActivity.this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Image Sending");
        progressDialog.show();
*/

        final String imagePushId = imageKeyRef.push().getKey();
       // messageIMGkEY = GroupNameRef.push().getKey();
       // StorageReference imagePath = groupImageRef.child(imagePushId + ".jpg");
        StorageReference imagePath = FirebaseStorage.getInstance().getReference("chatImage").child(imagePushId + ".jpg");
       // UploadTask uploadTask=imagePath.putBytes(image_Final);
       // imagePath.putFile(imageUri)

        imagePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                downloadUri = uriTask.getResult();

              //  GroupMessage groupImage=new GroupMessage(currentUserName,currentUserID,currentTime,currentDate,downloadUri.toString(),imageType,currentGroupName);
              //  GroupNameRef.child(messageIMGkEY).setValue(groupImage);

                Chat chat=new Chat(imagePushId,currentUserName,sender,receiver,msg_Type,downloadUri.toString(),msg,currentTime,currentDate,false);

                imageKeyRef.child(imagePushId).setValue(chat).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Image Msg Upload Success..",Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Image Msg Upload unSuccess: "+e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

                imageUri=null;
                imageLot.setVisibility(View.GONE);
                sendNotifiaction(fuser.getUid(),userid,username+": sent a new Photo.");
               // progressDialog.dismiss();
            }
        });

/*
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);*/
       // String sender, String receiver, String msg_Type, String imageUri, boolean isseen


        // add user to chat fragment
        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(fuser.getUid())
                .child(userid);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){

                    chatRef.child("id").setValue(userid);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference chatRefReceiver = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(userid)
                .child(fuser.getUid());
        chatRefReceiver.child("id").setValue(fuser.getUid());

       // final String msg = message;

        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SignUp user = dataSnapshot.getValue(SignUp.class);
                if (notify) {
                    sendNotifiaction(receiver, user.getName(), "New Photo");
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

        imageUri=null;
        text_send.setText("");
        imageLot.setVisibility(View.GONE);

    }

    public void dateTime(){
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        currentDate = currentDateFormat.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
        currentTime = currentTimeFormat.format(calForTime.getTime());
    }

    private void sendMessage(String sender, final String receiver, String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Chats");
/*
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);*/
        String msgKey=reference.push().getKey();
       // Chat(String msgId,String senderName,String sender, String receiver, String message,String msg_Type,String time,String date, boolean isseen)
        Chat chat=new Chat(msgKey,currentUserName,sender,receiver,message,"text",currentTime,currentDate,false);

        reference.child(msgKey).setValue(chat).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                   // Toast.makeText(getApplicationContext(),"msg successfully sended",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        // add user to chat fragment
        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(fuser.getUid())
                .child(userid);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    chatRef.child("id").setValue(userid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference chatRefReceiver = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(userid)
                .child(fuser.getUid());
        chatRefReceiver.child("id").setValue(fuser.getUid());

        final String msg = message;

        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SignUp user = dataSnapshot.getValue(SignUp.class);
                if (notify) {
                    sendNotifiaction(receiver, user.getName(), msg);
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void InitializeFields() {

        SendMessageButton = (ImageButton) findViewById(R.id.send_message_button);
        selectImageIBnt = (ImageButton) findViewById(R.id.image_send);

        // subEt = (AutoCompleteTextView) findViewById(R.id.subEtId);
        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);

        imageGC = findViewById(R.id.imageGCId);
        replyLot=findViewById(R.id.replyLotId);
        imageLot=findViewById(R.id.imageLotId);
        closeBtn=findViewById(R.id.closeBtnId);
        closeImgBtn=findViewById(R.id.closeImgBtnId);

        msgTv_Rp=findViewById(R.id.msgTv_RpId);

        // displayTextMessages =findViewById(R.id.group_chat_textId);
        // mScrollView = (ScrollView) findViewById(R.id.my_scroll_view);

    }

    private void sendNotifiaction(String receiver, final String username, final String message){


        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(fuser.getUid(), R.mipmap.ic_launcher, username+": "+message, "New Message",
                            userid);

                    Sender sender = new Sender(data, token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200){
                                        if (response.body().success != 1){
                                            Toast.makeText(MessageActivity.this,"Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readMesagges(final String myid, final String userid, final String imageurl){
        mchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                        mchat.add(chat);
                    }

                    messageAdapter = new MessageAdapter(MessageActivity.this, mchat, imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && data != null && data.getData() != null) {

            imageUri = data.getData();

            File actualImage=new File(imageUri.getPath());
           // compressedImageFile = new Compressor(this).compressToFile(actualImage);
            try {
  compressedImage = new Compressor(this)
                         .setMaxWidth(480)
                         .setMaxHeight(480)
                         .setQuality(100)
                         .compressToBitmap(actualImage);

          /*      compressedImage = new Compressor(this)
                        .setMaxWidth(640)
                        .setMaxHeight(480)
                        .setQuality(75)
                        .compressToFile(actualImage);
*/
/*

                File imageZipperFile=new ImageZipper(MessageActivity.this)
                        .setQuality(75)
                        .setMaxWidth(480)
                        .setMaxHeight(480)
                        .compressToFile(actualImage);
*/
                Bitmap b=new ImageZipper(MessageActivity.this).compressToBitmap(actualImage);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                image_Final = baos.toByteArray();

            } catch (IOException e) {
                e.printStackTrace();
            }


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    private void currentUser(String userid){
        SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
        editor.putString("currentuser", userid);
        editor.apply();
    }

    private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);

        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
        currentUser(userid);
    }

    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(seenListener);
        status("offline");
        currentUser("none");
    }

}
