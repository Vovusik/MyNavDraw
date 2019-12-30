package com.andrukhiv.mynavigationdrawer.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.RegionMapView;

public class RegionActivity extends AppCompatActivity {

    private TextView mapName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_ic_up_arrow);// замените своим пользовательским значком
        }

        initView();
    }


    @SuppressLint("SetTextI18n")
    private void initView() {
        RegionMapView mapView = findViewById(R.id.mapView);
        mapName = findViewById(R.id.address);
        mapView.setMapRes(R.raw.regions);
        mapView.loadMap();

        mapView.setOnMapItemListener(item -> mapName.setText(item.name + " - " + item.temperature + "°C"));
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

