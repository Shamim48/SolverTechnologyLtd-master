package com.Solver.Solver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import com.Solver.Solver.Fragment.ChatFm;
import com.Solver.Solver.Fragment.ClientFm;
import com.Solver.Solver.Fragment.EmployeeFm;
import com.Solver.Solver.Fragment.GroupChatFm;
import com.Solver.Solver.ModelClass.Client;
import com.Solver.Solver.ModelClass.SignUp;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Home";
    MenuItem createUserMenuItem;
    DatabaseReference rootRef;
    DatabaseReference currentUserRef;
    DatabaseReference clientDatabaseRef;
    FirebaseUser currentUser;
    String currentUerId;
    FirebaseAuth auth;
    ArrayList<SignUp> userInfo=new ArrayList<>();
    String email;
     public static String value;
   TabLayout tabLayout;
   ViewPager viewPager;
   SignUp user;

    //Client Details

    //Client data
    private EditText companyNameEt;
    private EditText consultantPEt;
    private EditText addressCp;
    private EditText clientPhone;
    private EditText clientEmail;
    private EditText clientPasswordEt;
    private EditText clientConfirmPasswordEt;
     TextView nav_use;
     CircleImageView userImage;
    // EditText by Create new Group Custom Alert Dialog
    private  EditText createGroupEt;
    // view by Client Custom Alert Dialog
    private View viewClientForm;
    // view by Create new Group Custom Alert Dialog
    private  View createGroupView;

    private String companyName;
    private String companyAddress;
    private String consultant;
    private String companyPhone;
    private String  clientEmailA;
    private String  clientPassword;
    private String  clientConfirmPassword;

    private Button clientRegistrationBtn;
    private Button clientCancelBtn;
    // Button by Create new Group Custom Alert Dialog
    private Button createGroupBtn;
    private Button cancelBtn;
    Toolbar toolbar;

    String userId;
    String imageUrl;
    String name;
    String department;
    String designation;
    String uEmail;
    String phone;
    String password;
    String confirmPassword;
    String userType;
    String userEmail;
    String currentUsername;
    String currentUserImageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

         toolbar = findViewById(R.id.toolbar);
        createUserMenuItem=findViewById(R.id.createUserId);
        tabLayout=findViewById(R.id.tabLayoutHmId);
        viewPager=findViewById(R.id.viewPagerHmId);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Home");


 tabLayout.addTab(tabLayout.newTab().setText("Employee"));
tabLayout.addTab(tabLayout.newTab().setText("Client"));
tabLayout.addTab(tabLayout.newTab().setText("Chat"));
tabLayout.addTab(tabLayout.newTab().setText("Groups"));


