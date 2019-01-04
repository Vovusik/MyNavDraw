package com.andrukhiv.mynavigationdrawer.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andrukhiv.mynavigationdrawer.DividerItemDecoration;
import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.activity.KitchenDetailsActivity;
import com.andrukhiv.mynavigationdrawer.adapters.KitchenRecyclerAdapter;
import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.models.KitchenModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class KitchenFragment extends Fragment implements android.support.v7.widget.SearchView.OnQueryTextListener {

    public KitchenFragment() {
    }

    DbAdapter mDbHelper;

    protected static final String TAG = "TableGrapesFragment";

    RecyclerView mRecyclerView;
    private KitchenRecyclerAdapter mAdapter;
    private ArrayList<KitchenModel> kitchenModels;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.kitchen_content_main, container, false);
        setHasOptionsMenu(true);
        mDbHelper = DbAdapter.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext());

        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        kitchenModels = mDbHelper.getKitchen();
        mAdapter = new KitchenRecyclerAdapter(kitchenModels);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), android.support.v7.widget.DividerItemDecoration.VERTICAL, 0));
        return mRecyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((KitchenRecyclerAdapter) mAdapter).setOnItemClickListener(new KitchenRecyclerAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(TAG, " Clicked on Item " + kitchenModels.get(position));
                Intent intent = new Intent(getActivity().getApplicationContext(), KitchenDetailsActivity.class);
                intent.putExtra(KitchenDetailsActivity.EXTRA_KITCHEN_ID, kitchenModels.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        ImageView searchIcon = searchView.findViewById(R.id.search_button);
        searchIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_search_icon));

        ImageView closeIcon = searchView.findViewById(R.id.search_close_btn);
        closeIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_close_icon));

        // встановлюю ширину вікна пошуку на весь екран
        searchView.setMaxWidth(Integer.MAX_VALUE);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<KitchenModel> filteredModelList = filter(kitchenModels, query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<KitchenModel> filter(List<KitchenModel> models, String query) {
        query = query.toLowerCase();

        final List<KitchenModel> filteredModelList = new ArrayList<>();
        for (KitchenModel model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
