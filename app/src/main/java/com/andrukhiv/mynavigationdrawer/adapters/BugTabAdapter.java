package com.andrukhiv.mynavigationdrawer.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.andrukhiv.mynavigationdrawer.fragments.BugFragmentMildew;
import com.andrukhiv.mynavigationdrawer.fragments.BugFragmentOidium;

public class BugTabAdapter extends FragmentPagerAdapter {

    private int allPagers;

    public BugTabAdapter(FragmentManager fm, int allPagers) {
        super(fm);
        this.allPagers = allPagers;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BugFragmentMildew();
            case 1:
                return new BugFragmentOidium();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return allPagers;
    }
}
