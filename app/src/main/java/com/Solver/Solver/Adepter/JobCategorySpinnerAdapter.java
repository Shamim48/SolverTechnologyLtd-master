package com.Solver.Solver.Adepter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.Solver.Solver.ModelClass.JobCategory;
import com.Solver.Solver.R;

import java.util.ArrayList;

public class JobCategorySpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    ArrayList<JobCategory> jobCaegoryList;
    Context context;

    public JobCategorySpinnerAdapter(Context context, ArrayList<JobCategory> jobCategories) {
        this.jobCaegoryList = jobCategories;
        this.context = context;
    }

    @Override
    public int getCount() {
        return jobCaegoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return jobCaegoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =  View.inflate(context, R.layout.spennersamplelayout, null);
        TextView textView = (TextView) view.findViewById(R.id.showTestSpinnerId);
        textView.setText(jobCaegoryList.get(position).getJobCategoryName());
        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View view;
        view =  View.inflate(context, R.layout.spennersamplelayout, null);
        final TextView textView = (TextView) view.findViewById(R.id.showTestSpinnerId);
        textView.setText(jobCaegoryList.get(position).getJobCategoryName());

       /* textView.setTextColor(Color.parseColor(colors[position]));
        textView.setBackgroundColor(Color.parseColor(colorsback[position]));
*/

        return view;
    }
}