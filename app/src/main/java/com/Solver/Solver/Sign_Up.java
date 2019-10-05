package com.Solver.Solver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Solver.Solver.ModelClass.SignUp;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Sign_Up extends AppCompatActivity {

    private static final String TAG = Sign_Up.class.getSimpleName();
    private Button registerBtn;
    private Button clearBtn;
    private Button updateBtn;
    private ProgressBar progressBar;
   private CircleImageView profileIV;
   private ImageView imageViewProfile;
   private EditText adminPasswordEt;
    private EditText usernameEt;
    private EditText addressEt;
    private EditText dateOfBirth;
    private RadioGroup genderGroup;
    private AutoCompleteTextView designationAt;
    private AutoCompleteTextView nationality;
    private EditText nid;
    private Spinner selectDepSp;
    private Spinner selectUserType;
    private EditText emailEt;
    private EditText phoneEt;
    private EditText passwordEt;
    private EditText ConfirmPasswordEt;
    private String selecteddateOfBirth;
    private String genderdf="Male";
    private ArrayList<String> bloodGroupList=new ArrayList<>();
    private ArrayList<String> userTypeList=new ArrayList<>();
    private TextView createAccountTv;
    private CheckBox showPassCb;
    private String adminPassword="";
    //Intent for receive Update Data
    Intent updateIntent;
    String updateKey="uploadKey";
    String userId;


    public static final int IMAGE_REQUEST=1;
    public static final int IMAGE_REQUEST2=2;

   // private Uri imageUri;
    private Uri cropImageUri;
    Bitmap bitmap;
    private String currentImage;
    private String[] country;
    private String[] religionList;
    private ProgressDialog loadingBar;

    //getData from view
    String userName;
    String designation;
    String selectDep;
    String userType;
    String email;
    String phone;
    String password;
    String confirmPassword;


    //Fire Base

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    FirebaseStorage firebaseStorage;
    DatabaseReference rootRef;
    StorageReference storageReference;
    DatabaseReference updateRef;

  //  ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        findId();
        auth=FirebaseAuth.getInstance();
         rootRef=FirebaseDatabase.getInstance().getReference();
         storageReference=FirebaseStorage.getInstance().getReference();

         loadingBar=new ProgressDialog(this);
         progressBar.setVisibility(View.GONE);

         // get Update data by Intent
         updateIntent=getIntent();
         updateKey=updateIntent.getStringExtra("update");
         //Check
try {
    if(updateKey.equals("update")){

        registerBtn.setVisibility(View.GONE);
        updateBtn.setVisibility(View.VISIBLE);
        updateUser();
    }

}catch (NullPointerException e){
    Toast.makeText(getApplicationContext(),"Exception: "+e,Toast.LENGTH_LONG).show();
}

        country=getResources().getStringArray(R.array.countryList);
        religionList=getResources().getStringArray(R.array.religionList);
       /* ArrayAdapter<String> countryAdepter=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,country);
        nationality.setAdapter(countryAdepter);*/
     final ArrayAdapter<String> religionAdepter=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,religionList);
        designationAt.setAdapter(religionAdepter);

        profileIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectImage();

            }
        });

      /*  imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent();
                intent.setType("image/*");
               intent.setAction(Intent.ACTION_GET_CONTENT);
               startActivityForResult(intent,IMAGE_REQUEST2);

            }
        });*/

        bloodGroupList.add("Chose Department ..");
        bloodGroupList.add("Sales and Marketing");
        bloodGroupList.add("Technical Support");
        bloodGroupList.add("Accounting");
        bloodGroupList.add("Administration");
        bloodGroupList.add("IT");
        userTypeList.add("Chose User Type..");
        userTypeList.add("Admin");
        userTypeList.add("Employee");
        userTypeList.add("Client");

        ArrayAdapter<String> bloodAdapter=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,bloodGroupList);
        selectDepSp.setAdapter(bloodAdapter);

         ArrayAdapter<String> userTypeAdapter=new ArrayAdapter<>(this,R.layout.spennersamplelayout,R.id.showTestSpinnerId,userTypeList);
        selectUserType.setAdapter(userTypeAdapter);

       /* genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb=findViewById(i);
                genderdf=rb.getText().toString();

                Toast.makeText(getApplicationContext(),genderdf,Toast.LENGTH_SHORT).show();
            }
        });
*/

