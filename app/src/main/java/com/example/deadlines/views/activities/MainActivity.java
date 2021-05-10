package com.example.deadlines.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.deadlines.R;
import com.example.deadlines.adapters.ProjectListFragmentPagerAdapter;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private ActionBar actionBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.about_us) {
            Intent intent = new Intent(MainActivity.this, CreditsActivity.class);
            startActivity(intent);
            return true;
        }

        return actionBarDrawerToggle.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new ProjectListFragmentPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        actionBar = getSupportActionBar();

        setupDrawerLayout();
    }

    void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerStateChanged(int newState) {

                super.onDrawerStateChanged(newState);
                boolean isOpened = drawerLayout.isDrawerOpen(navigationView);
                boolean isVisible = drawerLayout.isDrawerVisible(navigationView);

                if (!isOpened && !isVisible) {

                    if (newState == DrawerLayout.STATE_IDLE) {
                        restoreActionBar();
                    } else {
                        overrideActionBar();
                    }
                }
            }

            private void restoreActionBar() {
                getSupportActionBar().setTitle("DeadLines");
                actionBar.show();
                supportInvalidateOptionsMenu();
            }

            private void overrideActionBar() {
                actionBar.hide();
                supportInvalidateOptionsMenu();
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        actionBar.setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.navigation_drawer_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {

                    case R.id.schemes_item:
                        Toast.makeText(MainActivity.this, "SCHEMES", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.scholarship_item:
                        Toast.makeText(MainActivity.this, "SCHOLARSHIP", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.research_proposal_item:
                        Toast.makeText(MainActivity.this, "RESEARCH PROPOSALS", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.profile_item:
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.signOut_item:
                        AuthUI.getInstance().signOut(MainActivity.this);
                        break;

                    default:
                        return true;
                }

                return true;
            }
        });
    }
}
