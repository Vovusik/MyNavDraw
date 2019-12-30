package com.andrukhiv.mynavigationdrawer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.andrukhiv.mynavigationdrawer.adapters.PreparatyAdapterFactory;
import com.andrukhiv.mynavigationdrawer.models.PreparatyModel;


public class PreperatyViewGroup extends FrameLayout
        implements
        PreparatyAdapterFactory.FirstHeaderBinder<String>,
        PreparatyAdapterFactory.HeaderBinder<String>,
        PreparatyAdapterFactory.FirstBodyBinder<PreparatyModel>,
        PreparatyAdapterFactory.BodyBinder<PreparatyModel>,
        PreparatyAdapterFactory.SectionBinder<PreparatyModel> {

    private Context context;
    public TextView textView;
    public View vg_root;

    public PreperatyViewGroup(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PreperatyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(context).inflate(R.layout.preparaty_view_group, this, true);
        textView = findViewById(R.id.tv_text);
        vg_root = findViewById(R.id.vg_root);
    }

    @Override
    public void bindFirstHeader(String headerName) {
        textView.setText(headerName.toUpperCase());
        textView.setTypeface(null, Typeface.BOLD);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        vg_root.setBackgroundResource(R.color.colorPrimary);
    }

    @Override
    public void bindHeader(String headerName, int column) {
        textView.setText(headerName.toUpperCase());
        textView.setTypeface(null, Typeface.BOLD);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        vg_root.setBackgroundResource(R.color.colorPrimary);
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void bindFirstBody(PreparatyModel item, int row) {
        textView.setText(item.data[0]);
        textView.setTypeface(null, Typeface.NORMAL);
        textView.setGravity(Gravity.LEFT);
        vg_root.setBackgroundResource(row % 2 == 0 ? R.color.rowBackground : R.color.windowBackground);
    }

    @Override
    public void bindBody(PreparatyModel item, int row, int column) {
        textView.setText(item.data[column + 1]);
        textView.setTypeface(null, Typeface.NORMAL);
        textView.setGravity(Gravity.CENTER);
        vg_root.setBackgroundResource(row % 2 == 0 ? R.color.rowBackground : R.color.windowBackground);
    }

    @Override
    public void bindSection(PreparatyModel item, int row, int column) {
        textView.setText(column == 0 ? item.type.toUpperCase() : "");
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        vg_root.setBackgroundResource(R.color.colorAccent);
    }
}
