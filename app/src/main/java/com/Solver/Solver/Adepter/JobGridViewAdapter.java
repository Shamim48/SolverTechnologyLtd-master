package com.Solver.Solver.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Solver.Solver.ModelClass.JobC;
import com.Solver.Solver.ModelClass.JobCategory;
import com.Solver.Solver.ModelClass.SignUp;
import com.Solver.Solver.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JobGridViewAdapter extends ArrayAdapter<JobC> {

    private ArrayList<JobC> jobList;
    Context context;
    String name,category;

    private JobGridViewAdapter.JobListener jobListener;

    public JobGridViewAdapter(Context context,  ArrayList<JobC> jobList) {
       super(context,0, jobList);
        this.jobList = jobList;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        JobC jobC=jobList.get(position);
        final TextView nameTv;
        final TextView jobDesTv;

        final LinearLayout selectedPdtLlt;

        ImageButton closeBtn;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.job_grid_sample_row, parent, false);

            nameTv=convertView.findViewById(R.id.userNameCVSRId);
            jobDesTv=convertView.findViewById(R.id.jobDesTvId);

            closeBtn=convertView.findViewById(R.id.closeBtnSrId);

            DatabaseReference jobRef= FirebaseDatabase.getInstance().getReference("Utility").child("Job");
            Query jobName=jobRef.orderByChild("jobId").equalTo(jobList.get(position).getJobId());
            jobName.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot data:dataSnapshot.getChildren()){
                        JobC job=data.getValue(JobC.class);
                        name =job.getJobTitle();
                    }

                    nameTv.setText(name+"("+""+")");

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

DatabaseReference jobCategoryRef= FirebaseDatabase.getInstance().getReference("Utility").child("Job_Category");
            Query jobCategoryNameQuery=jobCategoryRef.orderByChild("jobCategoryId").equalTo(jobList.get(position).getCategoryId());
            jobCategoryNameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot data:dataSnapshot.getChildren()){
                        JobCategory job=data.getValue(JobCategory.class);
                        category =job.getJobCategoryName();
                    }

                    nameTv.setText(name+"("+category+")");

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            jobDesTv.setText(jobList.get(position).getJobDes());
            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jobListener.removeGridJob(position);
                }
            });
        }

        return convertView;
    }

    public interface JobListener {

        void removeGridJob(int position);

    }

    public void setRemoveJob(JobGridViewAdapter.JobListener removeListener) {
        this.jobListener = removeListener;
    }
}
