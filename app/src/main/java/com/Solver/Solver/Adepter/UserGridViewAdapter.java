package com.Solver.Solver.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Solver.Solver.ModelClass.SignUp;
import com.Solver.Solver.ModelClass.User;
import com.Solver.Solver.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserGridViewAdapter extends ArrayAdapter<String> {

    private List<String> userList;
    Context context;
    String name;

    private UserGridViewAdapter.UserListener userListener;

    public UserGridViewAdapter(Context context,  List<String> userList) {

        super(context, 0, userList);
        this.userList = userList;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final String userId = userList.get(position);
        final TextView nameTv;

        final LinearLayout selectedPdtLlt;

        ImageButton closeBtn;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.usercardsample_row, parent, false);

            nameTv=convertView.findViewById(R.id.userNameCVSRId);

            closeBtn=convertView.findViewById(R.id.closeBtnSrId);

            DatabaseReference userRef= FirebaseDatabase.getInstance().getReference("User");
            Query userName=userRef.orderByChild("userId").equalTo(userList.get(position));
            userName.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot data:dataSnapshot.getChildren()){
                        SignUp selectedUser=data.getValue(SignUp.class);
                        name =selectedUser.getName();
                    }

                    nameTv.setText(name);

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userListener.removeGridUser(position);
                }
            });
        }

        return convertView;
    }

    public interface UserListener {

        void removeGridUser(int position);

    }

    public void setRemoveUser(UserGridViewAdapter.UserListener removeListener) {
        this.userListener = removeListener;
    }
}


