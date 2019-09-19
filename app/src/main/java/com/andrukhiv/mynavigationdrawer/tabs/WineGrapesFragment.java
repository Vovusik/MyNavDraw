package com.andrukhiv.mynavigationdrawer.tabs;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrukhiv.mynavigationdrawer.R;


public class WineGrapesFragment extends Fragment {

    public WineGrapesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_tabs, container, false);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.image_cover);
        imageView.setImageResource(R.drawable.tab_icon_wine_true);
        TextView textView = (TextView) rootView.findViewById(R.id.tab);
        textView.setText("перелік технічних сортів винограду");

        return rootView;
    }

}
