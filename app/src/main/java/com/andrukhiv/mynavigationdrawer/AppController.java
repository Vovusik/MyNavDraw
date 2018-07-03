package com.andrukhiv.mynavigationdrawer;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;


public class AppController extends Application {


    @Override
    public void onCreate() {
        super.onCreate();


    }

    // за замовчуванням автоматичний вибір теми додатку в залежності від часу доби
    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_AUTO);
    }
}