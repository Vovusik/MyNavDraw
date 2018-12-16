package com.andrukhiv.mynavigationdrawer.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.andrukhiv.mynavigationdrawer.adapters.KitchenPagerAdapter;
import com.andrukhiv.mynavigationdrawer.R;

public class KitchenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

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
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_vermut));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_wine));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_wine_white));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_kagor));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_wine_suhe));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_izabella));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_glintvein));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_grappa));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_kizlyarka));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_konyak));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_liker));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_martini));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_oruho));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_pisko));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_portvein));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_sangriya));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_juice));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_chacha));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_shampanske));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_izum));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_churchhela));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_otset));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_bochka));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_kislotnist));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_osvitlennya));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_kitchen_girkota));

        final ViewPager viewPager = findViewById(R.id.tabViewpager);
        KitchenPagerAdapter tabAdapter = new KitchenPagerAdapter(getSupportFragmentManager(), 3);//Total - 26

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