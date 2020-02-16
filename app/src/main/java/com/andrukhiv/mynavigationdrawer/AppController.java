package com.andrukhiv.mynavigationdrawer;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;

import com.andrukhiv.mynavigationdrawer.activity.RegionActivity;
import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.database.DbHelper;

import java.io.IOException;

import static android.content.Intent.getIntent;


public class AppController extends Application {

    public static final String LIGHT_MODE = "light";
    public static final String DARK_MODE = "dark";
    public static final String DEFAULT_MODE = "default";
    DbAdapter mDbHelper;


    @Override
    public void onCreate() {
        super.onCreate();

        mDbHelper = DbAdapter.getInstance(getApplicationContext());
        mDbHelper.createDatabase();
        mDbHelper.open();

        // Створюю власну тему для додатка
        SharedPreferences sharedPreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        String themePref = sharedPreferences.getString("theme", ThemeHelper.DEFAULT_MODE);
        ThemeHelper.applyTheme(themePref);
    }


    @AppCompatDelegate.NightMode
    public static int getNightModeInt(String nightMode){
        switch (nightMode) {
            case LIGHT_MODE:
                return AppCompatDelegate.MODE_NIGHT_NO;
            case DARK_MODE:
                return AppCompatDelegate.MODE_NIGHT_YES;
            default:
                return AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY;
        }
    }
}