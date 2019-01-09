package com.andrukhiv.mynavigationdrawer.activity;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.andrukhiv.mynavigationdrawer.adapters.LibraryRecyclerViewAdapter;
import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.models.LibraryModel;

import java.util.ArrayList;

public class LibraryActivity extends AppCompatActivity {

    DbAdapter mDbHelper;

    private static final int REQUEST_OPEN = 1337;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<LibraryModel> libraryModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_up_arrow_icon);
        }

        mDbHelper = DbAdapter.getInstance(getApplicationContext());

        RecyclerView mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        libraryModels = mDbHelper.getLibrary();

        mAdapter = new LibraryRecyclerViewAdapter(libraryModels);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        ((LibraryRecyclerViewAdapter) mAdapter).setOnItemClickListener(new LibraryRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                openCustomTab(position);
            }
        });
    }


    private void openCustomTab(int position) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(LibraryActivity.this, R.color.colorPrimary));
        builder.setSecondaryToolbarColor(ContextCompat.getColor(LibraryActivity.this, R.color.colorPrimaryDark));
        builder.addDefaultShareMenuItem();
        builder.setShowTitle(true);
        builder.setCloseButtonIcon(BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_up_arrow_icon));
        builder.setStartAnimations(LibraryActivity.this, R.anim.slide_in_right, R.anim.slide_out_left);
        builder.setExitAnimations(LibraryActivity.this, android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        CustomTabsIntent customTabsIntent = builder.build();
        try {
            customTabsIntent.launchUrl(LibraryActivity.this, Uri.parse(libraryModels.get(position).getLink()));
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
        if (item.getItemId() == R.id.btnShowDwn) {
            show();
//        } else if (item.getItemId() == R.id.pdfs) {
//            open();
        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return (super.onOptionsItemSelected(item));
    }


//    private void open() {
//        Intent i = new Intent()
//                .setType("application/library")
//                .setAction(Intent.ACTION_OPEN_DOCUMENT)
//                .addCategory(Intent.CATEGORY_OPENABLE);
//        startActivityForResult(i, REQUEST_OPEN);
//    }

//    private void openDownloadsPage(Context context) {
//        Intent pageView = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
//        pageView.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(pageView);
//    }


//    @Keep
//    @AfterPermissionGranted(REQUEST_PERMISSION)
//    private void navigateToPlayer() {
//        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//            Intent intent = new Intent(this, MusicPlayerActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            EasyPermissions.requestPermissions(this, getString(R.string.read_permission_rational), REQUEST_PERMISSION, Manifest.permission.READ_EXTERNAL_STORAGE);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        // Forward results to EasyPermissions
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//    }


    private void show() {
        Intent showDwn = new Intent();
        showDwn.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(showDwn);
    }
}