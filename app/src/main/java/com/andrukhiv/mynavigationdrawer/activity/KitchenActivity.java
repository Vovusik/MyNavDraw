package com.andrukhiv.mynavigationdrawer.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.fragments.KitchenFragment;

public class KitchenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();

            KitchenFragment myFragment = new KitchenFragment();
            fragmentTransaction.add(R.id.container, myFragment);
            fragmentTransaction.commit();
        }
    }
}
