package com.example.ipmedt41617.ipmedt4_h;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ipmedt41617.ipmedt4_h.Fragments.OefeningFragment;
import com.example.ipmedt41617.ipmedt4_h.Fragments.WelkomFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 5;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new OefeningFragment();
            case 1:
                return new OefeningFragment();
            case 2:
                return new OefeningFragment();
            case 3:
                return new OefeningFragment();
            case 4:
                return new OefeningFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Week 1";
            case 1:
                return "Week 2";
            case 2:
                return "Week 3";
            case 3:
                return "Week 4";
            case 4:
                return "Week 5";
        }
        return null;
    }
}
