package com.andrukhiv.mynavigationdrawer.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.andrukhiv.mynavigationdrawer.AppRating;
import com.andrukhiv.mynavigationdrawer.Constant;
import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.adapters.MainPagerAdapter;
import com.bumptech.glide.Glide;

import static com.andrukhiv.mynavigationdrawer.Constant.APP_PACKAGE_NAME;
import static com.andrukhiv.mynavigationdrawer.Constant.GOOGLE_PLAY_MARKET_ANDROID;
import static com.andrukhiv.mynavigationdrawer.Constant.GOOGLE_PLAY_MARKET_WEB;
import static com.andrukhiv.mynavigationdrawer.Constant.URL_NAV_HEADER;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private Intent intent;
    private ImageView imgNavHeaderBg;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Запуск SplashScreen
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeader = navigationView.getHeaderView(0);
        imgNavHeaderBg = navHeader.findViewById(R.id.img_header_bg);

        setTab();

        // завантажити фонового зображення навігаціонного меню
        loadNavHeader();

        // todo - діалогове вікно «Оцінити цей додаток» - не слухається
        AppRating.app_launched(this);

        // Підключення бібліотеки появи діалогового вікна «Оцінити цей додаток» - RateThisApp

        // Отслеживать время запуска и интервал от установки
//        RateThisApp.onCreate(this);
//        RateThisApp.Config config = new RateThisApp.Config();
//        // Вказання  URL на сторінку програми в Google Play
//        config.setUrl("market://details?id=" + APP_PACKAGE_NAME);
//        RateThisApp.init(config);
//        // Если условие выполнено, будет показано диалоговое окно «Оценить это приложение»
//        RateThisApp.showRateDialogIfNeeded(this, R.style.MyAlertDialogStyle);
    }

    private void loadNavHeader() {
        Glide.with(this).load(URL_NAV_HEADER).into(imgNavHeaderBg);
    }


    public void setTab() {
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_item_home).setIcon(R.drawable.tab_selector_home));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_item_table).setIcon(R.drawable.tab_selector_table));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_item_seedless).setIcon(R.drawable.tab_selector_seedless));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_item_wine).setIcon(R.drawable.tab_selector_wine));

        final ViewPager viewPager = findViewById(R.id.tabViewpager);
        MainPagerAdapter tabAdapter = new MainPagerAdapter(getSupportFragmentManager(), 4);

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
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_home:
                break;

            case R.id.nav_alkcokitchen:
                intent = new Intent(MainActivity.this, KitchenActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_gallery:
                intent = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_reproduction:
                intent = new Intent(MainActivity.this, ReproductionActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_formuvannya:
                intent = new Intent(MainActivity.this, BudovaActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_region:
                intent = new Intent(MainActivity.this, RegionActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_library:
                intent = new Intent(MainActivity.this, LibraryActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_filter:
                intent = new Intent(MainActivity.this, SortableActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_settings:
                intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_marker:
                intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_bug:
                intent = new Intent(MainActivity.this, BugActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_star:
                rate();
                break;

            case R.id.nav_share:
                share();
                break;

            case R.id.nav_email:
                email();
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void rate() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(GOOGLE_PLAY_MARKET_ANDROID + APP_PACKAGE_NAME)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(GOOGLE_PLAY_MARKET_WEB + APP_PACKAGE_NAME + "&hl")));
        }
    }


    public void share() {
        intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, Constant.SHARE_CONTENT);
        intent.setType("text/plain");
        startActivity(intent);
    }


    public void email() {
        String to = Constant.EMAIL;// Адресат повідомлення
        String subject = getString(R.string.message_subject); // Тема повідомлення
        String body = getString(R.string.message_text); // Текст повідомлення

        intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        String[] toArr = new String[]{to};
        intent.putExtra(Intent.EXTRA_EMAIL, toArr);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        // Поява діалогового вікна для підтвердження виходу з додатку
        new AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                //.setIcon(R.drawable.dialog_icon_logo)
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
    }
}
