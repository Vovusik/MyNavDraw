package com.andrukhiv.mynavigationdrawer.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.andrukhiv.mynavigationdrawer.KitchenView;
import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.adapters.KitchenAdapter;
import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.models.KitchenModel;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;

public class KitchenActivity extends AppCompatActivity implements
        DiscreteScrollView.ScrollStateChangeListener<KitchenAdapter.ViewHolder>,
        DiscreteScrollView.OnItemChangedListener<KitchenAdapter.ViewHolder> {

    DbAdapter mDbHelper;
    private List<KitchenModel> recipe;
    private KitchenView recipeView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_ic_up_arrow);
        }

        recipeView = findViewById(R.id.forecast_view);

        mDbHelper = DbAdapter.getInstance(getApplicationContext());
        recipe = DbAdapter.getKitchen();

        //recipe = KitchenData.get().getKitchen();

        DiscreteScrollView glassPicker = findViewById(R.id.forecast_city_picker);
        glassPicker.setSlideOnFling(false);
        glassPicker.setAdapter(new KitchenAdapter(recipe));
        glassPicker.addOnItemChangedListener(this);
        glassPicker.setOrientation(DSVOrientation.HORIZONTAL);
        glassPicker.addScrollStateChangeListener(this);
        glassPicker.scrollToPosition(0);
        glassPicker.setItemTransitionTimeMillis(150);
        glassPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        recipeView.setKitchen(recipe.get(0));
    }


    @Override
    public void onCurrentItemChanged(@Nullable KitchenAdapter.ViewHolder holder, int position) {
        //viewHolder никогда не будет нулевым, потому что мы никогда не удаляем элементы из списка адаптера
        if (holder != null) {
            recipeView.setKitchen(recipe.get(position));
            holder.showText();
        }
    }


    @Override
    public void onScrollStart(@NonNull KitchenAdapter.ViewHolder holder, int position) {
        holder.hideText();
    }


    @Override
    public void onScroll(
            float position,
            int currentIndex, int newIndex,
            @Nullable KitchenAdapter.ViewHolder currentHolder,
            @Nullable KitchenAdapter.ViewHolder newHolder) {
    }


    @Override
    public void onScrollEnd(@NonNull KitchenAdapter.ViewHolder holder, int position) {
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


















