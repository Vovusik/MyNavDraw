package com.andrukhiv.mynavigationdrawer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {


    public SlidrConfig mConfig;
    private boolean isActive = false;

    // Добавляємо константу EXTRA_GRAPESNO
    public static final String EXTRA_GRAPESNO = "grapesNo";

    // todo симулятор зміни теми день/ніч
    // за замовчуванням автоматичний вибір теми додатку в залежності від часу доби
    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        CollapsingToolbarLayout mCollapsingToolbar = findViewById(R.id.collapsing_toolbar);

        int grapesNo = (Integer) getIntent().getExtras().get(EXTRA_GRAPESNO);

        // Заповненя назви винограду всередині toolbar
        String toolbarTitle = GrapesModel.grapes[grapesNo].getName();
        mCollapsingToolbar.setTitle(toolbarTitle);// Використовувати дані, отримані з курсора, для заповнення уявлень.

        // Включити кнопку Вверх на панелі дій.
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Ідентифікатор використовується для заповнення TextView і ImageView.
        String grapesDescription = GrapesModel.grapes[grapesNo].getDescription();
        TextView description = findViewById(R.id.description);
        description.setText(grapesDescription);

        String grapesWeb = GrapesModel.grapes[grapesNo].getWeb();
        TextView web = findViewById(R.id.web);
        web.setText(grapesWeb);
        web.setOnClickListener(this);

        int grapesImage = GrapesModel.grapes[grapesNo].getImageId();
        ImageView imageView = findViewById(R.id.grapes_image);
        imageView.setImageDrawable(getResources().getDrawable(grapesImage));
        imageView.setContentDescription(grapesDescription);

        // Добавлення слайду для відміни Activity
        mConfig = new SlidrConfig.Builder()
                .position(SlidrPosition.TOP)
                .sensitivity(0.5f)
                .build();
        Slidr.attach(this, mConfig);

        // Добавляю анімацію кнопки FAB
        @ColorInt final int colorActive = ContextCompat.getColor(this, android.R.color.white);
        @ColorInt final int colorPassive = ContextCompat.getColor(this, R.color.colorAccent);
        final FloatingActionButton mFab = findViewById(R.id.floatingActionButton);

        final float from = 1.0f;
        final float to = 1.2f; //Збільшую FAB

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mFab, View.SCALE_X, from, to);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mFab, View.SCALE_Y, from, to);
        ObjectAnimator translationZ = ObjectAnimator.ofFloat(mFab, View.TRANSLATION_Z, from, to);

        AnimatorSet set1 = new AnimatorSet();
        set1.playTogether(scaleX, scaleY, translationZ);
        set1.setDuration(100);
        set1.setInterpolator(new AccelerateInterpolator());

        set1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mFab.setImageResource(isActive ? R.drawable.ic_favorite_passive : R.drawable.ic_favorite_active);
                mFab.setBackgroundTintList(ColorStateList.valueOf(isActive ? colorPassive : colorActive));
                isActive = !isActive;
            }
        });

        ObjectAnimator scaleXBack = ObjectAnimator.ofFloat(mFab, View.SCALE_X, to, from);
        ObjectAnimator scaleYBack = ObjectAnimator.ofFloat(mFab, View.SCALE_Y, to, from);
        ObjectAnimator translationZBack = ObjectAnimator.ofFloat(mFab, View.TRANSLATION_Z, to, from);

        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.lineTo(0.5f, 1.3f);
        path.lineTo(0.75f, 0.8f);
        path.lineTo(1.0f, 1.0f);
        PathInterpolator pathInterpolator = new PathInterpolator(path);

        AnimatorSet set2 = new AnimatorSet();
        set2.playTogether(scaleXBack, scaleYBack, translationZBack);
        set2.setDuration(300);
        set2.setInterpolator(pathInterpolator);

        final AnimatorSet set = new AnimatorSet();
        set.playSequentially(set1, set2);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mFab.setClickable(true);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mFab.setClickable(false);
            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isActive) {
                    Snackbar.make(v, "Видалено з улюблених", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(v, "Добавлено до улюблених", Snackbar.LENGTH_LONG).show();
                }

                set.start();
            }
        });

        //loadBackdrop();
    }

//    private void loadBackdrop() {
//        final ImageView image_scrolling_top = findViewById(R.id.image_scrolling_top);
//        Glide.with(this).load(R.drawable.material_design_3).apply(new RequestOptions().fitCenter()).into(image_scrolling_top);
//
//    }

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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.web:
                openCustomTab();
                break;
        }
    }

    private void openCustomTab() {

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

        // установить цвета панели инструментов
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        builder.setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        // поширити додаток
        builder.addDefaultShareMenuItem();
        // показувати заголовок
        builder.setShowTitle(true);
        // змінюю хрестик на стрілочку повернення додому
        builder.setCloseButtonIcon(BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_arrow_back));

        // настроить анимацию начала и выхода
        builder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left);
        builder.setExitAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        CustomTabsIntent customTabsIntent = builder.build();

        int grapesNo = (Integer) getIntent().getExtras().get(EXTRA_GRAPESNO);

        customTabsIntent.launchUrl(this, Uri.parse(GrapesModel.grapes[grapesNo].getWeb()));
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