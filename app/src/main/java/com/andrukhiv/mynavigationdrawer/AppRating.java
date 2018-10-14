package com.andrukhiv.mynavigationdrawer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.Toast;

public class AppRating {

    private final static String APP_TITLE = "Sommelier";
    private final static String APP_PACKAGE_NAME = "com.eajy.materialdesigndemo";

    //	Инициализировано до 0 и 3 только для целей тестирования.
    //  В реальном приложении это изменение
    private final static int DAYS_UNTIL_PROMPT = 3;
    private final static int LAUNCH_UNTIL_PROMPT = 3;

    public static void app_launched(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("rate_app", 0);
        if (prefs.getBoolean("dontshowagain", false)) {
            return;
        }

        SharedPreferences.Editor editor = prefs.edit();

        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        Long date_firstLaunch = prefs.getLong("date_first_launch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_first_launch", date_firstLaunch);
        }

        if (launch_count >= LAUNCH_UNTIL_PROMPT) {

            if (System.currentTimeMillis() >= date_firstLaunch + (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showRateDialog(context, editor);
            }
        }
        editor.commit();
    }

    public static void showRateDialog(final Context context, final SharedPreferences.Editor editor) {

        Dialog dialog = new Dialog(context);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);

        String message = "Якщо Вам подобається додаток "
                + APP_TITLE
                + ", чи не могли б Ви знайти час, щоб оцінити його? Це займе не більше хвилини. "
                + "Дякую за Вашу підтримку!";

        builder.setMessage(message)
                .setTitle("Оцінити додаток " + APP_TITLE)
                 //.setIcon(R.drawable.dialog_icon_logo)
                //.setIcon(context.getApplicationInfo().icon)
                .setCancelable(false)
                .setPositiveButton("Оцінити зараз", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putBoolean("dontshowagain", true);
                        editor.commit();

                        // Если ваше приложение не было загружено на рынок, вы получите исключение. ,
                        // Для целей тестирования мы поймаем его здесь и покажем некоторый текст.
                        try {
                            context.startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=" + APP_PACKAGE_NAME)));
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(context, "Ви натиснули кнопку «Оцінити зараз»", Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();
                    }
                })
                .setNeutralButton("Оцінити пізніше", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Ви натиснули кнопку «Оцінити пізніше»", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Ні, дякую", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editor != null) {
                            editor.putBoolean("dontshowagain", true);
                            editor.commit();
                        }

                        Toast.makeText(context, "Ви натиснули кнопку «Ні, дякую»", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

        dialog = builder.create();
        dialog.show();
    }

}
