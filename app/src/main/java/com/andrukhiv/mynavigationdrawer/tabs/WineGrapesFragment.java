package com.andrukhiv.mynavigationdrawer.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.adapters.RecyclerAdapter;
import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.models.SpecificationsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class WineGrapesFragment extends Fragment  implements SearchView.OnQueryTextListener {

    private RecyclerAdapter mAdapter;
    protected static final String TAG = "AllGrapesFragment";
    private ArrayList<SpecificationsModel> grapes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_recycler_tabs, container, false);
        setHasOptionsMenu(true);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(Objects.requireNonNull(view), savedInstanceState);

        int numColumns = getResources().getInteger(R.integer.search_results_columns);
        RecyclerView mRecyclerView = view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), numColumns);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        grapes = new ArrayList<>();

        //grapes = SpecificationsModel.getGrapes();
        grapes = DbAdapter.getWineGrapes();
        mAdapter = new RecyclerAdapter(getActivity(), grapes);

        mRecyclerView.setAdapter(mAdapter);


    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);

        final MenuItem item = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        searchView.setQueryHint("Пошук сорту винограда");


        item.setOnActionExpandListener( new MenuItem.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mAdapter.setItems(grapes);
                return true;
            }
        });

        ImageView searchIcon = searchView.findViewById(R.id.search_button);
        searchIcon.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.menu_ic_search));

        ImageView closeIcon = searchView.findViewById(R.id.search_close_btn);
        closeIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.toolbar_ic_close));

        // встановлюю ширину вікна пошуку на весь екран
        searchView.setMaxWidth(Integer.MAX_VALUE);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {

//        final List<Data> filteredModelList = filter(dabListItem, query);
//        mAdapter.setItems(filteredModelList);
//        mAdapter.notifyDataSetChanged();
//        mRecyclerView.scrollToPosition(0);

        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        final List<SpecificationsModel> filteredModelList = filter(grapes, newText);
        mAdapter.setItems(filteredModelList);
        return true;
    }


    private List<SpecificationsModel> filter(List<SpecificationsModel> datas, String newText) {
        newText = newText.toLowerCase().trim();

        final List<SpecificationsModel> filteredModelList = new ArrayList<>();
        for (SpecificationsModel data : datas) {

            final String text = data.getName().toLowerCase().trim();
            if (text.contains(newText)) {
                filteredModelList.add(data);
            }
        }

        return filteredModelList;
    }


}