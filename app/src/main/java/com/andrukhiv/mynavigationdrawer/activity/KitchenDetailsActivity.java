package com.andrukhiv.mynavigationdrawer.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.models.KitchenModel;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;
import jp.wasabeef.glide.transformations.BlurTransformation;


public class KitchenDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_KITCHEN_ID = "kitchenId";
    public SlidrConfig mConfig;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_details);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        KitchenModel mGrapes = (KitchenModel) getIntent().getSerializableExtra(EXTRA_KITCHEN_ID);

        // Включити кнопку Вверх на панелі дій.
        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_ic_up_arrow);// замените своим пользовательским значком
        }

        CollapsingToolbarLayout mCollapsingToolbar = findViewById(R.id.collapsing_toolbar);
        assert mGrapes != null;
        mCollapsingToolbar.setTitle(mGrapes.getName());

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        ImageView photoCollapsing = findViewById(R.id.photo_large);
        Glide
                .with(this)
                .load(mGrapes.getImageBackground())
                .apply(new RequestOptions()
                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        //.skipMemoryCache(true)
                        .transform(new BlurTransformation(1, 1))
                )
                .thumbnail(0.5f)
                //.signature(new ObjectKey(System.currentTimeMillis() / (10 * 60 * 1000)))
                .transition(GenericTransitionOptions.with(animationObject))
                .into(photoCollapsing);

        TextView textDescription = findViewById(R.id.text_description);
        textDescription.setText(mGrapes.getDescription());

        TextView textComponents = findViewById(R.id.text_components);
        textComponents.setText(mGrapes.getComponents());

        TextView textRecipe = findViewById(R.id.text_recipe);
        textRecipe.setText(mGrapes.getRecipe());

        // Добавлення слайду для відміни Activity
        slidrConfig();
    }

    private void slidrConfig() {
        mConfig = new SlidrConfig.Builder()
                .position(SlidrPosition.TOP)
                .sensitivity(0.5f)
                .build();
        Slidr.attach(this, mConfig);
    }


    @Override
    protected void onResume() {
        super.onResume();

        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            CollapsingToolbarLayout collapsing_toolbar_layout = findViewById(R.id.collapsing_toolbar);
            collapsing_toolbar_layout.setExpandedTitleTextColor(ColorStateList.valueOf(Color.TRANSPARENT));
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }


    // Анімація завантаження картинки
    public ViewPropertyTransition.Animator animationObject = view -> {
        view.setAlpha(0f);

        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        fadeAnim.setDuration(2500);
        fadeAnim.start();
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}