package com.Solver.Solver.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Solver.Solver.Adepter.EmployeeAdepter;
import com.Solver.Solver.ModelClass.SignUp;
import com.Solver.Solver.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeFm extends Fragment  {

    DatabaseReference rootRef;
    DatabaseReference userRef;
    List<SignUp> userInfo;
    RecyclerView employeeRv;
    EmployeeAdepter adepter;

    public EmployeeFm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        employeeRv=view.findViewById(R.id.employeeRVId);


        userInfo=new ArrayList<>();
        rootRef= FirebaseDatabase.getInstance().getReference();
        userRef=rootRef.child("User");
        // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
      //  userRef.keepSynced(true);
        rootRef.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                userInfo.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    SignUp signUp=data.getValue(SignUp.class);
                    userInfo.add(signUp);
                }
                adepter=new EmployeeAdepter(getContext(),userInfo);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                employeeRv.setLayoutManager(linearLayoutManager);
                employeeRv.setAdapter(adepter);


                //   Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



    }


}
