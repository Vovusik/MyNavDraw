package com.andrukhiv.mynavigationdrawer.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.utils.MapsUtils;
import com.andrukhiv.mynavigationdrawer.adapters.MapsAdapterOdesa;
import com.andrukhiv.mynavigationdrawer.adapters.MapsAdapterTrans;
import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.models.MapsModel;
import com.github.nitrico.mapviewpager.MapViewPager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.Objects;

public class MapsActivity extends AppCompatActivity implements MapViewPager.Callback, OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();

    public Toolbar mToolbarActivity;
    private ViewPager viewPager;
    private MapViewPager mvp;
    public Spinner mSpinner;
    private SupportMapFragment mapFragment;

    DbAdapter mDbHelper;
    ArrayList<MapsModel> mapsModels, mapsModelsOdesa;

    public MapsActivity() {
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Прибрав статусбар з опису дегустаційних залів
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.act_maps);
        mToolbarActivity = findViewById(R.id.toolbar_spinner_maps);
        setSupportActionBar(mToolbarActivity);
        // Прибрав назву заголовку з тулбара
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_ic_up_arrow);// замените своим пользовательским значком
        }

        mDbHelper = DbAdapter.getInstance(getApplicationContext());
        mapsModels = DbAdapter.getMaps();
        mapsModelsOdesa = DbAdapter.getMapsOdesa();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        viewPager = findViewById(R.id.viewPager);
        viewPager.setPageMargin(MapsUtils.dp(this, 16));
        MapsUtils.setMargins(viewPager, 0, 0, 0, 0);

        // Добавляю спіннер у тулбар
        mSpinner = findViewById(R.id.spinner);

        spinnerRegionToolbar();
    }

    private void spinnerRegionToolbar() {

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(MapsActivity.this,
                R.layout.item_spinner,
                getResources().getStringArray(R.array.region));
        mAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        mSpinner.setAdapter(mAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        transcarpathianMapViewPager();
                        break;
                    case 1:
                        odesaMapViewPager();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void transcarpathianMapViewPager() {
        mvp = new MapViewPager.Builder(MapsActivity.this)
                .mapFragment(mapFragment)
                .viewPager(viewPager)
                .position(0)
                .adapter(new MapsAdapterTrans(this.getSupportFragmentManager(), mapsModels))
                .callback(MapsActivity.this)
                .build();
    }


    private void odesaMapViewPager() {
        mvp = new MapViewPager.Builder(MapsActivity.this)
                .mapFragment(mapFragment)
                .viewPager(viewPager)
                .position(0)
                .adapter(new MapsAdapterOdesa(this.getSupportFragmentManager(), mapsModelsOdesa))
                .callback(MapsActivity.this)
                .build();
    }


    @Override
    public void onMapViewPagerReady() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mvp.getMap().setPadding(
                    MapsUtils.getNavigationBarWidth(this),
                    MapsUtils.getNavigationBarWidth(this),
                    MapsUtils.getNavigationBarWidth(this),
                    viewPager.getHeight());
            mvp.getMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mvp.getMap().setPadding(
                    MapsUtils.getNavigationBarWidth(this),
                    MapsUtils.getNavigationBarWidth(this),
                    MapsUtils.getNavigationBarWidth(this),
                    MapsUtils.getNavigationBarWidth(this));
            mvp.getMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }

        // Встановлення маркера власного кольору
        mvp.getMarker(1)
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        //.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.toolbar_ic_up_arrow));
        mvp.getMarker(0)
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));

        // Зміна відтінку маркера, який не перебуває у фокусі
        mvp.getViewPager().addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mvp.getMarkers().size(); i++) {
                    Marker marker = mvp.getMarker(i);
                    if (i == position) marker.setAlpha(1f);
                    else marker.setAlpha(0.5f);
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        int uiMode = getResources().getConfiguration().uiMode;
        int dayNightUiMode = uiMode & Configuration.UI_MODE_NIGHT_MASK;

        try {
            if (dayNightUiMode == Configuration.UI_MODE_NIGHT_YES) {
                boolean success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(this, R.raw.style_night));
                if (!success) {
                    Log.e(TAG, "Разбор стиля не удался.");
                }
            } else {
                boolean success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(this, R.raw.style_day));
                if (!success) {
                    Log.e(TAG, "Разбор стиля не удался.");
                }
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Не могу найти стиль. Ошибка:", e);
        }


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
