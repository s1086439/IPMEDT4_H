package com.example.ipmedt41617.ipmedt4_h;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ipmedt41617.ipmedt4_h.Fragments.OefeningenFragment;
import com.example.ipmedt41617.ipmedt4_h.Fragments.ProfielFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private int fragmentCount;

    public SectionsPagerAdapter(FragmentManager fm, int fragmentCount) {
        super(fm);
        this.fragmentCount = fragmentCount + 1;
    }

    @Override
    public int getCount() {
        return fragmentCount;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new ProfielFragment();
        } else {
            OefeningenFragment oefeningFragment = new OefeningenFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            oefeningFragment.setArguments(args);
            return oefeningFragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "profiel";
        } else {
            return "week " + position;
        }
    }
}
