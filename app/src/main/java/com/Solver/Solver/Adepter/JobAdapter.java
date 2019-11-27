package com.Solver.Solver.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.Solver.Solver.ModelClass.JobC;
import com.Solver.Solver.R;
import java.util.List;

public class JobAdapter  extends ArrayAdapter<JobC> {

    List<JobC> jobCList;
    Context context;

public JobAdapter(Context context, int resource,List<JobC> jobCList) {

        super(context, resource);
        this.jobCList=jobCList;
        this.context=context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){

            convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.job_sample_row, parent, false);

        }
        return convertView;
    }

}
