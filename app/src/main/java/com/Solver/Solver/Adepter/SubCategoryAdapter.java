package com.Solver.Solver.Adepter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.Solver.Solver.ModelClass.Sub_categories;
import com.Solver.Solver.R;

import java.util.ArrayList;

public class SubCategoryAdapter extends BaseAdapter implements SpinnerAdapter {

    ArrayList<Sub_categories> sub_caegoryList;
    Context context;
    String[] colors = {"#13edea","#e20ecd","#15ea0d"};
    String[] colorsback = {"#FF000000","#FFF5F1EC","#ea950d"};

    public SubCategoryAdapter(Context context, ArrayList<Sub_categories> product_typesList) {
        this.sub_caegoryList = product_typesList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return sub_caegoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return sub_caegoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =  View.inflate(context, R.layout.spennersamplelayout, null);
        TextView textView = (TextView) view.findViewById(R.id.showTestSpinnerId);
        textView.setText(sub_caegoryList.get(position).getSub_cat_name());
        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View view;
        view =  View.inflate(context, R.layout.spennersamplelayout, null);
        final TextView textView = (TextView) view.findViewById(R.id.showTestSpinnerId);
        textView.setText(sub_caegoryList.get(position).getSub_cat_name());

       /* textView.setTextColor(Color.parseColor(colors[position]));
        textView.setBackgroundColor(Color.parseColor(colorsback[position]));
*/

        return view;
    }

}
