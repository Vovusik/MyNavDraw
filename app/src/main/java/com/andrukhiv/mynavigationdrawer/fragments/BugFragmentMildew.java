package com.andrukhiv.mynavigationdrawer.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.andrukhiv.mynavigationdrawer.BugParallaxPageTransformer;
import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.adapters.BugPagerAdapter;
import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.database.DbHelper;
import com.andrukhiv.mynavigationdrawer.models.BugModel;
import com.andrukhiv.mynavigationdrawer.tables.BugTable;

import java.util.ArrayList;
import java.util.Objects;

import static com.andrukhiv.mynavigationdrawer.database.DbAdapter.getBugMildew;

public class BugFragmentMildew extends Fragment implements View.OnClickListener {

    ViewPager mViewPager;
    DbAdapter mDbHelper;
    ArrayList<BugModel> bugModels;
    LinearLayout mSliderDotsPanel;
    private int mDotsCount;
    private ImageView[] mDots;
    ImageView mLeftButton, mRightButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bug, container, false);

        mLeftButton = (ImageButton) view.findViewById(R.id.left_nav);
        mLeftButton.setOnClickListener(this);
        mRightButton = (ImageButton) view.findViewById(R.id.right_nav);
        mRightButton.setOnClickListener(this);

        mLeftButton.setVisibility(View.GONE);

        mViewPager = view.findViewById(R.id.viewPager);

        mDbHelper = DbAdapter.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext());
        bugModels = getBugMildew();

        BugPagerAdapter adapter = new BugPagerAdapter(getFragmentManager(), getBugMildew());
        mViewPager.setAdapter(adapter);

        BugParallaxPageTransformer pageTransformer = new BugParallaxPageTransformer();

        BugParallaxPageTransformer.ParallaxTransformInformation transformInformation = new BugParallaxPageTransformer
                .ParallaxTransformInformation(R.id.imageView, 2, 2);
        pageTransformer.addViewToParallax(transformInformation);

        mViewPager.setPageTransformer(true, pageTransformer);

        mSliderDotsPanel = view.findViewById(R.id.SliderDots);
        mDotsCount = adapter.getCount();

        SQLiteOpenHelper bugDbHelper = new DbHelper(getContext());

        try {
            SQLiteDatabase db = bugDbHelper.getReadableDatabase();
            Cursor cursor = db.query(BugTable.BUG_NAME_TABLE,
                    new String[]{
                            BugTable.BUG_COLUMN_IMAGE,
                            BugTable.BUG_COLUMN_DESCRIPTION,
                            BugTable.BUG_COLUMN_CATEGORY,
                            BugTable.BUG_COLUMN_CATEGORY_ID},
                    BugTable.BUG_COLUMN_CATEGORY + "= ?",
                    new String[]{"Мілдью"},
                    null, null, null);

            if (cursor.moveToFirst()) {
                String descriptionText = cursor.getString(1);
                TextView textView = view.findViewById(R.id.textView);
                textView.setText(descriptionText);
            }

            cursor.close();
            db.close();

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getContext(), "База данных недоступна", Toast.LENGTH_SHORT);
            toast.show();
        }

        sliderDot();

        return view;
    }

    private void sliderDot() {

        mDots = new ImageView[mDotsCount];

        for (int i = 0; i < mDotsCount; i++) {

            mDots[i] = new ImageView(getContext());
            mDots[i].setImageDrawable(ContextCompat.getDrawable(getContext(),
                    R.drawable.defaulte_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            mSliderDotsPanel.addView(mDots[i], params);
        }

        mDots[0].setImageDrawable(ContextCompat.getDrawable(getContext(),
                R.drawable.active_dot));

        mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFirstPage()) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
                }
            }
        });

        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLastPage()) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < mDotsCount; i++) {
                    mDots[i].setImageDrawable(ContextCompat.getDrawable(getContext(),
                            R.drawable.defaulte_dot));
                }

                mDots[position].setImageDrawable(ContextCompat.getDrawable(getContext(),
                        R.drawable.active_dot));

                handleVisibility();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_nav:
                mViewPager.arrowScroll(View.FOCUS_LEFT);
                break;
            case R.id.right_nav:
                mViewPager.arrowScroll(View.FOCUS_RIGHT);

                break;
        }
    }

    private void handleVisibility() {
        if (isFirstPage()) {
            mLeftButton.setVisibility(View.INVISIBLE);
        } else {
            mLeftButton.setVisibility(View.VISIBLE);
        }

        if (isLastPage()) {
            mRightButton.setVisibility(View.INVISIBLE);
        } else {
            mRightButton.setVisibility(View.VISIBLE);
        }
    }

    private boolean isFirstPage() {
        return mViewPager.getCurrentItem() == 0;
    }

    private boolean isLastPage() {
        return mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1;
    }
}