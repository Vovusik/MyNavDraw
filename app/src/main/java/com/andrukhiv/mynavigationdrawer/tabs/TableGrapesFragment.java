package com.andrukhiv.mynavigationdrawer.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.RecyclerViewAdapter;
import com.andrukhiv.mynavigationdrawer.RecyclerViewAdapter2;
import com.andrukhiv.mynavigationdrawer.VarietiesDetailsActivity;
import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.models.VarietiesModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TableGrapesFragment extends Fragment implements android.support.v7.widget.SearchView.OnQueryTextListener {

    // Требуемый пустой публичный конструктор
    public TableGrapesFragment() {
    }

    DbAdapter mDbHelper;

    protected static final String TAG = "TableGrapesFragment";

    RecyclerView mRecyclerView;
    private RecyclerViewAdapter2 mAdapter;
    private ArrayList<VarietiesModel> grapes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.fragment_tabs, container, false);
        setHasOptionsMenu(true);
        mDbHelper = DbAdapter.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext());

        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        grapes = mDbHelper.getGrapes();
        mAdapter = new RecyclerViewAdapter2(grapes);
        mRecyclerView.setAdapter(mAdapter);

        return mRecyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((RecyclerViewAdapter2) mAdapter).setOnItemClickListener(new RecyclerViewAdapter2.MyClickListener() {
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
        inflater.inflate(R.menu.menu_tab_2, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<VarietiesModel> filteredModelList = filter(grapes, query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private List<VarietiesModel> filter(List<VarietiesModel> models, String query) {
        query = query.toLowerCase();

        final List<VarietiesModel> filteredModelList = new ArrayList<>();
        for (VarietiesModel model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

}
