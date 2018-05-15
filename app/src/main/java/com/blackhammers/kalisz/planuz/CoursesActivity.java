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

public class CoursesActivity extends AppCompatActivity  implements  onCoursesAdapterListener{

    TextView textView;
    RecyclerView recyclerView;
    CoursesAdapter adapter;
    List<Courses> coursesList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Integer> keys;
    android.support.v7.widget.Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_id);
        recyclerView = findViewById(R.id.RecyclerViewCoursesID);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

        coursesList = new ArrayList<>();
        keys = new ArrayList<>();



        adapter = new CoursesAdapter(this, coursesList, this);
        recyclerView.setAdapter(adapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        getCoursesFromDatabase(id);
        adapter.notifyDataSetChanged();
    }

    public void getCoursesFromDatabase(Integer key) {
        databaseReference = firebaseDatabase.getReference().child("data/"+key+"/courses");
        databaseReference.keepSynced(true);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Courses courses = dataSnapshot.getValue(Courses.class);
                coursesList.add(courses);

                adapter.notifyDataSetChanged();

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                Courses courses = dataSnapshot.getValue(Courses.class);
                String key = dataSnapshot.getKey();

                int index = keys.indexOf(key);

                coursesList.set(index, courses);

                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                String key = dataSnapshot.getKey();

                int index = keys.indexOf(key);

                coursesList.remove(index);

                adapter.notifyDataSetChanged();

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        adapter.notifyDataSetChanged();





//        databaseReference = firebaseDatabase.getReference().child("data/"+key+"/courses");
//
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                    for(DataSnapshot ds : dataSnapshot.getChildren()){
//                    if(dataSnapshot.exists()){
//                        Courses courses = ds.getValue(Courses.class);
//                        coursesList.add(courses);
//                        recyclerView.setAdapter(adapter);
//                        Log.d("Courses name ", courses.getName());
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//        databaseReference.addValueEventListener(valueEventListener);
//        adapter.notifyDataSetChanged();
////        databaseReference = firebaseDatabase.getReference().child("data");
//        databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }





//    public void getCoursesFromDatabase(){
//        databaseReference = firebaseDatabase.getReference().child("data").child("courses");
//        databaseReference.keepSynced(true);
//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//
//
//                Courses courses = dataSnapshot.getValue(Courses.class);
//                coursesList.add(courses);
//
//                String key = databaseReference.getKey();
//                keys.add(key);
//
//                adapter.notifyDataSetChanged();
//
//                recyclerView.setAdapter(adapter);
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                Courses courses = dataSnapshot.getValue(Courses.class);
//                String key = dataSnapshot.getKey();
//
//                int index = keys.indexOf(key);
//
//                coursesList.set(index, courses);
//
//                adapter.notifyDataSetChanged();
//                recyclerView.setAdapter(adapter);
//
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
//                coursesList.remove(index);
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
//
//        adapter.notifyDataSetChanged();
//
//
//    }

    @Override
    public void onCoursesSelectedListener(Courses courses) {
        Intent intent = new Intent(getApplicationContext(), GroupsActivity.class);
        intent.putExtra("id", courses.getId());
        startActivity(intent);

    }
}
