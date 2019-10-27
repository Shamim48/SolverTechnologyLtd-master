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
import android.widget.Toast;

import com.Solver.Solver.Adepter.ClientAdepter;
import com.Solver.Solver.ModelClass.Client;
import com.Solver.Solver.ModelClass.Common_Resouces;
import com.Solver.Solver.R;
import com.google.firebase.auth.FirebaseAuth;
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
public class ClientFm extends Fragment {

    RecyclerView clientRV;
    ClientAdepter adepter;
    List<Client> clientList;
    DatabaseReference clientDatabaseRef;
    FirebaseAuth auth;
    String currentUserId;
    Common_Resouces common;

    public ClientFm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_fm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clientRV=view.findViewById(R.id.clientRvId);
        common=new Common_Resouces();

        clientList=new ArrayList<>();
        auth=FirebaseAuth.getInstance();
        currentUserId=auth.getCurrentUser().getUid();
        clientDatabaseRef=FirebaseDatabase.getInstance().getReference().child("Client");
      //  FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        clientDatabaseRef.keepSynced(true);

        clientDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clientList.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    Client client=data.getValue(Client.class);
                    clientList.add(client);




                }
                adepter=new ClientAdepter(getContext(),clientList);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                clientRV.setLayoutManager(linearLayoutManager);
                clientRV.setAdapter(adepter);
                Common_Resouces common_resouces=new Common_Resouces();
                common_resouces.setCommon_clientList(clientList);
                adepter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getContext(),"Error:"+databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
