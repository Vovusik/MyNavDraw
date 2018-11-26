package com.andrukhiv.mynavigationdrawer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.andrukhiv.mynavigationdrawer.tabs.AllGrapesFragment;
import com.andrukhiv.mynavigationdrawer.tabs.SeedlessGrapesFragment;
import com.andrukhiv.mynavigationdrawer.tabs.TableGrapesFragment;
import com.andrukhiv.mynavigationdrawer.tabs.WineGrapesFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private int allPagers;

    public MainPagerAdapter(FragmentManager fm, int allPagers) {
        super(fm);
        this.allPagers = allPagers;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllGrapesFragment();
            case 1:
                return new TableGrapesFragment();
            case 2:
                return new SeedlessGrapesFragment();
            case 3:
                return new WineGrapesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return allPagers;
    }

}
