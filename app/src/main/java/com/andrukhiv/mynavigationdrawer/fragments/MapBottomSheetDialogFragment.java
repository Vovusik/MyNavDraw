package com.andrukhiv.mynavigationdrawer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.adapters.MapsAdapterTrans;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import jp.wasabeef.glide.transformations.BlurTransformation;


public class MapBottomSheetDialogFragment extends BottomSheetDialogFragment implements AppBarLayout.OnOffsetChangedListener {


    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;
    private boolean mIsTheTitleContainerVisible = true;
    private LinearLayoutCompat mTitleContainer;
    private FrameLayout mFrameParallax;
    private FrameLayout frameViewPager;
    private int index;
    private TextView mTitle, bottomAddress, bottomCall, bottomWeb, locationDescription;


    public static MapBottomSheetDialogFragment newInstance(int i) {

        MapBottomSheetDialogFragment fragment = new MapBottomSheetDialogFragment();

        Bundle args = new Bundle();
        args.putInt("INDEX", i);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(STYLE_NORMAL, R.style.SheetDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.maps_dialog_bottom_sheet, container, false);


        Bundle args = getArguments();
        if (args != null) index = args.getInt("INDEX", 0);

        bottomAddress = view.findViewById(R.id.bottom_address);
        bottomCall = view.findViewById(R.id.bottom_call);
        bottomWeb = view.findViewById(R.id.bottom_web);
        locationDescription = view.findViewById(R.id.location_description);

        bottomAddress.setText(MapsAdapterTrans.getAddress(index));
        bottomCall.setText(MapsAdapterTrans.getTextPhone(index));
        bottomWeb.setText(MapsAdapterTrans.getTextWeb(index));
        locationDescription.setText(MapsAdapterTrans.getDescripion(index));

        ImageButton closeImageButton = view.findViewById(R.id.bottom_sheet_close);
        closeImageButton.setOnClickListener(v -> {
            MapsFragmentTrans.myBottomSheet.dismiss();
        });

        mTitleContainer = view.findViewById(R.id.main_linearlayout_title);
        mTitle = view.findViewById(R.id.main_textview_title);
        AppBarLayout mAppBarLayout = view.findViewById(R.id.main_appbar);
        ImageView mImageparallax = view.findViewById(R.id.main_imageview_placeholder);
        mFrameParallax = view.findViewById(R.id.main_framelayout_title);
        frameViewPager = view.findViewById(R.id.frame_view_pager);
        mTitle.setText(MapsAdapterTrans.TITLES.get(index));

        mAppBarLayout.addOnOffsetChangedListener(this);
        initParallaxValues();

        Glide
                .with(getContext())
                .load(MapsAdapterTrans.getImage(index))
                .apply(new RequestOptions()
                        .transform(new BlurTransformation(1, 1))
                )
                .thumbnail(0.5f)
                .into(mImageparallax);

        return view;
    }


    private void initParallaxValues() {

        CollapsingToolbarLayout.LayoutParams petDetailsLp =
                (CollapsingToolbarLayout.LayoutParams) frameViewPager.getLayoutParams();

        CollapsingToolbarLayout.LayoutParams petBackgroundLp =
                (CollapsingToolbarLayout.LayoutParams) mFrameParallax.getLayoutParams();

        petDetailsLp.setParallaxMultiplier(0.9f);
        petBackgroundLp.setParallaxMultiplier(0.3f);

        frameViewPager.setLayoutParams(petDetailsLp);
        mFrameParallax.setLayoutParams(petBackgroundLp);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {

        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
    }


    private void handleAlphaOnTitle(float percentage) {

        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {

            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }


    private static void startAlphaAnimation(View v, int visibility) {

        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration((long) MapBottomSheetDialogFragment.ALPHA_ANIMATIONS_DURATION);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
