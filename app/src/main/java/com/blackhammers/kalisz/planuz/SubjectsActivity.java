package com.blackhammers.kalisz.planuz;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SubjectsActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjcets);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        defineView();
        bindView();
    }
    private void defineView(){
        tabLayout=findViewById(R.id.tabLayoutId);
        viewPager=findViewById(R.id.viewPagerId);
    }
    public void bindView(){
        setupViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_groups, menu);               // POPRAW !!!!
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ShowUserListFragment().newInstance(setUpUserList()), "PON");
        adapter.addFragment(new ShowUserListFragment().newInstance(setUpChatListUsers()), "WTO");
        adapter.addFragment(new ShowUserListFragment().newInstance(setUpOnlineUsers()), "SRO");
        adapter.addFragment(new ShowUserListFragment().newInstance(setUpChatListUsers()), "CZW");
        adapter.addFragment(new ShowUserListFragment().newInstance(setUpChatListUsers()), "PT");
        adapter.addFragment(new ShowUserListFragment().newInstance(setUpUserList()), "SOB");
        adapter.addFragment(new ShowUserListFragment().newInstance(setUpChatListUsers()), "ND");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private List<Subjects> setUpUserList(){
        final List<Subjects> userList=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator=  dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    DataSnapshot snapshot=iterator.next();
                    userList.add(snapshot.getValue(Subjects.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return userList;
    }
    private List<Subjects> setUpChatListUsers(){
        List<Subjects> subjectsList=new ArrayList<>();
        subjectsList.add(new Subjects("ram@gmail.com"));
        return subjectsList;
    }
    private List<Subjects> setUpOnlineUsers(){
        List<Subjects> userList=new ArrayList<>();
        userList.add(new Subjects("ram@gmail.com"));
        userList.add(new Subjects("hari@gmail.com"));
        return userList;
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
