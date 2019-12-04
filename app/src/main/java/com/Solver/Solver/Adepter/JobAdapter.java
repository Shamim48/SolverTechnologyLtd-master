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
import com.Solver.Solver.ModelClass.Product;
import com.Solver.Solver.ModelClass.productHolder;
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

            jobDesLLt.setVisibility(View.GONE);

            convertView.setTag(new JobC(jobDesLLt,jobCB,jobTtlTv));

            jobCB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox cb= (CheckBox) view;
                    JobC job=(JobC) cb.getTag();
                    job.setChecked(cb.isChecked());

                    if(cb.isChecked()) {
                         jobDesLLt.setVisibility(View.VISIBLE);
                        //  checkedListener.getCheckListener(position);

                    }
                    if(!cb.isChecked()){
                        //76yd   ` checkedListener.removeProduct(position);
                        jobDesLLt.setVisibility(View.GONE);
                        addJobBtn.setEnabled(true);
                        addJobBtn.setBackgroundResource(R.drawable.buttonbg);
                       // quantityEd.setText("");
                    }
                }
            });

/*

            addQuantityBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String quantity=quantityEd.getText().toString();
                    if (quantity.isEmpty()){
                        Toast.makeText(getContext(),"Please Enter Quantity",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    checkedListener.getCheckListener(position);
                    int qut=Integer.parseInt(quantity);
                    productList.get(position).setQuantity(qut);
                    addQuantityBtn.setEnabled(false);
                    addQuantityBtn.setBackgroundResource(R.drawable.edittextbg);
                    addQuantityBtn.setTextColor(Color.GRAY);

                }
            });
*/


           /* productCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(checkedListener!=null){
                        if(productCb.isChecked()) {
                            checkedListener.getCheckListener(position);
                        }
                       if(!productCb.isChecked()){

                            //checkedListener.removeProduct(position);

                        }
                    }

                }

            });*/

        }else {

            JobC viewHolder = (JobC) convertView
                    .getTag();
            jobCB = viewHolder.getCheckBox();
            jobTtlTv = viewHolder.getJobTitleTv();
            jobDesLLt = viewHolder.getJobDesLLt();
        }
        jobCB.setTag(job);

          /*
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
            */


        return convertView;
    }

}
