package com.example.deadlines.Views.Activities;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import com.example.deadlines.Views.Adapters.ProjectListFragmentPagerAdapter;
import com.example.deadlines.R;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView mNavigationView;
    private ActionBar mActionBar;
    private static final String TAG = "MainActivity";
    public static final String ANONYMOUS = "anonymous";
    public static final int RC_SIGN_IN = 1;


    //..
    private String mUsername;

    // Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private TextView usernameView;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()==R.id.about_us)
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


        // Initialize Firebase components
        mUsername = ANONYMOUS;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    onSignedInInitialize(user.getDisplayName());
                } else {
                    // User is signed out
                    onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            new AuthUI.IdpConfig.GoogleBuilder().build()
                                           ))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };


    }

    private void onSignedInInitialize(String username) {
        mUsername = username;
        mFirebaseUser=FirebaseAuth.getInstance().getCurrentUser();
//        if(mFirebaseUser.isEmailVerified())
//        {
//
//        }
//        else
//        {
//
            mFirebaseUser.sendEmailVerification();
//        }
        //attachDatabaseReadListener();
    }

    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
        //mMessageAdapter.clear();
        //detachDatabaseReadListener();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign-in succeeded, set up the UI
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // Sign in was canceled by the user, finish the activity
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    //        mMessageAdapter.clear();
      //  detachDatabaseReadListener();
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

                    case R.id.schemes_item:
                        Toast.makeText(MainActivity.this, "SCHEMES",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.scholarship_item:
                        Toast.makeText(MainActivity.this, "SCHOLARSHIP",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.research_proposal_item:
                        Toast.makeText(MainActivity.this, "RESEARCH PROPOSALS",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.profile_item:
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.signOut_item:
                        AuthUI.getInstance().signOut(MainActivity.this);break;

                    default:
                        return true;
                }

                return true;
            }
        });
        //usernameView=(TextView)findViewById(R.id.username_token);
        //usernameView.setText(mUsername);

    }


}
