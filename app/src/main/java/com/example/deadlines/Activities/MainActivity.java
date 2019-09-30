package com.example.deadlines.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.deadlines.Adapters.ProjectListFragmentPagerAdapter;
import com.example.deadlines.R;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView mNavigationView;
    private ActionBar mActionBar;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()==R.id.credits)
        {
            Intent intent=new Intent(MainActivity.this, CreditsActivity.class);
            startActivity(intent);
            return true;
        }

        if(mActionBarDrawerToggle.onOptionsItemSelected(item)) {
                return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        PagerAdapter pagerAdapter= new ProjectListFragmentPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout=(TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        mActionBar=getSupportActionBar();

        //navigation drawer setup
        setupDrawerLayout();


    }

    void setupDrawerLayout()
    {
        mDrawerLayout = (DrawerLayout)findViewById(R.id.navigation_drawer);


        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open, R.string.close)
        {
            @Override
            public void onDrawerStateChanged(int newState) {

                super.onDrawerStateChanged(newState);
                boolean isOpened = mDrawerLayout.isDrawerOpen(mNavigationView);
                boolean isVisible = mDrawerLayout.isDrawerVisible(mNavigationView);

                if (!isOpened && !isVisible) {

                    if (newState == DrawerLayout.STATE_IDLE) {
                        restoreActionBar();
                    }

                    else {
                        overrideActionBar();
                    }
                }
            }

            private void restoreActionBar() {
                getSupportActionBar().setTitle("DeadLines");
                mActionBar.show();
                supportInvalidateOptionsMenu();
            }

            private void overrideActionBar() {
                mActionBar.hide();
                supportInvalidateOptionsMenu();
            }
        };


        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        mActionBar.setDisplayHomeAsUpEnabled(true);

        mNavigationView = (NavigationView)findViewById(R.id.navigation_drawer_menu);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.naigation_item_1:
                        Toast.makeText(MainActivity.this, "item 1",Toast.LENGTH_SHORT).show();break;
                    case R.id.naigation_item_2:
                        Toast.makeText(MainActivity.this, "item 2",Toast.LENGTH_SHORT).show();break;
                    default:
                        return true;
                }

                return true;
            }
        });
    }

}
