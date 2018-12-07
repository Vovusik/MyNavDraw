package com.andrukhiv.mynavigationdrawer.tabs;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import android.support.v7.widget.SearchView;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andrukhiv.mynavigationdrawer.RecyclerViewAdapter;
import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.models.SpecificationsModel;
import com.andrukhiv.mynavigationdrawer.VarietiesDetailsActivity;
import com.andrukhiv.mynavigationdrawer.R;

import java.util.Objects;

public class AllGrapesFragment extends Fragment {

    // Требуемый пустой публичный конструктор
    public AllGrapesFragment() {
    }

    DbAdapter mDbHelper;

    protected static final String TAG = "AllGrapesFragment";

    RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private ArrayList<SpecificationsModel> grapes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.fragment_recycler_tabs, container, false);
        setHasOptionsMenu(true);
        mDbHelper = DbAdapter.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext());

        // Щоб картки відображалися в табличному виді, використовуємо об'єкт GridLayoutManager.
        // Кількість колонок в сітці залежить від розміру і орієнтації екрана
        int numColumns = getResources().getInteger(R.integer.search_results_columns);
        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), numColumns);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        grapes = mDbHelper.getAllGrapes();

        // Передати масиви адаптера.
        mAdapter = new RecyclerViewAdapter(grapes);
        mRecyclerView.setAdapter(mAdapter);

        return mRecyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((RecyclerViewAdapter) mAdapter).setOnItemClickListener(new RecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(TAG, " Clicked on Item " + grapes.get(position));
                Intent intent = new Intent(getActivity().getApplicationContext(), VarietiesDetailsActivity.class);
                intent.putExtra(VarietiesDetailsActivity.EXTRA_GRAPES_ID, grapes.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);

        final MenuItem item = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }
        });

        ImageView searchIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        searchIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_search_icon));

        ImageView closeIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        closeIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_close_icon));

        // встановлюю ширину вікна пошуку на весь екран
        searchView.setMaxWidth(Integer.MAX_VALUE);

        super.onCreateOptionsMenu(menu, inflater);
    }


//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
//
//        ImageView searchIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
//        searchIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_search_icon));
//
//        ImageView closeIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
//        closeIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_close_icon));
//
//        // встановлюю ширину вікна пошуку на весь екран
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//
//        super.onPrepareOptionsMenu(menu);
//    }

}




