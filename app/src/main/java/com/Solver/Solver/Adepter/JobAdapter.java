package com.Solver.Solver.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.Solver.Solver.ModelClass.JobC;
import com.Solver.Solver.R;
import java.util.List;

public class JobAdapter extends ArrayAdapter<JobC> {

    List<JobC> jobCList;
    Context context;
    CheckBox jobCB;
    Spinner jobCategorySp;
    EditText jobDesEt;
    TextView jobTtlTv;
    LinearLayout jobDesLLt;
    Button addJobBtn;

public JobAdapter(Context context,List<JobC> jobCList) {

        super(context, 0,jobCList);
        this.jobCList=jobCList;
        this.context=context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    JobC job=jobCList.get(position);
        if(convertView==null){
            convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.job_sample_row, parent, false);

            jobCB=convertView.findViewById(R.id.jobCBId);
            jobCategorySp=convertView.findViewById(R.id.jobCategorySpId);
            jobDesEt=convertView.findViewById(R.id.jobDesEtId);
            jobTtlTv=convertView.findViewById(R.id.jobNameTvId);
            jobDesLLt=convertView.findViewById(R.id.jobDesLLtId);
            addJobBtn=convertView.findViewById(R.id.addJobBtnId);
            jobTtlTv.setText(jobCList.get(position).getJobTitle());
            jobCB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox checkBox=(CheckBox) view;
                    if(checkBox.isChecked()){
                        jobDesLLt.setVisibility(View.VISIBLE);
                    }
                    else if(!checkBox .isChecked()){
                        jobDesLLt.setVisibility(View.GONE);
                    }
                }
            });
        }

        return convertView;
    }

}
