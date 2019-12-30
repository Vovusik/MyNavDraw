package com.andrukhiv.mynavigationdrawer;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatDelegate;

import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.database.DbHelper;

import java.io.IOException;


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

        createChannel(); // Створюю канал для Notification

        // Створюю власну тему для додатка
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = prefs.getString("theme", DEFAULT_MODE);
        AppCompatDelegate.setDefaultNightMode(
                getNightModeInt(theme)
        );

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    // Todo: перевірку працездатності Notification не зміг, так як консоль firebase 1 год. відправляла сповіщення
    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (nm != null) {
                NotificationChannel channel = new NotificationChannel(
                        "Message",
                        "title",
                        NotificationManager.IMPORTANCE_HIGH);
                nm.createNotificationChannel(channel);

            }
        }
    }

    @AppCompatDelegate.NightMode
    public static int getNightModeInt(String nightMode){
        switch (nightMode) {
            case LIGHT_MODE:
                return AppCompatDelegate.MODE_NIGHT_NO;
            case DARK_MODE:
                return AppCompatDelegate.MODE_NIGHT_YES;
            default:
                return AppCompatDelegate.MODE_NIGHT_AUTO;
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}