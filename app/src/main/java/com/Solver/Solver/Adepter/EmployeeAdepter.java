package com.Solver.Solver.Adepter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Solver.Solver.ModelClass.SignUp;
import com.Solver.Solver.R;
import com.Solver.Solver.ShowUserDetails;
import com.Solver.Solver.Sign_Up;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class EmployeeAdepter extends RecyclerView.Adapter<EmployeeAdepter.EmployeeHolder> {

    private android.content.Context context;
   private List<SignUp> employeeList;
    CustomItemClickListener listener;
   private FirebaseUser currentUser;
   private DatabaseReference databaseReference;
   FirebaseUser getCurrentUser;
   StorageReference storageReference;
    String userEmail;
    private UserListener userListener;


    // Current User Information / user info variable
    private String userId;
    private String imageUrl;
    private String name;
    private String department;
    private String designation;
    private String email;
    private String phone;
    private String password;
    private String confirmPassword;
    private String userType;
     SignUp signUp;


    public EmployeeAdepter(Context context, List<SignUp> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
       // userListener= (UserListener) context;
    }

    @NonNull
    @Override
    public EmployeeHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
       View view=LayoutInflater.from(context).inflate(R.layout.usersample_layout,parent,false);
       final EmployeeHolder employeeHolder=new EmployeeHolder(view);


        return employeeHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final EmployeeHolder holder, final int position) {

         signUp=employeeList.get(position);

        // get current User data from Employee List
        userId=employeeList.get(position).getUserId();
        imageUrl=employeeList.get(position).getImageUrl();
        name=employeeList.get(position).getName();
        department=employeeList.get(position).getDepartment();
        designation=employeeList.get(position).getDesignation();
        phone=employeeList.get(position).getPhone();
        email=employeeList.get(position).getEmail();
        password=employeeList.get(position).getPassword();
        confirmPassword=employeeList.get(position).getConfirmPassword();
        userType=employeeList.get(position).getUserType();

        holder.nameTv.setText(signUp.getName());
        holder.designation.setText(signUp.getDesignation());
        Glide.with(holder.employeeImage).load(signUp.getImageUrl())
                .placeholder(R.drawable.ic_insert_photo_black_24dp)
                .into(holder.employeeImage);



        if(!(userEmail.equals("solver.apps.bd@gmail.com"))){
            holder.menuTv.setText(null);
        }else {

            holder.menuTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu=new PopupMenu(context,view);
                    MenuInflater inflater=popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.user_row_popup,popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.editMenuItmId:

                               // Toast.makeText(context,"Coming son",Toast.LENGTH_SHORT).show();
                                editUserInfo();
                                break;
                            case R.id.deleteMenuItmId:

                                final AlertDialog.Builder builder=new AlertDialog.Builder(context);
                                builder.setTitle("Are You sure..?");
                                builder.setMessage("You Want to delete..?");

                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        deleteUser();
                                        dialogInterface.cancel();
                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });

                                builder.show();
                                break;
                        }
                            return false;
                        }
                    });
                }
            });
        }

        holder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri="tel:"+employeeList.get(position).getPhone();
                Intent callIntent=new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(uri));
                context.startActivity(callIntent);
            }
        });
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ShowUserDetails.class);
                intent.putExtra("image",employeeList.get(position).getImageUrl());
                intent.putExtra("name",employeeList.get(position).getName());
                intent.putExtra("department",employeeList.get(position).getDepartment());
                intent.putExtra("designation",employeeList.get(position).getDesignation());
                intent.putExtra("email",employeeList.get(position).getEmail());
                intent.putExtra("phone",employeeList.get(position).getPhone());
                context.startActivity(intent);
            }
        });
    }

    private void editUserInfo() {
        Intent intent=new Intent(context, Sign_Up.class);
        intent.putExtra("userId",userId);
        intent.putExtra("image",imageUrl);
        intent.putExtra("name",name);
        intent.putExtra("department",department);
        intent.putExtra("designation",designation);
        intent.putExtra("email",email);
        intent.putExtra("phone",phone);
        intent.putExtra("password",password);
        intent.putExtra("confirmPass",confirmPassword);
        intent.putExtra("userType",userType);
        intent.putExtra("update","update");
        context.startActivity(intent);
    }

    public void deleteUser(){
      /*  try {*/



        getCurrentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context,"User Delete Successful",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Delete user in Exception: "+e,Toast.LENGTH_SHORT).show();
            }
        });
        databaseReference.child(signUp.getUserId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context,"Delete user information Successful: ",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Delete user information in Exception: "+e,Toast.LENGTH_SHORT).show();

            }
        });
        storageReference.child(imageUrl).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context,"Image Delete Successful",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Delete Fail in Exception: "+e,Toast.LENGTH_SHORT).show();

            }
        });
       /* }catch (Exception e){
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }*/
    }
    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    class EmployeeHolder extends RecyclerView.ViewHolder{

        private ImageView employeeImage;
        private TextView nameTv;
        private TextView designation;
        private ImageButton callBtn;
        LinearLayout parentLayout;
        TextView menuTv;
       public EmployeeHolder(@NonNull View itemView) {
           super(itemView);

           employeeImage=itemView.findViewById(R.id.userRltImageId);
           nameTv=itemView.findViewById(R.id.nameTvId);
           designation=itemView.findViewById(R.id.designationTvId);
           callBtn=(ImageButton)itemView.findViewById(R.id.callBtnTbId);
           parentLayout=(LinearLayout) itemView.findViewById(R.id.parentLayoutId);
           menuTv=itemView.findViewById(R.id.row_menu);
           try {
               currentUser= FirebaseAuth.getInstance().getCurrentUser();
               userEmail=currentUser.getEmail();
               databaseReference= FirebaseDatabase.getInstance().getReference().child("User");
               getCurrentUser=FirebaseAuth.getInstance().getCurrentUser();
               storageReference= FirebaseStorage.getInstance().getReference().child("User");
           }catch (Exception e){
               Toast.makeText(context,"Exception: "+e,Toast.LENGTH_SHORT).show();
           }
       }
   }

  public interface UserListener {
        public void onUserDelete(String id);
        public void onUserEdit(String id);
   }
}
