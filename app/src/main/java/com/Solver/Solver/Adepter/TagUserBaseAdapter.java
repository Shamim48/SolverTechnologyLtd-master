package com.Solver.Solver.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.Solver.Solver.ModelClass.SignUp;
import com.Solver.Solver.ModelClass.UserNameAndID;
import com.Solver.Solver.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TagUserBaseAdapter extends BaseAdapter implements Filterable {
    Context context;
    List<SignUp> tagUserList;

    public TagUserBaseAdapter(Context context, List<SignUp> tagUserList) {
        this.context = context;
        this.tagUserList = tagUserList;
    }

    @Override
    public int getCount() {
        return tagUserList.size();
    }

    @Override
    public Object getItem(int i) {
        return tagUserList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.user_item,viewGroup,false);
        }
        final SignUp userInfo=tagUserList.get(i);
        TextView userNameTv=view.findViewById(R.id.usernameUiId);
        CircleImageView userImage=view.findViewById(R.id.profile_imageUiId);
        userNameTv.setText(userInfo.getName());
        Glide.with(userImage).load(userInfo.getImageUrl()).placeholder(R.drawable.ic_insert_photo_black_24dp).into(userImage);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /*// UserNameAndID userNameAndID=new UserNameAndID();
                userNameAndID.setUserName(userInfo.getName());
                userNameAndID.setUserID(userInfo.getUserId());*/
            }
        });
        return view;
    }

   /* @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public String convertResultToString(Object resultValue) {
                return ((SignUp) resultValue).getName();
            }*/

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
                    String str = ((SignUp) resultValue).getName();
                    return str;
                }
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<SignUp> departmentsSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (SignUp user : tagUserList) {
                        if (user.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            departmentsSuggestion.add(user);
                        }
                    }
                    filterResults.values = departmentsSuggestion;
                    filterResults.count = departmentsSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                tagUserList.clear();
                tagUserList.addAll((Collection<? extends SignUp>) results.values );
                notifyDataSetChanged();
               /* if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    for (Object object : (List<?>) results.values) {
                        if (object instanceof SignUp) {
                            tagUserList.add((SignUp) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    tagUserList.addAll(tagUserList);
                    notifyDataSetInvalidated();
                }*/
            }
        };
    }

