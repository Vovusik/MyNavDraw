package com.andrukhiv.mynavigationdrawer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andrukhiv.mynavigationdrawer.models.SpecificationsModel;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class VarietiesDetailsActivity extends AppCompatActivity {

    private SpecificationsModel mGrapes;
    public SlidrConfig mConfig;
    private boolean isActive = false;

    // Добавляємо константу EXTRA_GRAPES_ID
    public static final String EXTRA_GRAPES_ID = "grapesId";


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        mGrapes = (SpecificationsModel) getIntent().getSerializableExtra(EXTRA_GRAPES_ID);

        // Включити кнопку Вверх на панелі дій.
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_up_arrow_icon);// замените своим пользовательским значком
        }

        CollapsingToolbarLayout mCollapsingToolbar = findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbar.setTitle(mGrapes.getName());

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        ImageView photoLarge = findViewById(R.id.photo_large);
       // Picasso.get().load(mGrapes.getPhotoLarge()).error(R.drawable.oops).into(photoLarge);
        Glide
                .with(this)
                .load(mGrapes.getPhotoLarge())
                .apply(new RequestOptions()
//                        .placeholder(R.drawable.placeholder)
//                        .fallback(R.drawable.ic_520016)
//                        .error(R.drawable.oops)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(GenericTransitionOptions.with(animationObject))
                .into(photoLarge);

        TextView textDescription = findViewById(R.id.text_description);
        textDescription.setText(mGrapes.getDescription());

        TextView textLink = findViewById(R.id.text_link);
        textLink.setText(mGrapes.getLink());
        View.OnClickListener clickLink = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomTab();
            }
        };
        textLink.setOnClickListener(clickLink);

//        CheckBox favorite = findViewById(R.id.check_favorite);
//        favorite.setChecked(isFavorite);

        // Добавлення слайду для відміни Activity
        slidrConfig();

        // Добавляю анімацію кнопки FAB
        fabAnimation();
    }


    private void fabAnimation() {
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
                getResources(), R.drawable.ic_up_arrow_icon));

        // настроить анимацию начала и выхода
        builder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left);
        builder.setExitAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(mGrapes.getLink()));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Анімація завантаження картинки
    public ViewPropertyTransition.Animator animationObject = new ViewPropertyTransition.Animator() {
        @Override
        public void animate(View view) {
            view.setAlpha(0f);

            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
            fadeAnim.setDuration(2500);
            fadeAnim.start();
        }
    };
}