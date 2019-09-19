package com.andrukhiv.mynavigationdrawer.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.andrukhiv.mynavigationdrawer.fragments.BudovaGuyot;
import com.andrukhiv.mynavigationdrawer.fragments.BudovaPosadka;
import com.andrukhiv.mynavigationdrawer.fragments.Budova;

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
                return new Budova();
            case 1:
                return new BudovaPosadka();
            case 2:
                return new BudovaGuyot();
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
