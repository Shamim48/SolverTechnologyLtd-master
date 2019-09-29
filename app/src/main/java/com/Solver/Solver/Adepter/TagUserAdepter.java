package com.Solver.Solver.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Solver.Solver.ModelClass.SignUp;
import com.Solver.Solver.ModelClass.UserNameAndID;
import com.Solver.Solver.R;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TagUserAdepter extends RecyclerView.Adapter<TagUserAdepter.TagUserHolder> {

    Context context;
    List<SignUp> userList;

    public TagUserAdepter(Context context, List<SignUp> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public TagUserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        TagUserHolder tagUserHolder=new TagUserHolder(view);
        return tagUserHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TagUserHolder holder, final int position) {

        final SignUp userInfo=userList.get(position);
        holder.userNameTextView.setText(userInfo.getName());
        Glide.with(holder.userImageV).load(userInfo.getImageUrl()).placeholder(R.drawable.ic_insert_photo_black_24dp).into(holder.userImageV);
/*
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName=userList.get(position).getName();
                String userId=userList.get(position).getUserId();
                UserNameAndID userNameAndID=new UserNameAndID();
                userNameAndID.setUserName(userName);
                userNameAndID.setUserID(userId);

            }
        });
*/
    }



    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class TagUserHolder extends RecyclerView.ViewHolder {

        TextView userNameTextView;
        CircleImageView userImageV;
        RelativeLayout parentLayout;

        public TagUserHolder(View itemView) {
            super(itemView);
            userNameTextView=itemView.findViewById(R.id.usernameUiId);
            userImageV=itemView.findViewById(R.id.profile_imageUiId);
            parentLayout=itemView.findViewById(R.id.user_ItmRowId);
        }
    }
}