//tabLayout.setBackgroundResource(R.color.white);
tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
//tabLayout.setTabTextColors(getResources().getColor(R.color.white_gray,Color.WHITE));
        tabLayout.setTabTextColors(R.color.white_gray,R.color.white);
        tabLayout.setTabTextColors(R.color.white_gray,R.color.white);
        rootRef= FirebaseDatabase.getInstance().getReference();
        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        clientDatabaseRef=FirebaseDatabase.getInstance().getReference().child("Client");
        auth=FirebaseAuth.getInstance();
        currentUerId=auth.getCurrentUser().getUid();
        currentUserRef=FirebaseDatabase.getInstance().getReference().child("User").child(currentUerId);

         email=currentUser.getEmail();

         FragmentAdepter adepter=new FragmentAdepter(getSupportFragmentManager());
         viewPager.setAdapter(adepter);
         viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

         tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
             @Override
             public void onTabSelected(TabLayout.Tab tab) {
                 viewPager.setCurrentItem(tab.getPosition());

             }

             @Override
             public void onTabUnselected(TabLayout.Tab tab) {

             }

             @Override
             public void onTabReselected(TabLayout.Tab tab) {

             }
         });

        // Read from the database


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("userType");
            //The key argument here must match that used in the other activity
        }
       /* FloatingActionButton fab = findViewById(R.id.fab);
       // TextView textView=findViewById(R.id.textViewId);
       // textView.setText(value);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/





        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        if((!(email.equals("solver.apps.bd@gmail.com")))){
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.createUserId).setVisible(false);
            nav_Menu.findItem(R.id.createGroupId).setVisible(false);
            nav_Menu.findItem(R.id.trackLocationMenuItmId).setVisible(false);
            nav_Menu.findItem(R.id.deleteMessageMenuItmId).setVisible(false);
            nav_Menu.findItem(R.id.editedMessageMenuItmId).setVisible(false);
            nav_Menu.findItem(R.id.createClientId).setVisible(false);
            nav_Menu.findItem(R.id.addDataMenuItmId).setVisible(false);

        }


      // View hView =  navigationView.inflateHeaderView(R.layout.nav_header_home);
        View hView =  navigationView.getHeaderView(0);
        nav_use = (TextView)hView.findViewById(R.id.userNameNHId);
         userImage=(CircleImageView) hView.findViewById(R.id.imageViewNHId);

        currentUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    SignUp signUp=dataSnapshot.getValue(SignUp.class);
                    currentUsername=signUp.getName();
                    userEmail=signUp.getEmail();
                    currentUserImageUrl=signUp.getImageUrl();

                    nav_use.setText(currentUsername);
                    Glide.with(Home.this).load(currentUserImageUrl).placeholder(R.drawable.ic_account_circle_black_24dp).into(userImage);


                }else {
                    toast("Data Not Found");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        nav_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast(currentUsername);
                nav_use.setText(currentUsername);
                Glide.with(Home.this).load(currentUserImageUrl).placeholder(R.drawable.ic_account_circle_black_24dp).into(userImage);

            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public boolean isActivityTransitionRunning() {


        return super.isActivityTransitionRunning();
    }



    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            toolbar.setTitle("No Internet connection!");
            Toast.makeText(getApplicationContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {

            nav_use.setText(currentUsername);
            Glide.with(Home.this).load(currentUserImageUrl).placeholder(R.drawable.ic_account_circle_black_24dp).into(userImage);
        }catch (Exception e){

        }

        if (isOnline())
        {
            toolbar.setTitle("Home");
        }
        else
        {
            // Home.this.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            try {
                toolbar.setTitle("No Internet Connection.!");
            }
            catch(Exception e)
            {
                //Log.d(Constants.TAG, "Show Dialog: "+e.getMessage());
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Home.this.finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.signOutId) {
            auth.signOut();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

         if (id == R.id.editAccountId) {

            editUserInfo();
        } else if (id == R.id.displayScheduleId) {

            Intent intent=new Intent(getApplicationContext(),Schedule_Display.class);
            startActivity(intent);

        }else if(id==R.id.dailyReportId){
             String action;
             Intent intent=new Intent(getApplicationContext(),Daily_Report.class);
             startActivity(intent);
         }

         else if (id == R.id.write_ScheduleId) {

            Intent intent=new Intent(Home.this,Write_Schedule.class);
            startActivity(intent);
        }else if (id == R.id.requestQuotationId) {

            Intent intent=new Intent(Home.this,Quotation_Request.class);
            startActivity(intent);
        } else if (id == R.id.trackLocationMenuItmId) {

             Intent intent=new Intent(getApplicationContext(),ViewImage.class);
             startActivity(intent);

        }else if (id == R.id.addDataMenuItmId) {

             Intent intent=new Intent(getApplicationContext(),AddData.class);
             startActivity(intent);

        }  else if (id == R.id.createClientId) {

            AlertDialog.Builder alert=new AlertDialog.Builder(Home.this);
            viewClientForm=getLayoutInflater().inflate(R.layout.client_dialog_form,null);
            findIdByClientDialogViw();
            alert.setView(viewClientForm);
            final AlertDialog alertDialogByClient=alert.create();
            alertDialogByClient.setCanceledOnTouchOutside(false);
            alertDialogByClient.show();
            clientRegistrationBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    companyName=companyNameEt.getText().toString().trim();
                    //companyNameEt.setText(bloodGroups);
                    companyAddress=addressCp.getText().toString().trim();
                    consultant=consultantPEt.getText().toString().trim();
                    companyPhone=clientPhone.getText().toString().trim();
                    clientEmailA=clientEmail.getText().toString().trim();


                    if(companyName.isEmpty()){
                        companyNameEt.setError("Please Enter Client/Company Name..!");
                        return;
                    }

                    if(consultant.isEmpty()){
                        consultantPEt.setError("Please Enter Consultant person..!");
                        return;
                    }

                    if(companyAddress.isEmpty()){
                        addressCp.setError("Please Enter Company Address..!");
                        return;
                    }

                    if(companyPhone.isEmpty()){
                        clientPhone.setError("Please Enter Client phone number..!");
                        return;
                    }
                    if(clientEmailA.isEmpty()){
                        clientEmail.setError("Please Enter client email..!");
                        return;
                    }

                    String key=clientDatabaseRef.push().getKey();
                          Client client=new Client(key,companyName,consultant,companyAddress,companyPhone,clientEmailA);
                            clientDatabaseRef.child(key).setValue(client).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Client Registration Successful",Toast.LENGTH_SHORT).show();
                                    }else {
                                        toast("Registration unsuccessful");
                                    }
                                }
                            });


                    alertDialogByClient.cancel();
                }
            });
            clientCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialogByClient.dismiss();
                }
            });


        } else if (id == R.id.deleteMessageMenuItmId) {

        }else if (id == R.id.createUserId) {
            Intent i=new Intent(getApplicationContext(),Sign_Up.class);
            startActivity(i);
        }else if (id == R.id.showQuotationId) {
            Intent i=new Intent(getApplicationContext(),Quotation_Show.class);
            startActivity(i);
        }else if(id==R.id.createGroupId){

            requestNewGroup();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void requestNewGroup() {

        /*
        final AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext(),R.style.Theme_AppCompat_Dialog_Alert);
        builder.setTitle("Create New Group.");
        final  EditText groupNameEt=new EditText(getApplicationContext());
        groupNameEt.setHint("Enter group name");
        builder.setView(groupNameEt);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String groupName=groupNameEt.getText().toString();
                // call Create Group method
                if(TextUtils.isEmpty(groupName)){
                    toast("please enter group name");
                    groupNameEt.setError("Please enter group name..!");
                    return;
                }else {
                    createNewGroup(groupName);
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();*/


        //Custom Alert Dialog for create new Group....

        AlertDialog.Builder builder=new AlertDialog.Builder(Home.this);
        createGroupView=getLayoutInflater().inflate(R.layout.createggroup_lot,null);
        //initialize id in this method
        // and call this initialize id method
        findIdByCreateGroupDialog();
        builder.setView(createGroupView);
        final AlertDialog alertDialog=builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        createGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupName=createGroupEt.getText().toString();
                if(TextUtils.isEmpty(groupName)){
                    toast("please enter group name");
                    createGroupEt.setError("Please enter group name..!");
                    return;
                }else {
                    createNewGroup(groupName);
                    alertDialog.cancel();
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 alertDialog.dismiss();
            }
        });



    }
    //initialize id in this method
    private void findIdByCreateGroupDialog() {
        createGroupEt=createGroupView.findViewById(R.id.createGroupEtId);
        createGroupBtn=createGroupView.findViewById(R.id.createGroupBtnId);
        cancelBtn=createGroupView.findViewById(R.id.createGroupCancelBtnId);

    }

    // This is Create Group method
    private void createNewGroup(String groupName) {

        rootRef.child("Group").child(groupName).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    toast("Group is Created Successfully..");
                }
            }
        });

        //subscribe topic for group Notification
        FirebaseMessaging.getInstance().subscribeToTopic(groupName)
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

    private void findIdByClientDialogViw() {

        companyNameEt=viewClientForm.findViewById(R.id.companyNameEtId);
        consultantPEt=viewClientForm.findViewById(R.id.consultantPNId);
        addressCp=viewClientForm.findViewById(R.id.addressCompanyId);
        clientPhone=viewClientForm.findViewById(R.id.phoneCompanyId);
        clientEmail=viewClientForm.findViewById(R.id.emailCompanyId);
        clientRegistrationBtn=viewClientForm.findViewById(R.id.clientRegisterBtnId);
        clientCancelBtn=viewClientForm.findViewById(R.id.clientCancelBtnId);

    }

    public void toast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
    public  class  FragmentAdepter extends FragmentPagerAdapter{

        public FragmentAdepter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch ((position)){
                case 0:
                    return new EmployeeFm();
                case 1:
                    return new ClientFm();
                case 2:
                    return new ChatFm();
                case 3:
                    return new GroupChatFm();

            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    public void call(String num){
        Intent callIntent=new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+num));
        startActivity(callIntent);
    }

    public void editUserInfo(){

        FirebaseUser currentUser;
        final DatabaseReference databaseRef;
        FirebaseUser getCurrentUser;
        StorageReference storageReference;

            currentUser= FirebaseAuth.getInstance().getCurrentUser();

            userEmail=currentUser.getEmail();
            name=currentUser.getDisplayName();
           // imageUrl=currentUser.getPhotoUrl().toString();

            databaseRef= FirebaseDatabase.getInstance().getReference().child("User").child(currentUerId);
            getCurrentUser=FirebaseAuth.getInstance().getCurrentUser();
            storageReference= FirebaseStorage.getInstance().getReference("User").child(currentUerId);

            databaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        SignUp signUp=dataSnapshot.getValue(SignUp.class);
                        name=signUp.getName();
                        userEmail=signUp.getEmail();
                        imageUrl=signUp.getImageUrl();
                        department=signUp.getDepartment();
                        designation=signUp.getDesignation();
                        phone=signUp.getPhone();
                        password=signUp.getPassword();
                        confirmPassword=signUp.getPassword();
                        userType=signUp.getUserType();
                        userId=signUp.getUserId();
                        editUserIntent();
                    }else {
                        toast("Data Not Found");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            /*
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot data, @Nullable String s) {
                   // SignUp signUp=data.getValue(SignUp.class);
                    //imageUrl=data.getImageUrl();
                   // name=signUp.getName();
                    userId=data.child("userId").getValue(String.class);
                     imageUrl=data.child("imageUri").getValue(String.class);
                      name=data.child("name").getValue(String.class);
                    department=data.child("department").getValue(String.class);
                    designation=data.child("designation").getValue(String.class);
                    phone=data.child("phone").getValue(String.class);
                    uEmail=data.child("email").getValue(String.class);
                    password=data.child("password").getValue(String.class);
                    confirmPassword=data.child("password").getValue(String.class);
                    userType=data.child("userType").getValue(String.class);
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
            });*/
/*
       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot data) {

               SignUp signUp=data.getValue(SignUp.class);
               // SignUp userInfo=data.getValue(SignUp.class);
               imageUrl=signUp.getImageUrl();
               name=signUp.getName();
               userId=data.child("userId").getValue(String.class);
               // imageUrl=data.child("imageUri").getValue(String.class);
               //  name=data.child("name").getValue(String.class);
               department=data.child("department").getValue(String.class);
               designation=data.child("designation").getValue(String.class);
               phone=data.child("phone").getValue(String.class);
               uEmail=data.child("email").getValue(String.class);
               password=data.child("password").getValue(String.class);
               confirmPassword=data.child("password").getValue(String.class);
               userType=data.child("userType").getValue(String.class);


           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });*/


      //  editUserIntent();

    }

   public void editUserIntent() {
        Intent intent=new Intent(getApplicationContext(), Sign_Up.class);
        intent.putExtra("userId",userId);
        intent.putExtra("image",imageUrl);
        intent.putExtra("name",name);
        intent.putExtra("department",department);
        intent.putExtra("designation",designation);
        intent.putExtra("email",userEmail);
        intent.putExtra("phone",phone);
        intent.putExtra("password",password);
        intent.putExtra("confirmPass",confirmPassword);
        intent.putExtra("userType",userType);
        intent.putExtra("update","update");
        startActivity(intent);
    }
}
