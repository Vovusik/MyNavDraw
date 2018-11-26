package com.andrukhiv.mynavigationdrawer;

import android.net.Uri;

public class Constant {

    public static String EMAIL = "vovaandrukhiv@gmail.com";
    public final static String APP_PACKAGE_NAME = "com.eajy.materialdesigndemo";
    public final static String GOOGLE_PLAY_MARKET_ANDROID = "market://details?id=";
    public final static String GOOGLE_PLAY_MARKET_WEB = "https://play.google.com/store/apps/details?id=";
    public static String DEVELOPER_BY = "Розробник Володимир Андрухів";
    public static String SHARE_CONTENT = "Додаток, який допомагає вирощуванню та догляду винограду," +
            " виноробству, а також відвідуванню дегустаційних залів Закарпаття:\n\n" +
            GOOGLE_PLAY_MARKET_ANDROID + APP_PACKAGE_NAME + "\n\n" + DEVELOPER_BY;

    //public static String APP_URL = "https://play.google.com/store/apps/details?id=com.eajy.materialdesigndemo";

//    Ссылка на страницу разработчика
//    https://play.google.com/store/apps/dev?id=5700313618786177705
//
//    Ссылка на страницу приложения
//    http://play.google.com/store/apps/details?id=<package_name>

    // Добавляємо константу EXTRA_GRAPES_ID
    public static final String EXTRA_GRAPES_ID = "grapesId";
}
