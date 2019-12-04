package com.Solver.Solver.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Solver.Solver.ModelClass.Factories;
import com.Solver.Solver.ModelClass.SignUp;
import com.Solver.Solver.R;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
public class EmployeeArrayAdapter extends ArrayAdapter<SignUp>  implements Filterable {

    List<SignUp> factoriesList;

    public EmployeeArrayAdapter(Context context, List<SignUp> factoriesList) {
        super(context, 0,factoriesList);
        this.factoriesList=new ArrayList<>(factoriesList);
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.factory_sample_row, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.companyNameTSRTvId);


        SignUp factories = getItem(position);

        if (factories != null) {
            textViewName.setText(factories.getName());

        }

        return convertView;

    }

    @NotNull
    @Override
    public Filter getFilter() {
        return factoriesFilter;
    }
    private Filter factoriesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<SignUp> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(factoriesList);
            } else {

                String filterPattern = constraint.toString().toLowerCase().trim();

                for (SignUp item : factoriesList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((SignUp) resultValue).getName();
        }
    };
}

