package com.andrukhiv.mynavigationdrawer.activity;

import android.animation.ObjectAnimator;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andrukhiv.mynavigationdrawer.AppRating;
import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.adapters.MainPagerAdapter;
import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.andrukhiv.mynavigationdrawer.Constant.URL_NAV_HEADER;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static DrawerLayout mDrawer;
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

        // завантажити фонового зображення навігаційного меню
        loadNavHeader();

        // Підключення бібліотеки появи діалогового вікна «Оцінити цей додаток» - RateThisApp
        // Todo - діалогове вікно «Оцінити цей додаток» - не слухається
        AppRating.app_launched(this);

        createChannel(); // Створюю канал для Notification
    }

    private void loadNavHeader() {
        Glide.with(this)
                .load(URL_NAV_HEADER)
                .apply(new RequestOptions()
                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        //.placeholder(R.drawable.placeholder)
                        //.fallback(R.drawable.ic_520016)
                        //.error(R.drawable.oops)

                        //.skipMemoryCache(true)
                        .transform(new BlurTransformation(1, 1)


                        )
                )
                .thumbnail(0.5f)
                //.signature(new ObjectKey(System.currentTimeMillis() / (10 * 60 * 1000)))
                .transition(GenericTransitionOptions.with(animationObject))
                .into(imgNavHeaderBg);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_home:
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Ви знаходитесь на головній сторінці", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                View view = toast.getView();
                view.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                TextView toastMessage = toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.WHITE);


                toast.show();
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

            case R.id.nav_laboratory:
                intent = new Intent(MainActivity.this, LaboratoryActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_filter:
                intent = new Intent(MainActivity.this, SortableActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_preparaty:
                intent = new Intent(MainActivity.this, PreparatyActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_settingss:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
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
            case R.id.nav_calendar:
                Toast toast_calendar;
                toast_calendar = Toast.makeText(this,
                        "Розділ у розробці", Toast.LENGTH_LONG);
                toast_calendar.setGravity(Gravity.CENTER, 0, 0);
                View viewCalendar = toast_calendar.getView();
                viewCalendar.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                TextView toastCalendar = toast_calendar.getView().findViewById(android.R.id.message);
                toastCalendar.setTextColor(Color.WHITE);
                toast_calendar.show();
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Анімація завантаження картинки Glide
    private ViewPropertyTransition.Animator animationObject = view -> {
        view.setAlpha(0f);
        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        fadeAnim.setDuration(2500);
        fadeAnim.start();
    };

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Создать канал для показа уведомлений.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(new NotificationChannel(
                        channelId,
                        channelName,
                        NotificationManager.IMPORTANCE_LOW));
            }
        }
    }
}