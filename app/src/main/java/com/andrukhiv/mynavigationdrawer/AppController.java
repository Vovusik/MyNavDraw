package com.andrukhiv.mynavigationdrawer;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
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

        createChannel(); // Створюю канал для Notification

        // Створюю власну тему для додатка
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = prefs.getString("theme", "default");
        AppCompatDelegate.setDefaultNightMode(
                getNightModeInt(theme)
        );

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    // todo: перевірку працездатності Notification не зміг, так як консоль firebase 1 год. відправляла сповіщення
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
            case "light":
                return AppCompatDelegate.MODE_NIGHT_NO;
            case "dark":
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