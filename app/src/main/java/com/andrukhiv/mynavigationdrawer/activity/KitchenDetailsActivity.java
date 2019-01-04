package com.andrukhiv.mynavigationdrawer.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.models.KitchenModel;


public class KitchenDetailsActivity extends AppCompatActivity {

    private KitchenModel kitchenModel;

    public static final String EXTRA_KITCHEN_ID = "kitchenId";

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_kitchen);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        kitchenModel = (KitchenModel) getIntent().getSerializableExtra(EXTRA_KITCHEN_ID);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(kitchenModel.getName());
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_up_arrow_icon);
        }

        TextView mDescription = findViewById(R.id.text_description);
        mDescription.setText(kitchenModel.getDescription());
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