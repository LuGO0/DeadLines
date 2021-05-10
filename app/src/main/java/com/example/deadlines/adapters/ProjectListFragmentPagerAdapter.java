package com.example.deadlines.adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.deadlines.views.fragments.CurrentProjectsFragment;
import com.example.deadlines.views.fragments.UpcomingProjectsFragment;

public class ProjectListFragmentPagerAdapter extends FragmentPagerAdapter {
    public ProjectListFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CurrentProjectsFragment();
            case 1:
                return new UpcomingProjectsFragment();
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
        }
        return "NA";
    }
}
