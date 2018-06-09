package com.blackhammers.kalisz.planuz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SubjectsActivity extends AppCompatActivity implements onSubjectsAdapterListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Subjects> subjectsList;
    SubjectsListAdapter adapter;
    RecyclerView recyclerView;
    private  Menu menu;
    private MenuItem menuItem;
    boolean isFavClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjcets);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Przedmioty");
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutId);
        viewPager = (ViewPager) findViewById(R.id.viewPagerId);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerSubjectsView);
        firebaseDatabase = FirebaseDatabase.getInstance();
        subjectsList = new ArrayList<>();
        menuItem = findViewById(R.id.my_subject);
        adapter = new SubjectsListAdapter(subjectsList);
        SubjectsListFragment fragment;
        setSupportActionBar(toolbar);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        isFavClicked = sharedPreferences.getBoolean("menu_item", false);


        int coursesId = getIntent().getIntExtra("coursesId", 0);
        int facultiesId = getIntent().getIntExtra("facultiesId", 0);
        int groupsId = getIntent().getIntExtra("groupsId", 0);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SubjectsListFragment().newInstance(getMondayPlanFromFirebase(facultiesId, coursesId, groupsId)), "PN");
        adapter.addFragment(new SubjectsListFragment().newInstance(getTuesdayPlanFromFirebase(facultiesId, coursesId, groupsId)), "WT");
        adapter.addFragment(new SubjectsListFragment().newInstance(getWednesdayPlanFromFirebase(facultiesId, coursesId, groupsId)), "SR");
        adapter.addFragment(new SubjectsListFragment().newInstance(getThursdayPlanFromFirebase(facultiesId, coursesId, groupsId)), "CZ");
        adapter.addFragment(new SubjectsListFragment().newInstance(getFridayPlanFromFirebase(facultiesId, coursesId, groupsId)), "PT");
        adapter.addFragment(new SubjectsListFragment().newInstance(getSaturdayPlanFromFirebase(facultiesId, coursesId, groupsId)), "SB");
        adapter.addFragment(new SubjectsListFragment().newInstance(getSundayPlanFromFirebase(facultiesId, coursesId, groupsId)), "ND");
        adapter.addFragment(new SubjectsListFragment().newInstance(getIrregularPlanFromFirebase(facultiesId, coursesId, groupsId)), "+");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_subject, menu);
       // menu.getItem(0).setIcon(menuItem.getIcon());
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final SharedPreferences myPrefs = getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = myPrefs.edit();
        isFavClicked = myPrefs.getBoolean("menu_item", false);


        switch (item.getItemId()) {
            case R.id.my_subject:
                isFavClicked=true;
                editor.putBoolean("menu_item", isFavClicked);
                editor.commit();
                invalidateOptionsMenu();
                return true;

            case R.id.my_subject2:

                isFavClicked=false;
                editor.putBoolean("menu_item", isFavClicked);
                editor.commit();
                invalidateOptionsMenu();
                return super.onOptionsItemSelected(item);
        }
        return true;
