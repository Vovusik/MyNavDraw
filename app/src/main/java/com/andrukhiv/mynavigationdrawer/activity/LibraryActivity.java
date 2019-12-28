package com.andrukhiv.mynavigationdrawer.activity;


import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.adapters.LibraryAdapter;
import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.models.LibraryModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;

public class LibraryActivity extends AppCompatActivity
        implements DiscreteScrollView.OnItemChangedListener,
        View.OnClickListener {

    DbAdapter mDbHelper;
    private List<LibraryModel> data;
    private TextView textTitle;
    private TextView textAuthor;
    private InfiniteScrollAdapter infiniteAdapter;
    FloatingActionButton buttonRead;
    private DiscreteScrollView itemPicker;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_ic_up_arrow);
        }

        textTitle = findViewById(R.id.text_title);
        textAuthor = findViewById(R.id.text_author);
        buttonRead = findViewById(R.id.button_read);
        itemPicker = findViewById(R.id.item_picker);

//        DB shop = DB.get();
//        data = shop.getLibrary();

        mDbHelper = DbAdapter.getInstance(getApplicationContext());
        data = DbAdapter.getLibrary();


        infiniteAdapter = InfiniteScrollAdapter.wrap(new LibraryAdapter(data));
        itemPicker.setOrientation(DSVOrientation.HORIZONTAL);
        itemPicker.addOnItemChangedListener(this);
        itemPicker.setAdapter(infiniteAdapter);
        itemPicker.setItemTransitionTimeMillis(150);
        itemPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        onItemChanged(data.get(0));

        buttonRead.setOnClickListener(this);
    }


    private void onItemChanged(LibraryModel item) {
        textTitle.setText(item.getTitle());
        textAuthor.setText(item.getAuthor());
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_read) {
            int realPosition = infiniteAdapter.getRealPosition(itemPicker.getCurrentItem());
            LibraryModel current = data.get(realPosition);

            openCustomTab(current.getId());

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Відкривається книга: " +
                            data.get(realPosition).getTitle(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }


    private void openCustomTab(int position) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(LibraryActivity.this, R.color.colorPrimary));
        builder.setSecondaryToolbarColor(ContextCompat.getColor(LibraryActivity.this, R.color.colorPrimaryDark));
        builder.addDefaultShareMenuItem();
        builder.setShowTitle(true);
        builder.setCloseButtonIcon(BitmapFactory.decodeResource(
                getResources(), R.drawable.toolbar_ic_up_arrow));
        builder.setStartAnimations(LibraryActivity.this, R.anim.slide_in_right, R.anim.slide_out_left);
        builder.setExitAnimations(LibraryActivity.this, android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        CustomTabsIntent customTabsIntent = builder.build();
        try {
            customTabsIntent.launchUrl(LibraryActivity.this, Uri.parse(data.get(position).getLink()));
        } catch (Exception e) {
            e.getStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.library, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.button_show_download:
                show();
                break;
            case android.R.id.home:
                // onBackPressed();
                finish();
//                Toast.makeText(this, "Повернення до головного меню", Toast.LENGTH_SHORT)
//                        .show();
//                break;
        }
        return (super.onOptionsItemSelected(item));
    }


    private void show() {
        Intent showDwn = new Intent();
        showDwn.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(showDwn);
    }


    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int position) {
        int positionInDataSet = infiniteAdapter.getRealPosition(position);
        onItemChanged(data.get(positionInDataSet));
    }
}
