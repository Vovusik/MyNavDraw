package com.andrukhiv.mynavigationdrawer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.andrukhiv.mynavigationdrawer.fragments.GuyotFragment;
import com.andrukhiv.mynavigationdrawer.fragments.PlantingFragment;
import com.andrukhiv.mynavigationdrawer.fragments.StructureFragment;

public class FormationPagerAdapter extends FragmentPagerAdapter {

    private int allPagers;

    public FormationPagerAdapter(FragmentManager fm, int allPagers) {
        super(fm);
        this.allPagers = allPagers;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new StructureFragment();
            case 1:
                return new PlantingFragment();
            case 2:
                return new GuyotFragment();
//            case 3:
//                return new StelagFragment();
//            case 4:
//                return new ViyaloFragment();
//            case 5:
//                return new CordonFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return allPagers;
    }

}