/*
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog=new DatePickerDialog(this,listener,year,month,dayOfMonth);
                datePickerDialog.show();
            }
        });*/

showPassCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b){
            passwordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ConfirmPasswordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else {
            passwordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ConfirmPasswordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
});

registerBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        final ProgressDialog progressDialog=new ProgressDialog(Sign_Up.this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Uploading Data..");
        progressDialog.show();
       // registerBtn.setBackgroundResource(R.drawable.layoutbg);
         userName=usernameEt.getText().toString();
       // String address=addressEt.getText().toString();
         designation=designationAt.getText().toString();
       // String nationalitys=nationality.getText().toString();
       // String nId=nid.getText().toString();
       /// String dateOfBirths=dateOfBirth.getText().toString();
       // String gender=genderdf;
         selectDep= selectDepSp.getSelectedItem().toString();
         userType=selectUserType.getSelectedItem().toString();
         email=emailEt.getText().toString();
         phone=phoneEt.getText().toString();
         password=passwordEt.getText().toString();
         confirmPassword=ConfirmPasswordEt.getText().toString();

        if(userName.isEmpty()){
            usernameEt.setError("Please enter user name.!");
            return;
        }

        /*
        if(address.isEmpty()){
            addressEt.setError("Please enter current address.!");
        }
        if(religions.isEmpty()){
            religion.setError("Please enter religion.!");
        }
        if(nationalitys.isEmpty()){
            nationality.setError("Please enter nationality.!");
        }
        if(nId.isEmpty()){
            nid.setError("Please enter user name.!");
        }
        if(dateOfBirths.isEmpty()){
            dateOfBirth.setError("Please enter date of birth.!");
        }*/

        if(email.isEmpty()){
            emailEt.setError("Please enter your email..!");
            return;
        }
        if(phone.isEmpty()){
            phoneEt.setError("Please enter your phone number.!");
            return;
        }

         if(password.isEmpty()){
            passwordEt.setError("Please enter your password.!");
            return;
        }

        if(confirmPassword.isEmpty()){
            ConfirmPasswordEt.setError("Please enter your confirm password.!");
            return;
        }
       /* if(password!=confirmPassword){
            ConfirmPasswordEt.setError("Password and Confirm Password are not match..");
            return;
        }*/
/*

        if(userType.equals("Client")){

            AlertDialog.Builder alert=new AlertDialog.Builder(Sign_Up.this);
             viewClientForm=getLayoutInflater().inflate(R.layout.client_dialog_form,null);
            findIdByClientDialogViw();
            alert.setView(viewClientForm);
            final AlertDialog alertDialogByClient=alert.create();
            alertDialogByClient.setCanceledOnTouchOutside(false);
            clientRegistrationBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                     companyName=companyNameEt.getText().toString().trim();
                    // companyNameEt.setText(bloodGroups);
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
                    toast("Registration Successful");
                    alertDialogByClient.cancel();
                }
            });
            clientCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialogByClient.dismiss();
                }
            });
            alertDialogByClient.show();
            */
/*Intent i=new Intent(getApplicationContext(),Client_reg.class);
            startActivity(i);*//*

        }else if (userType.equals("Admin")){
            final AlertDialog.Builder alert=new AlertDialog.Builder(Sign_Up.this);
            View viewDialog=getLayoutInflater().inflate(R.layout.custom_dialog,null);
             adminPasswordEt=viewDialog.findViewById(R.id.adminPasswordEtId);
          // adminPasswordEt.setText(userType);
            Button adminCancelBtn=viewDialog.findViewById(R.id.adminCancelBtnId);
            Button adminOkBtn=viewDialog.findViewById(R.id.adminOkBtnId);

            alert.setView(viewDialog);
            final AlertDialog alertDialog=alert.create();
            alertDialog.setCanceledOnTouchOutside(false);
            adminCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            adminOkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adminPassword=adminPasswordEt.getText().toString();
                    if(adminPassword.equals("1234")){
                        toast("Registration Successful");
                        alertDialog.cancel();
                    }else {
                        toast("Pleas Enter Correct password" );
                    }

                }
            });
            alertDialog.show();


        }
*/

        //User account Method
      /*  progressDialog.setTitle("Creating new account");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
        progressBar.setVisibility(View.VISIBLE);
*/
        createUserAccount();

       // progressDialog.dismiss();

   //  creatAccount(email,password,confirmPassword);

progressDialog.dismiss();

    }
});


clearBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        usernameEt.setText(null);
        designationAt.setText(null);
        emailEt.setText(null);
        phoneEt.setText(null);
        emailEt.setText(null);
        passwordEt.setText(null);
        ConfirmPasswordEt.setText(null);
        profileIV.setImageURI(null);
       profileIV.invalidate();
       profileIV.setImageBitmap(null);
       showPassCb.setChecked(false);
       cropImageUri=null;

    }
});

        createAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

    }

    private void selectImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    public void creatAccount(String email,String password,String confirmPassword){
        if(!(password.equals(confirmPassword))){
            ConfirmPasswordEt.setError("Password and Confirm Password are not match..");

            return;
        }
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void findId(){
        profileIV=findViewById(R.id.profile_imageId);
       // dateOfBirth=findViewById(R.id.dateOfBirthId);
      //  genderGroup=findViewById(R.id.genderGroupId);
        selectDepSp =findViewById(R.id.selectDepartmentId);
        selectUserType=findViewById(R.id.selectUserTypeId);
        usernameEt=findViewById(R.id.UsernameSignUpId);
        //addressEt=findViewById(R.id.addressSignUpEtId);
        designationAt=findViewById(R.id.designationATId);
       // nationality=findViewById(R.id.nationalityAtId);
       // nid=findViewById(R.id.nidId);
        emailEt=findViewById(R.id.EmailSignUpId);
        phoneEt=findViewById(R.id.phoneId);
        passwordEt=findViewById(R.id.PasswordSignUpId);
        ConfirmPasswordEt=findViewById(R.id.confirmPasswordSignUpId);
        registerBtn=findViewById(R.id.registerBtnId);
        createAccountTv=findViewById(R.id.createAccountTvId);
        progressBar=findViewById(R.id.progressBarId);
        clearBtn=findViewById(R.id.ClearBtnId);
        updateBtn=findViewById(R.id.updateBtnId);
        showPassCb=findViewById(R.id.showPasswordCBId);
      //  imageViewProfile=findViewById(R.id.imageViewId);
      //  progressDialog=new ProgressDialog(Sign_Up.this);


    }
   public void inputData(){



   }

    public void dateOfbirth( ) {
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog=new DatePickerDialog(this,listener,year,month,dayOfMonth);
        datePickerDialog.show();
    }
    public DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            Calendar calendar=Calendar.getInstance();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyy");

            calendar.set(year,month,dayOfMonth);

            selecteddateOfBirth=simpleDateFormat.format(calendar.getTime());
            dateOfBirth.setText(selecteddateOfBirth);

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null /*&& data.getData() != null*/) {

          Uri  imageUri = data.getData();

            // Picasso.get().load(imageUri).rotate(270).into(profileIV);
            //this is file compress method
            // setPic();

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(2, 2)
                    .setAutoZoomEnabled(true)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                loadingBar.setTitle("Set Profile Image");
                loadingBar.setMessage("Please wait, your profile image is updating...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                cropImageUri = result.getUri();
                Glide.with(profileIV).load(cropImageUri)
                        .placeholder(R.drawable.ic_touch_app_black_24dp)
                        .into(profileIV);
                loadingBar.dismiss();

            }

        }
    }

   public void toast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
   }


    public String getFileExtension(Uri imageUri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    public void createUserAccount(){

        registerBtn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        String uEmail=emailEt.getText().toString();
        String uPassword=passwordEt.getText().toString();
        auth.createUserWithEmailAndPassword(uEmail,uPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                  uploadData();
                   toast("Account Successful Created...");
                   toast("Please wait uploading your data...");
                  // FirebaseUser user = auth.getCurrentUser();
                 //  updateUI(user);
               }else {
                   toast("Exception :"+task.getException().getMessage());
                   Log.e(TAG,task.getException().getMessage());
               }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                toast("Registration failed..! and Exception: "+e);
            }
        });


    }

    private void uploadData() {


        final ProgressDialog progressDialog=new ProgressDialog(Sign_Up.this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Uploading Data..");
        progressDialog.show();


        final String currentUserId=auth.getCurrentUser().getUid();

        StorageReference ref=storageReference.child("User").child(currentUserId);

        ref.putFile(cropImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //  Uri downloadUrl = taskSnapshot.getDownloadUrl();


                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadUri=uriTask.getResult();

                     //  Upload upload=new Upload(imageName,downloadUri.toString());

                       // String userId=databaseReference.push().getKey();
                        SignUp signUp=new SignUp(currentUserId,downloadUri.toString(),userName,selectDep,designation,email,phone,password,userType);

                        rootRef.child("User").child(currentUserId).setValue(signUp).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    toast("Data Upload Success");



                                            progressDialog.dismiss();
                                            registerBtn.setEnabled(true);
                                            progressBar.setVisibility(View.GONE);
                                            Intent intent=new Intent(getApplicationContext(),Home.class);
                                            startActivity(intent);
                                            finish();
                                        }

                            }
                        });



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });



    }


    public void updateUser(){


          userId=updateIntent.getStringExtra("userId");
         final String imageUrlUd=updateIntent.getStringExtra("image");
         String name=updateIntent.getStringExtra("name");
         String departmentUd=updateIntent.getStringExtra("department");
         final String designationUd=updateIntent.getStringExtra("designation");
         String emailUd=updateIntent.getStringExtra("email");
         String phoneUd=updateIntent.getStringExtra("phone");
         String passwordUd=updateIntent.getStringExtra("password");
        // String confirmPassword=updateIntent.getStringExtra("confirmPass");
         String userTypeUd=updateIntent.getStringExtra("userType");

         Glide.with(profileIV).load(imageUrlUd).into(profileIV);
         usernameEt.setText(name);
         if(departmentUd.equals("Chose Department ..")){
             selectDepSp.setSelection(0);
         }else if(departmentUd.equals("Sales and Marketing")){
             selectDepSp.setSelection(1);
         }else if(departmentUd.equals("Technical Support")){
             selectDepSp.setSelection(2);
         }else if(departmentUd.equals("Accounting")){
             selectDepSp.setSelection(3);
         }else if(departmentUd.equals("Administration")){
             selectDepSp.setSelection(4);
         }else if(departmentUd.equals("IT")){
             selectDepSp.setSelection(5);
         }
         designationAt.setText(designationUd);
         emailEt.setText(emailUd);
         phoneEt.setText(phoneUd);
         passwordEt.setText(passwordUd);
         ConfirmPasswordEt.setText(passwordUd);
        if(userTypeUd.equals("Chose User Type..")){
            selectUserType.setSelection(0);
        }else if(userTypeUd.equals("Admin")){
            selectUserType.setSelection(1);
        }else if(userTypeUd.equals("Employee")){
            selectUserType.setSelection(2);
        }else if(userTypeUd.equals("Client")){
            selectUserType.setSelection(3);
        }
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog=new ProgressDialog(Sign_Up.this);
                progressDialog.setTitle("Please Wait..");
                progressDialog.setMessage("Uploading Data..");
                progressDialog.show();
                userName=usernameEt.getText().toString();
                // String address=addressEt.getText().toString();
                designation=designationAt.getText().toString();
                // String nationalitys=nationality.getText().toString();
                // String nId=nid.getText().toString();
                /// String dateOfBirths=dateOfBirth.getText().toString();
                // String gender=genderdf;
                selectDep= selectDepSp.getSelectedItem().toString();
                userType=selectUserType.getSelectedItem().toString();
                email=emailEt.getText().toString();
                phone=phoneEt.getText().toString();
                password=passwordEt.getText().toString();
                confirmPassword=ConfirmPasswordEt.getText().toString();

                progressBar.setVisibility(View.VISIBLE);
                updateRef=FirebaseDatabase.getInstance().getReference("User").child(userId);
                StorageReference updateStorageRef=FirebaseStorage.getInstance().getReference("Solver");
                final String currentUserId=auth.getCurrentUser().getUid();
                StorageReference ref=storageReference.child("User").child(currentUserId);

                registerBtn.setActivated(false);
                registerBtn.setEnabled(false);

                if(cropImageUri!=null) {


                    ref.putFile(cropImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Get a URL to the uploaded content
                                    //  Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!uriTask.isSuccessful()) ;
                                    Uri downloadUri = uriTask.getResult();

                                    //  Upload upload=new Upload(imageName,downloadUri.toString());
                                    // String userId=databaseReference.push().getKey();

                                    SignUp signUp = new SignUp(currentUserId, downloadUri.toString(), userName, selectDep, designation, email, phone, password, userType);

                                    rootRef.child("User").child(currentUserId).setValue(signUp).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                FirebaseUser currentUser = auth.getCurrentUser();

                                                currentUser.updatePassword(password);
                                                currentUser.updateEmail(email);
                                                toast("Data update Successful");
                                                progressDialog.dismiss();
                                                progressBar.setVisibility(View.GONE);
                                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                                startActivity(intent);
                                                finish();

                                            } else {
                                                toast("Data update UnSuccessful");
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            toast("Exception: " + e.getMessage());
                                        }
                                    });

                                    registerBtn.setEnabled(true);
                                    registerBtn.setActivated(true);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    // ...

                                    Toast.makeText(getApplicationContext(), "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                }else {



                                    //  Upload upload=new Upload(imageName,downloadUri.toString());
                                    // String userId=databaseReference.push().getKey();

                                    SignUp signUp = new SignUp(currentUserId, imageUrlUd, userName, selectDep, designation, email, phone, password, userType);

                                    rootRef.child("User").child(currentUserId).setValue(signUp).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                FirebaseUser currentUser = auth.getCurrentUser();

                                                currentUser.updatePassword(password);
                                                currentUser.updateEmail(email);
                                                toast("Data update Successful");
                                                progressDialog.dismiss();
                                                progressBar.setVisibility(View.GONE);
                                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                                startActivity(intent);
                                                finish();

                                            } else {
                                                toast("Data update UnSuccessful");
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            toast("Exception: " + e.getMessage());
                                        }
                                    });
                                    registerBtn.setEnabled(true);
                                    registerBtn.setActivated(true);

                }
            }
        });
    }
    //this is file compress method
    private void setPic() {
        // Get the dimensions of the View
        int targetW = profileIV.getWidth();
        int targetH = profileIV.getHeight();
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(cropImageUri.toString(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
         bitmap = BitmapFactory.decodeFile(cropImageUri.toString(), bmOptions);
        profileIV.setImageBitmap(bitmap);

    }


 }
