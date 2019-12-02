package com.Solver.Solver.Adepter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Solver.Solver.ModelClass.Factories;
import com.Solver.Solver.ModelClass.JobC;
import com.Solver.Solver.ModelClass.Schedule;
import com.Solver.Solver.ModelClass.SignUp;
import com.Solver.Solver.ModelClass.User;
import com.Solver.Solver.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

import java.util.List;
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder> {

    Context context;
    List<Schedule> schedulesList;
    int slNum=1;

    private FirebaseUser currentUser;
     DatabaseReference databaseReference;
    FirebaseUser getCurrentUser;
    StorageReference storageReference;
    String userEmail;
    String factoryName;
    String userName;


    public ScheduleAdapter(Context context, List<Schedule> schedulesList) {
        this.context = context;
        this.schedulesList = schedulesList;
    }

    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =LayoutInflater.from(context).inflate(R.layout.schedule_row, parent, false);
        ScheduleHolder scheduleHolder=new ScheduleHolder(view);
        return scheduleHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ScheduleHolder holder, final int position) {

        final Schedule schedule=schedulesList.get(position);

        holder.dateTv.setText(schedule.getDate()+" Work Plan.");
        try {

            if(!(schedulesList.get(position).getEmployeeName().equals(""))){
                holder.userNameTV.setText(schedule.getEmployeeName());
            }

        }catch (Exception e){

        }

        //schedulesList.get(position).getEmp_id()

        try {

            if(!(schedulesList.get(position).getEmp_id().equals(""))){
                DatabaseReference empName=FirebaseDatabase.getInstance().getReference("User");
                Query userInfoRef=empName.orderByChild("userId").equalTo(schedulesList.get(position).getEmp_id());
                userInfoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data:dataSnapshot.getChildren()){
                            SignUp user=data.getValue(SignUp.class);
                            userName=user.getName();

                        }

                        holder.userNameTV.setText(userName);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }catch (Exception e){

        }

        String comId=String.valueOf(schedulesList.get(position).getFactoryId());
        DatabaseReference companyREf=FirebaseDatabase.getInstance().getReference("factories");

        companyREf.child(comId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Factories factories=data.getValue(Factories.class);
                    factoryName=factories.getCompany_name();

                }
                try {
                    if(!(schedulesList.get(position).getCompanyName().equals(""))){

                        holder.companyNameTV.setText(schedulesList.get(position).getCompanyName());

                    }else {
                        holder.companyNameTV.setText(factoryName);
                    }
                }catch (Exception e){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /*for (int i=0;i<=schedule.getList().size();i++){

        holder.jobInfoTv.append(schedule.getList().get(i).getJobTitle()+"\nCategory:"+schedule.getList().get(i).getCategory()+"\nDes: "+schedule.getList().get(i).getJobDes()+"\n\n");

        }*/

        try {

            StringBuffer job=new StringBuffer();
            String jobCategory="";
            for (JobC jobC:schedulesList.get(position).getList()){
                if(jobC.getCategory().equals("Chose category")){
                    jobCategory="";
                }else {
                    jobCategory=jobC.getCategory();
                }

                job.append("Job Name: "+jobC.getJobTitle()+"("+jobCategory+").\nDes: "+jobC.getJobDes()+" .\n\n");

            }

            holder.jobInfoTv.setText(job);

        }catch (NullPointerException e){

        }

       /* try {

            for (int i=0;i<=schedulesList.get(position).getList().size();i++){

                holder.jobInfoTv.append("Job Name: "+schedule.getList().get(i).getJobTitle()+" .\nCategory:"+schedule.getList().get(i).getCategory()+" .\nDes: "+schedule.getList().get(i).getJobDes()+" .\n\n");

            }
        }catch (Exception e){

        }
*/
        if(!(userEmail.equals("solver.apps.bd@gmail.com"))){
            holder.row_menuTv.setText(null);
        }else {

            holder.row_menuTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu=new PopupMenu(context,view);
                    MenuInflater inflater=popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.delete_row,popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.deleteRowId:
                                    databaseReference= FirebaseDatabase.getInstance().getReference().child("Schedule").child("All_ScheduleTbl");
                                    String key=schedule.getScheduleId().toString();
                                    databaseReference.child(schedule.getScheduleId()).removeValue();
                                    Toast.makeText(context,"Delete Successful",Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            return false;
                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return schedulesList.size();
    }

    public class ScheduleHolder extends RecyclerView.ViewHolder{

        TextView dateTv;
        TextView userNameTV;
        TextView companyNameTV;
        TextView jobInfoTv;
        TextView row_menuTv;

        public ScheduleHolder(@NonNull View itemView) {
            super(itemView);

            dateTv=itemView.findViewById(R.id.scheduleDateId);
            userNameTV=itemView.findViewById(R.id.nameUSDId);
            companyNameTV=itemView.findViewById(R.id.comNameSdId);
            jobInfoTv=itemView.findViewById(R.id.jobSdId);
            row_menuTv=itemView.findViewById(R.id.row_menuSdId);

            try {
                currentUser= FirebaseAuth.getInstance().getCurrentUser();

                userEmail=currentUser.getEmail();
                getCurrentUser=FirebaseAuth.getInstance().getCurrentUser();

            }catch (Exception e){
                Toast.makeText(context,"Exception: "+e,Toast.LENGTH_SHORT).show();
            }
        }
    }
}