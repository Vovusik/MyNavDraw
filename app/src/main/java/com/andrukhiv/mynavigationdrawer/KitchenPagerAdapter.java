package com.andrukhiv.mynavigationdrawer;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.andrukhiv.mynavigationdrawer.fragments.FragmentVermut;
import com.andrukhiv.mynavigationdrawer.fragments.FragmentWine;
import com.andrukhiv.mynavigationdrawer.fragments.FragmentWineWhite;

public class KitchenPagerAdapter extends FragmentPagerAdapter {

    private int allPages;


    public KitchenPagerAdapter(FragmentManager fm, int allPages) {
        super(fm);
        this.allPages = allPages;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentVermut();
            case 1:
                return new FragmentWine();
            case 2:
                return new FragmentWineWhite();
//            case 3:
//                return new FragmentKagor();
//            case 4:
//                return new FragmentWineSuhe();
//            case 5:
//                return new FragmentIzabella();
//            case 6:
//                return new FragmentGlintvein();
//            case 7:
//                return new FragmentGrappa();
//            case 8:
//                return new FragmentKizlyarka();
//            case 9:
//                return new FragmentKonyak();
//            case 10:
//                return new FragmentLiker();
//            case 11:
//                return new FragmentMartini();
//            case 12:
//                return new FragmentOruho();
//            case 13:
//                return new FragmentPisko();
//            case 14:
//                return new FragmentPortvein();
//            case 15:
//                return new FragmentSangriya();
//            case 16:
//                return new FragmentJuice();
//            case 17:
//                return new FragmentChacha();
//            case 18:
//                return new FragmentShampanske();
//            case 19:
//                return new FragmentIzum();
//            case 20:
//                return new FragmentChurchhela();
//            case 21:
//                return new FragmentOtset();
//            case 22:
//                return new FragmentBochka();
//            case 23:
//                return new FragmentKislotnist();
//            case 24:
//                return new FragmentOsvitlennya();
//            case 25:
//                return new FragmentGirkota();
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return allPages;
    }
}