//        int id = item.getItemId();
//
//        if (id == R.id.my_subject) {
//
//            if (item.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.ic_gold_star).getConstantState())) {
//                item.setIcon(R.drawable.ic_fav_star);
//                this.menuItem = item;
//            } else {
//                item.setIcon(R.drawable.ic_gold_star);
//                this.menuItem = item;
//
//            }
//
//
//            return true;
//        }
//
//
//
//        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(isFavClicked==true){
            menu.findItem(R.id.my_subject).setVisible(false);
            menu.findItem(R.id.my_subject2).setVisible(true);

        }else{
            menu.findItem(R.id.my_subject).setVisible(true);
            menu.findItem(R.id.my_subject2).setVisible(false);

        }



        return super.onPrepareOptionsMenu(menu);
    }




    public List<Subjects> getMondayPlanFromFirebase(Integer facultiesKey, Integer coursesKey, Integer groupsKey){

        final List<Subjects> subjectsList=new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("script-scraped/"+facultiesKey+"/courses/"+coursesKey+"/groups/"+groupsKey+"/schedule/monday");
        databaseReference.keepSynced(true);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Subjects subjects = dataSnapshot.getValue(Subjects.class);
                subjectsList.add(subjects);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       return  subjectsList;
    }
    public List<Subjects> getTuesdayPlanFromFirebase(Integer facultiesKey, Integer coursesKey, Integer groupsKey){
        final List<Subjects> subjectsList=new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("script-scraped/"+facultiesKey+"/courses/"+coursesKey+"/groups/"+groupsKey+"/schedule/tuesday");
        databaseReference.keepSynced(true);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Subjects subjects = dataSnapshot.getValue(Subjects.class);
                subjectsList.add(subjects);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return  subjectsList;
    }
    public List<Subjects> getWednesdayPlanFromFirebase(Integer facultiesKey, Integer coursesKey, Integer groupsKey){
        final List<Subjects> subjectsList=new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("script-scraped/"+facultiesKey+"/courses/"+coursesKey+"/groups/"+groupsKey+"/schedule/wednesday");
        databaseReference.keepSynced(true);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Subjects subjects = dataSnapshot.getValue(Subjects.class);
                subjectsList.add(subjects);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return  subjectsList;
    }

    public List<Subjects> getThursdayPlanFromFirebase(Integer facultiesKey, Integer coursesKey, Integer groupsKey){
        final List<Subjects> subjectsList=new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("script-scraped/"+facultiesKey+"/courses/"+coursesKey+"/groups/"+groupsKey+"/schedule/thursday");
        databaseReference.keepSynced(true);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Subjects subjects = dataSnapshot.getValue(Subjects.class);
                subjectsList.add(subjects);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return  subjectsList;
    }

    public List<Subjects> getFridayPlanFromFirebase(Integer facultiesKey, Integer coursesKey, Integer groupsKey){
        final List<Subjects> subjectsList=new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("script-scraped/"+facultiesKey+"/courses/"+coursesKey+"/groups/"+groupsKey+"/schedule/friday");
        databaseReference.keepSynced(true);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Subjects subjects = dataSnapshot.getValue(Subjects.class);
                subjectsList.add(subjects);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return  subjectsList;
    }

    public List<Subjects> getSaturdayPlanFromFirebase(Integer facultiesKey, Integer coursesKey, Integer groupsKey){
        final List<Subjects> subjectsList=new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("script-scraped/"+facultiesKey+"/courses/"+coursesKey+"/groups/"+groupsKey+"/schedule/saturday");
        databaseReference.keepSynced(true);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Subjects subjects = dataSnapshot.getValue(Subjects.class);
                subjectsList.add(subjects);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return  subjectsList;
    }

    public List<Subjects> getSundayPlanFromFirebase(Integer facultiesKey, Integer coursesKey, Integer groupsKey){
        final List<Subjects> subjectsList=new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("script-scraped/"+facultiesKey+"/courses/"+coursesKey+"/groups/"+groupsKey+"/schedule/sunday");
        databaseReference.keepSynced(true);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Subjects subjects = dataSnapshot.getValue(Subjects.class);
                subjectsList.add(subjects);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return  subjectsList;
    }


    public List<Subjects> getIrregularPlanFromFirebase(Integer facultiesKey, Integer coursesKey, Integer groupsKey){
        final List<Subjects> subjectsList=new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("script-scraped/"+facultiesKey+"/courses/"+coursesKey+"/groups/"+groupsKey+"/schedule/irregular");
        databaseReference.keepSynced(true);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Subjects subjects = dataSnapshot.getValue(Subjects.class);
                subjectsList.add(subjects);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return  subjectsList;
    }


    @Override
    public void onSubjectsAdapterListener(Subjects subjects) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
