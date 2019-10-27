package com.Solver.Solver.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.Solver.Solver.Adepter.TagUserArrayAdapter;
import com.Solver.Solver.Adepter.UserAdapter;
import com.Solver.Solver.MessageActivity;
import com.Solver.Solver.ModelClass.Chatlist;
import com.Solver.Solver.ModelClass.SignUp;
import com.Solver.Solver.Notifications.Token;
import com.Solver.Solver.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFm extends Fragment {

    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<SignUp> mUsers;

    EditText search_users;
    private SearchView userSV;
    private LinearLayout lltCtLV;
    FirebaseUser fuser;
    DatabaseReference reference;
    private ListView tagUserLV;
    private ImageButton closeBtn;
    List<SignUp> tagUserList;
    DatabaseReference userRef;
    TagUserArrayAdapter tagUserArrayAdapter;


    private List<Chatlist> usersList;


    public ChatFm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat_fm, container, false);


        userSV=view.findViewById(R.id.employeeName_CtId);
        tagUserLV=view.findViewById(R.id.userTagLVCtId);
        lltCtLV=view.findViewById(R.id.lltCtLVId);
        closeBtn=view.findViewById(R.id.closeBtnCtId);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        fuser = FirebaseAuth.getInstance().getCurrentUser();
        userRef=FirebaseDatabase.getInstance().getReference().child("User");

        usersList = new ArrayList<>();
        tagUserLV.setVisibility(View.GONE);
        lltCtLV.setVisibility(View.GONE);

        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(fuser.getUid());
       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chatlist chatlist = snapshot.getValue(Chatlist.class);
                    usersList.add(chatlist);
                }

                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        userSV.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lltCtLV.setVisibility(View.VISIBLE);
                tagUserLV.setVisibility(View.VISIBLE);
                tagUser();
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lltCtLV.setVisibility(View.GONE);
            }
        });


        updateToken(FirebaseInstanceId.getInstance().getToken());








       // mUsers = new ArrayList<>();

        /*readUsers();

        search_users = view.findViewById(R.id.search_users);
        search_users.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUsers(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/


        return view;
    }
    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(fuser.getUid()).setValue(token1);
    }
    private void tagUser() {

        tagUserList=new ArrayList<>();

        tagUserList.clear();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    SignUp signUp=data.getValue(SignUp.class);
                    tagUserList.add(signUp);
                }
                // tagUserAdepter=new TagUserAdepter(Write_Schedule.this,tagUserList);

                // tagUserBaseAdapter=new TagUserBaseAdapter(Write_Schedule.this,tagUserList);
                //  userSV.setAdapter(tagUserBaseAdapter);
                tagUserArrayAdapter=new TagUserArrayAdapter(getContext(),tagUserList);
                tagUserLV.setAdapter(tagUserArrayAdapter);
                tagUserArrayAdapter.notifyDataSetChanged();
                // userSV.setAdapter(tagUserArrayAdapter);

                getId();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        userSV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {



                tagUserArrayAdapter.getFilter().filter(newText);

                return false;
            }
        });


        tagUserLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra("userid",tagUserList.get(i).getUserId());
                startActivity(intent);
               // tagUserName=tagUserList.get(i).getName();
             //   tagUserId=tagUserList.get(i).getUserId();
              //  userSV.setQuery(tagUserName,true);
                // showTV.setText(tagUserName+"\n ID: "+tagUserId);
                lltCtLV.setVisibility(View.GONE);
                tagUserLV.setVisibility(View.GONE);

            }
        });
    }

    private void chatList() {
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("User");
        reference.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    SignUp user = snapshot.getValue(SignUp.class);
                    for (Chatlist chatlist : usersList){
                        if (user.getUserId().equals(chatlist.getId())){
                            mUsers.add(user);
                        }
                    }
                }
                userAdapter = new UserAdapter(getContext(), mUsers, true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });}

    private void searchUsers(String s) {

        final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference().child("User").orderByChild("name")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    SignUp user = snapshot.getValue(SignUp.class);

                    assert user != null;
                    assert fuser != null;
                    if (!user.getUserId().equals(fuser.getUid())){
                        mUsers.add(user);
                    }
                }

                userAdapter = new UserAdapter(getContext(), mUsers, false);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (search_users.getText().toString().equals("")) {
                    mUsers.clear();
                    try {


                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        SignUp user = snapshot.getValue(SignUp.class);

                        if (!user.getUserId().equals(firebaseUser.getUid())) {
                            mUsers.add(user);
                        }

                    }

                    userAdapter = new UserAdapter(getContext(), mUsers, false);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(userAdapter);
                    }catch (Exception e){

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        String tagUserName=userSV.getQuery().toString();
        if(tagUserName.equals("")){
            lltCtLV.setVisibility(View.GONE);
            tagUserLV.setVisibility(View.GONE);
        }else {
            lltCtLV.setVisibility(View.VISIBLE);
            tagUserLV.setVisibility(View.VISIBLE);
        }
    }
}
