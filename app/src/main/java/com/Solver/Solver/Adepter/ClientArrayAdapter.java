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
import com.Solver.Solver.R;

import java.util.ArrayList;
import java.util.List;

public class ClientArrayAdapter extends ArrayAdapter<Factories> implements Filterable {
    private List<Factories> clientList,tempItems ,suggestions;
//int resource ,int tvId,
    public ClientArrayAdapter( Context context, List<Factories> clientList) {
        super(context, 0, clientList);
        this.clientList = clientList;
        tempItems = new ArrayList<Factories>(clientList); // this makes the difference.
        suggestions = new ArrayList<Factories>();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.tagclientsample_row, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.companyNameTSRTvId);
        TextView compAddress = convertView.findViewById(R.id.companyAddressTSRTvId);
        RelativeLayout parentLayout=convertView.findViewById(R.id.user_ItmRowId);

        final Factories clientinfo = clientList.get(position);


        if (clientinfo != null) {
            textViewName.setText(clientinfo.getCompany_name());
            compAddress.setText(clientinfo.getAddress());
           // Glide.with(imageViewFlag).load(clientinfo.getImageUrl()).placeholder(R.drawable.ic_insert_photo_black_24dp).into(imageViewFlag);


            // userNameAndID.setUserID(clientList.get(position).getUserId());
            //  userNameAndID.setUserName(clientList.get(position).getName());


        }

       /* textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // DatabaseReference tagUserIdRef= FirebaseDatabase.getInstance().getReference().child("TagUserId");
              // tagUserIdRef.setValue(clientList.get(position).getUserId());
                *//*Intent intent=new Intent(getContext(),Write_Schedule.class);
                intent.putExtra("UserId",clientList.get(position).getUserId());
                getContext().startActivity(intent);*//*
            }
        });*/

      /*
        UserNameAndID userNameAndID=new UserNameAndID(clientList.get(position).getName().toString(),clientList.get(position).getUserId().toString());
        List<UserNameAndID> userNameAndIDList=new ArrayList<>();
        DatabaseReference tagUserIdRef= FirebaseDatabase.getInstance().getReference().child("TagUserId");
        tagUserIdRef.setValue(userNameAndID).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(),"Successful",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(),"Exception : "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        });

*/
        return convertView;
    }

    @Override
    public int getCount() {
        return clientList.size();
    }


    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((Factories) resultValue).getCompany_name();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Factories factories : tempItems) {
                    if (factories.getCompany_name().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(factories);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Factories> filterList = (ArrayList<Factories>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Factories people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };

/*

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Factories> filteredList=new ArrayList<>();
            if(constraint==null || constraint.length()==0){
                filteredList.addAll(clientList);
            }else {
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (Factories userInfo : clientList){
                    if (userInfo.getCompany_name().toLowerCase().contains(filterPattern)){
                        filteredList.add(userInfo);
                    }
                }
            }

            FilterResults results=new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            clientList.clear();
            clientList.addAll((Collection<? extends Factories>) results.values);
            notifyDataSetChanged();

        }
    };*/
}

