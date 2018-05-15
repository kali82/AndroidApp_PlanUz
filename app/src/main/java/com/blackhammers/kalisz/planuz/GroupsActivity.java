package com.blackhammers.kalisz.planuz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GroupsActivity extends AppCompatActivity implements onGroupsAdapterListener {
    TextView textView;
    RecyclerView recyclerView;
    GroupsAdapter adapter;
    List<Groups> groupsList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Integer> keys;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_id);
        recyclerView = findViewById(R.id.groupsRecyclerID);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

        groupsList = new ArrayList<>();
        keys = new ArrayList<>();



        adapter = new GroupsAdapter(this, groupsList, this);
        recyclerView.setAdapter(adapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        getGroupsFromFirebase(id);
        adapter.notifyDataSetChanged();


 //       String text = getIntent().getStringExtra("name");
//
//        Faculties facultiesInf = intent.getParcelableExtra("Infname");
//        String text1 = facultiesInf.getname();
//
//        Faculties facultiesEko = intent.getParcelableExtra("Ekoname");
//        String text = facultiesEko.getname();
//
//        textView = (TextView) findViewById(R.id.textId);
//        textView.setText(text);
    }

    public void getGroupsFromFirebase(Integer key){
        databaseReference = firebaseDatabase.getReference().child("data/"+key+"/courses/"+key+"/groups");
        databaseReference.keepSynced(true);
//
//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Groups groups = dataSnapshot.getValue(Groups.class);
//                groupsList.add(groups);
//
//                adapter.notifyDataSetChanged();
//
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                Groups groups = dataSnapshot.getValue(Groups.class);
//                String key = dataSnapshot.getKey();
//
//                int index = keys.indexOf(key);
//
//                groupsList.set(index, groups);
//
//                adapter.notifyDataSetChanged();
//                recyclerView.setAdapter(adapter);
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                String key = dataSnapshot.getKey();
//
//                int index = keys.indexOf(key);
//
//                groupsList.remove(index);
//
//                adapter.notifyDataSetChanged();
//
//                recyclerView.setAdapter(adapter);
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        adapter.notifyDataSetChanged();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    for(DataSnapshot snap : ds.getChildren()){
//                    for(DataSnapshot templateSnapshot : dataSnapshot.getChildren()){
//                        for(DataSnapshot snap : templateSnapshot.getChildren())
                    if(dataSnapshot.exists()){
                        Groups groups = ds.getValue(Groups.class);
                        groupsList.add(groups);
                        recyclerView.setAdapter(adapter);
                        Log.d("Courses name ", groups.getName());
                    }
                }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);
        adapter.notifyDataSetChanged();
//        databaseReference = firebaseDatabase.getReference().child("data");
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }




    @Override
    public void onGroupsSelectedListener(Groups groups) {

    }
}

