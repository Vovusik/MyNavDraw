package com.andrukhiv.mynavigationdrawer.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.andrukhiv.mynavigationdrawer.adapters.FormationPagerAdapter;
import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.models.FormationModel;

import java.util.ArrayList;
import java.util.Objects;

public class FormationActivity extends AppCompatActivity {

    DbAdapter mDbHelper;
    ArrayList<FormationModel> mStructureFormation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);

        mDbHelper = DbAdapter.getInstance(Objects.requireNonNull(this).getApplicationContext());
        mStructureFormation = mDbHelper.getFormation();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_up_arrow_icon);
        }

        setUi();
    }

    public void setUi() {
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_formuvannya_budova));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_formuvannya_posadka));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_formuvannya_guyot));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_formuvannya_stelag));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_formuvannya_viyalo));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_formuvannya_cordon));

        final ViewPager viewPager = findViewById(R.id.tabViewpager);
        FormationPagerAdapter tabAdapter = new FormationPagerAdapter(getSupportFragmentManager(), 3);

        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
