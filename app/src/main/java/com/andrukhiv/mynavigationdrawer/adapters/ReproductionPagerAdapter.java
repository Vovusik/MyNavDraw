package com.andrukhiv.mynavigationdrawer.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.andrukhiv.mynavigationdrawer.fragments.ReproductionChubuk;
import com.andrukhiv.mynavigationdrawer.fragments.ReproductionDesktopVaccine;

public class ReproductionPagerAdapter extends FragmentPagerAdapter {

    private int allPages;


    public ReproductionPagerAdapter(FragmentManager fm, int allPages) {
        super(fm);
        this.allPages = allPages;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ReproductionChubuk();
            case 1:
                return new ReproductionDesktopVaccine();
//            case 2:
//                return new FragmentWineWhite();
//            case 3:
//                return new FragmentKagor();
//            case 4:
//                return new FragmentWineSuhe();
//            case 5:
//                return new FragmentIzabella();
//            case 6:
//                return new FragmentGlintvein();

            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return allPages;
    }
}