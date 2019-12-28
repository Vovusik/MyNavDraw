package com.andrukhiv.mynavigationdrawer.activity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;

import com.andrukhiv.mynavigationdrawer.adapters.FormationPagerAdapter;
import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.models.FormationModel;

import java.util.ArrayList;
import java.util.Objects;

public class BudovaActivity extends AppCompatActivity {

    DbAdapter mDbHelper;
    ArrayList<FormationModel> mStructureFormation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budova);

        mDbHelper = DbAdapter.getInstance(Objects.requireNonNull(this).getApplicationContext());
        mStructureFormation = mDbHelper.getFormation();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_ic_up_arrow);
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
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
