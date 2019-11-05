package com.Solver.Solver.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.Solver.Solver.GroupChatAtv;
import com.Solver.Solver.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GroupChatFm extends Fragment {

    private ListView groupList;
    private ArrayList<String> groupArrayList=new ArrayList<>();
    private ArrayAdapter<String> groupAdapter;
    private DatabaseReference groupREf;

    public GroupChatFm() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_chat_fm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        groupList=view.findViewById(R.id.groupListId);
        // initialize Database Reference
        groupREf= FirebaseDatabase.getInstance().getReference().child("Group");
       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        groupREf.keepSynced(true);
        //initialize array adapter
        groupAdapter=new ArrayAdapter<String>(getContext(),R.layout.groupname_row,R.id.groupNameSampleTextId,groupArrayList);
       //Set adapter
        groupList.setAdapter(groupAdapter);

        // call retrieve and Show Group method
        showGroup();

        groupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String currentGroupName=adapterView.getItemAtPosition(i).toString();

                Intent intent=new Intent(getContext(), GroupChatAtv.class);
                intent.putExtra("GroupName",currentGroupName);
                startActivity(intent);

            }
        });

    }
// retrieve and Show Group method
    private void showGroup() {

        groupREf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> set=new HashSet<>();
                Iterator iterator=dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    set.add(((DataSnapshot)iterator.next()).getKey());
                }
                groupArrayList.clear();
                groupArrayList.addAll(set);
                groupAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
