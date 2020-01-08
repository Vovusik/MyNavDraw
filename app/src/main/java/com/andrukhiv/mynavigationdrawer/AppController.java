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

        //createChannel(); // Створюю канал для Notification

        // Створюю власну тему для додатка
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        String theme = prefs.getString("theme", LIGHT_MODE);
//        AppCompatDelegate.setDefaultNightMode(
//                getNightModeInt(theme)
//        );

        //PreferenceManager.setDefaultValues(this, R.xml.preferences, false);


        SharedPreferences sharedPreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        String themePref = sharedPreferences.getString("theme", ThemeHelper.DEFAULT_MODE);
        ThemeHelper.applyTheme(themePref);

       // androidx.preference.PreferenceManager.setDefaultValues(this, R.xml.preferencess, false);






    }


//    private void createChannel() {
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////
////
////
////            NotificationManager nm = (NotificationManager)
////                    getSystemService(Context.NOTIFICATION_SERVICE);
////            if (nm != null) {
////                NotificationChannel channel = new NotificationChannel(
////                        "Message",
////                        "title",
////                        NotificationManager.IMPORTANCE_HIGH);
////                nm.createNotificationChannel(channel);
////
////            }
////        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            // Create channel to show notifications.
//            String channelId  = getString(R.string.default_notification_channel_id);
//            String channelName = getString(R.string.default_notification_channel_name);
//            NotificationManager notificationManager =
//                    getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
//                    channelName, NotificationManager.IMPORTANCE_LOW));
//        }
//
//    }










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







    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}