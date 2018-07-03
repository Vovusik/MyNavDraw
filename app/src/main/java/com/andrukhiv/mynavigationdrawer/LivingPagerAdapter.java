package com.andrukhiv.mynavigationdrawer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.github.saiff35.livingtabs.LivingTabsLayout;

import java.util.Locale;


public class LivingPagerAdapter extends android.support.v4.app.FragmentPagerAdapter implements LivingTabsLayout.DrawableResIconAdapter{

    public LivingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "Усі сорти";
            case 1:
                return "Столові";
            case 2:
                return "Киш-миш";
            case 3:
                return "Технічні";
        }
        return null;
    }

    @Override
    public int getIcon(int position) {
        switch (position) {
            case 0:
                return R.drawable.tab_icon_home_true;
            case 1:
                return R.drawable.tab_icon_table_true;
            case 2:
                return R.drawable.tab_icon_seedless_true;
            case 3:
                return R.drawable.tab_icon_wine_true;
        }
        return -1;
    }

}












