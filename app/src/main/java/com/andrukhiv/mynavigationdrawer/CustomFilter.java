package com.andrukhiv.mynavigationdrawer;

import android.widget.Filter;
import com.andrukhiv.mynavigationdrawer.models.VarietiesModel;
import java.util.ArrayList;

public class CustomFilter extends Filter {

    private RecyclerViewAdapter mAdapter;
    private ArrayList<VarietiesModel> mFilterList;


    CustomFilter(ArrayList<VarietiesModel> filterList, RecyclerViewAdapter adapter) {
        this.mAdapter = adapter;
        this.mFilterList = filterList;
    }


    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<VarietiesModel> filteredPlayers = new ArrayList<>();
            for (int i = 0; i < mFilterList.size(); i++) {
                if (mFilterList.get(i).getName().toUpperCase().contains(constraint)) {
                    filteredPlayers.add(mFilterList.get(i));
                }
            }
            results.count = filteredPlayers.size();
            results.values = filteredPlayers;
        } else {
            results.count = mFilterList.size();
            results.values = mFilterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        mAdapter.mDataset = (ArrayList<VarietiesModel>) results.values;
        //REFRESH
        mAdapter.notifyDataSetChanged();
    }
}