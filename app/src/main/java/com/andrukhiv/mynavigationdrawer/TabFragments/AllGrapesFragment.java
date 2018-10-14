package com.andrukhiv.mynavigationdrawer.TabFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrukhiv.mynavigationdrawer.DetailActivity;
import com.andrukhiv.mynavigationdrawer.GrapesAdapter;
import com.andrukhiv.mynavigationdrawer.GrapesModel;
import com.andrukhiv.mynavigationdrawer.R;

public class AllGrapesFragment extends Fragment {

    public AllGrapesFragment() {
        // Требуемый пустой публичный конструктор
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tabs, container, false);
        RecyclerView mRecyclerView = view.findViewById(R.id.grapes_recycler);

        // Назви винограду додаються в масив рядків
        String[] grapesNames = new String[GrapesModel.grapes.length];
        for (int i = 0; i < grapesNames.length; i++) {
            grapesNames[i] = GrapesModel.grapes[i].getName();
        }

        int[] grapesImages = new int[GrapesModel.grapes.length];
        for (int i = 0; i < grapesImages.length; i++) {
            grapesImages[i] = GrapesModel.grapes[i].getImageId();
        }

        // Передати масиви адаптера.
        GrapesAdapter mAdapter = new GrapesAdapter(grapesNames, grapesImages);
        mRecyclerView.setAdapter(mAdapter);

        // Щоб картки відображалися в табличному виді, використовуємо об'єкт GridLayoutManager.
        // Кількість колонок в сітці залежить від розміру і орієнтації екрана
        int numColumns = getResources().getInteger(R.integer.search_results_columns);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), numColumns);
        mRecyclerView.setLayoutManager(layoutManager);

        // Реалізація методу onClick () інтерфейсу Listener запускає активність
        // DetaiGrapeslActivity, передаючи їй ідентифікатор винограду, обраного користувачем.
        mAdapter.setListener(new GrapesAdapter.Listener() {
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_GRAPESNO, position);
                getActivity().startActivity(intent);
            }
        });
        return mRecyclerView;
    }

}