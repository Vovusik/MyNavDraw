package com.andrukhiv.mynavigationdrawer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.andrukhiv.mynavigationdrawer.models.KitchenModel;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.bumptech.glide.signature.ObjectKey;

import jp.wasabeef.glide.transformations.BlurTransformation;


public class KitchenView extends LinearLayout {

    public KitchenView(Context context) {
        super(context);
    }

    public KitchenView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KitchenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        inflate(getContext(), R.layout.kitchen_view, this);
    }


    private ImageView backgroundImage = findViewById(R.id.weather_image);


    public void setKitchen(KitchenModel kitchen) {

        Glide.with(getContext())
                .load(kitchen.getImageBackground())
                .centerCrop()
                .placeholder(R.drawable.kitchen_gradient_placeholder)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(true)
                        .transform(new BlurTransformation(1, 1))
                )
                .thumbnail(0.5f)
                .signature(new ObjectKey(System.currentTimeMillis() / (10 * 60 * 1000)))
                .transition(GenericTransitionOptions.with(animationObject))
                .into(backgroundImage);
        invalidate();


        backgroundImage
                .animate()
                .scaleX(1f)
                .scaleY(1f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(getResources().getInteger(R.integer.anim_duration_medium))
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        backgroundImage.setVisibility(View.VISIBLE);
                    }
                });
    }


    // Анімація завантаження картинки Glide
    private ViewPropertyTransition.Animator animationObject = view -> {
        view.setAlpha(0f);
        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        fadeAnim.setDuration(2500);
        fadeAnim.start();
    };
}
