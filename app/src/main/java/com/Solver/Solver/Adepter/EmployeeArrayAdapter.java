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
import com.Solver.Solver.ModelClass.SignUp;
import com.Solver.Solver.R;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
public class EmployeeArrayAdapter extends ArrayAdapter<SignUp>  implements Filterable {

   List<SignUp> usertagList;
    public EmployeeArrayAdapter( Context context,  List<SignUp> userList) {
        super(context, 0, userList);
        this.usertagList = new ArrayList<>(userList);
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.user_item, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.usernameUiId);
        CircleImageView imageViewFlag = convertView.findViewById(R.id.profile_imageUiId);
        RelativeLayout parentLayout=convertView.findViewById(R.id.user_ItmRowId);

        final SignUp userinfo = usertagList.get(position);

        if (userinfo != null) {
            textViewName.setText(userinfo.getName());
            Glide.with(imageViewFlag).load(userinfo.getImageUrl()).placeholder(R.drawable.ic_insert_photo_black_24dp).into(imageViewFlag);

        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return employeeFilter;
    }
    private Filter employeeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<SignUp> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(usertagList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (SignUp item : usertagList) {
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
