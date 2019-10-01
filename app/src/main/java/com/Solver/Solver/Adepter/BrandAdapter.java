package com.Solver.Solver.Adepter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.Solver.Solver.ModelClass.Brands;
import com.Solver.Solver.R;

import java.util.ArrayList;

public class BrandAdapter extends BaseAdapter implements SpinnerAdapter {
    ArrayList<Brands> brandsArrayList;
    Context context;
    String[] colors = {"#13edea","#e20ecd","#15ea0d"};
    String[] colorsback = {"#FF000000","#FFF5F1EC","#ea950d"};

    public BrandAdapter(Context context, ArrayList<Brands> product_typesList) {
        this.brandsArrayList = product_typesList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return brandsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return brandsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =  View.inflate(context, R.layout.spennersamplelayout, null);
        TextView textView = (TextView) view.findViewById(R.id.showTestSpinnerId);
        textView.setText(brandsArrayList.get(position).getBrand_name());
        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View view;
        view =  View.inflate(context, R.layout.spennersamplelayout, null);
        final TextView textView = (TextView) view.findViewById(R.id.showTestSpinnerId);
        textView.setText(brandsArrayList.get(position).getBrand_name());

       /* textView.setTextColor(Color.parseColor(colors[position]));
        textView.setBackgroundColor(Color.parseColor(colorsback[position]));
*/

        return view;
    }

}
