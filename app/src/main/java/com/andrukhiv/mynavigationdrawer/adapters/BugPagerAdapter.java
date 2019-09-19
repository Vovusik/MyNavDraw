package com.andrukhiv.mynavigationdrawer.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.andrukhiv.mynavigationdrawer.models.BugModel;
import com.andrukhiv.mynavigationdrawer.fragments.BugPagerFragment;

import java.util.ArrayList;
import java.util.List;

public class BugPagerAdapter extends FragmentStatePagerAdapter {

    private List<BugModel> bugModels;
    private ArrayList<String> BUGLIST = new ArrayList<>();

    public BugPagerAdapter(FragmentManager fragmentManager, List<BugModel> bugModels) {
        super(fragmentManager);
        this.bugModels = bugModels;

        for (int i=0; i<bugModels.size(); i++){

            BUGLIST.add(bugModels.get(i).getImageId());
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return BugPagerFragment.newInstance(BUGLIST.get(position));
    }

    @Override
    public int getCount() {
        return bugModels.size();
    }
}