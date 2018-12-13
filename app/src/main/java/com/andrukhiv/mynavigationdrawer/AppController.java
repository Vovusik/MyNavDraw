package com.andrukhiv.mynavigationdrawer;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
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

    // за замовчуванням автоматичний вибір теми додатку в залежності від часу доби
    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_AUTO);
    }
}