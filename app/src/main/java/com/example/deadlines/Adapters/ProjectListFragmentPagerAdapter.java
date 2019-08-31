package com.example.deadlines.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.deadlines.Fragments.CurrentProjectsFragment;
import com.example.deadlines.Fragments.UpcomingProjectsFragment;

public class ProjectListFragmentPagerAdapter extends FragmentPagerAdapter {

    public ProjectListFragmentPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        //here will be a switch statement which will cook the fragments we will display
        switch (position) {
            case 0:
                return new CurrentProjectsFragment();
            case 1:
                return new UpcomingProjectsFragment();

            //TODO setup the default case
        }
        return new CurrentProjectsFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "CURRENT ";
            case 1:
                return "UPCOMING";

            //TODO setup the default case
        }
        return "    -----";
    }
}
