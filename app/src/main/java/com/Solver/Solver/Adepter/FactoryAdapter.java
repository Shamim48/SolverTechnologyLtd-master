package com.Solver.Solver.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.Solver.Solver.ModelClass.Factories;
import com.Solver.Solver.R;

import java.util.ArrayList;
import java.util.List;

public class FactoryAdapter extends ArrayAdapter<Factories> implements Filterable {
List<Factories> factoriesList;

    public FactoryAdapter(Context context, List<Factories> factoriesList) {
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


        Factories factories = getItem(position);

        if (factories != null) {
            textViewName.setText(factories.getCompany_name());

        }

        return convertView;

    }
    @Override
    public Filter getFilter() {
        return factoriesFilter;
    }
    private Filter factoriesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Factories> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(factoriesList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Factories item : factoriesList) {
                    if (item.getCompany_name().toLowerCase().contains(filterPattern)) {
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
            return ((Factories) resultValue).getCompany_name();
        }
    };
}

