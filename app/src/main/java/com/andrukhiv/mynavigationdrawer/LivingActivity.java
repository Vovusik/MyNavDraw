package com.andrukhiv.mynavigationdrawer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kobakei.ratethisapp.RateThisApp;


public class LivingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final static String APP_PACKAGE_NAME = "com.eajy.materialdesigndemo";

    private DrawerLayout mDrawer;
    private FloatingActionButton mFab;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new LivingPagerAdapter(getSupportFragmentManager()));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // діалогове вікно «Оцінити цей додаток» - не слухається
        //AppRating.app_launched(this);


        // Підключення бібліотеки появи діалогового вікна «Оцінити цей додаток» - RateThisApp

        // Отслеживать время запуска и интервал от установки
        RateThisApp.onCreate(this);
        RateThisApp.Config config = new RateThisApp.Config();
        // Вказання  URL на сторінку програми в Google Play
        config.setUrl("market://details?id=" + APP_PACKAGE_NAME);
        RateThisApp.init(config);
        // Если условие выполнено, будет показано диалоговое окно «Оценить это приложение»
        RateThisApp.showRateDialogIfNeeded(this, R.style.MyAlertDialogStyle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    // todo - тимчасово - потім заміню на пошук
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();

        if (item.getItemId() == R.id.material_tabs) {
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.living_tabs) {
            intent.setClass(this, LivingActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.theme) {
            int uiMode = getResources().getConfiguration().uiMode;
            int dayNightUiMode = uiMode & Configuration.UI_MODE_NIGHT_MASK;

            switch (dayNightUiMode) {
                case Configuration.UI_MODE_NIGHT_NO: {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    recreate();
                    return true;
                }
                case Configuration.UI_MODE_NIGHT_YES: {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    recreate();
                    return true;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent = new Intent();

        switch (item.getItemId()) {
            case R.id.nav_home:
//                intent.setClass(this, LivingActivity.class);
//                startActivity(intent);
                break;

            case R.id.nav_email:
//                intent.setClass(this, ScrollingActivity.class);
//                startActivity(intent);
                break;

            case R.id.nav_sun:
//                intent.setClass(this, BottomNavigationActivity.class);
//                startActivity(intent);
                break;

            case R.id.nav_rozmnogennya:
//                intent.setClass(this, SettingsActivity.class);
//                startActivity(intent);
                break;

            case R.id.nav_bug:
//                intent.setClass(this, AboutActivity.class);
//                startActivity(intent);
                break;

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // todo - не зберіга стан табів при зміні орієнтації екрану
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        // Поява діалогового вікна для підтвердження виходу з додатку
        new AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                .setIcon(R.drawable.dialog_icon_logo)
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Ви хочете вийти ?")
                .setMessage("Ви впевнені, що хочете вийти з цього додатку ?")
                .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Ні", null)
                .show();

//        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
//            mDrawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }

    }
}

