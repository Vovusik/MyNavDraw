package com.andrukhiv.mynavigationdrawer;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.andrukhiv.mynavigationdrawer.database.DbAdapter;


public class AppController extends Application {

    DbAdapter mDbHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        mDbHelper = DbAdapter.getInstance(getApplicationContext());
        mDbHelper.createDatabase();
        mDbHelper.open();
    }

    // за замовчуванням автоматичний вибір теми додатку в залежності від часу доби
    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_AUTO);
    }
}