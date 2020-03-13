package com.example.deadlines.Views.Adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.deadlines.Fragments.CurrentProjectsFragment;
import com.example.deadlines.Fragments.UpcomingProjectsFragment;

public class ProjectListFragmentPagerAdapter extends FragmentPagerAdapter {

    public ProjectListFragmentPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
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
