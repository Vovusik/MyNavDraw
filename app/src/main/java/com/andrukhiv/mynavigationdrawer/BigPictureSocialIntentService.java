///*
//Copyright 2016 The Android Open Source Project
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
// */
//package com.andrukhiv.mynavigationdrawer;
//
//import android.annotation.SuppressLint;
//import android.app.IntentService;
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationCompat.BigPictureStyle;
//import androidx.core.app.NotificationManagerCompat;
//import androidx.core.app.RemoteInput;
//import androidx.core.app.TaskStackBuilder;
//
//import com.andrukhiv.mynavigationdrawer.activity.MainActivity;
//import com.andrukhiv.mynavigationdrawer.activity.RegionActivity;
//
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//
//// Асинхронно обрабатывает обновления сообщений социальных приложений (и активных уведомлений) с
////комментариями от пользователь. Уведомление для использования в социальных сетях BigPictureStyle.
//
//public class BigPictureSocialIntentService extends IntentService {
//
//    private static final String TAG = "BigPictureService";
//
//    public static final String ACTION_COMMENT =
//            "com.example.android.wearable.wear.wearnotifications.handlers.action.COMMENT";
//
//    public static final String EXTRA_COMMENT =
//            "com.example.android.wearable.wear.wearnotifications.handlers.extra.COMMENT";
//
//    public BigPictureSocialIntentService() {
//        super("BigPictureSocialIntentService");
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        Log.d(TAG, "onHandleIntent(): " + intent);
//
//        if (intent != null) {
//            final String action = intent.getAction();
//            if (ACTION_COMMENT.equals(action)) {
//                handleActionComment(getMessage(intent));
//            }
//        }
//    }
//
//    /**
//   * Обрабатывает действие для добавления комментария из уведомления.
//     */
//    private void handleActionComment(CharSequence comment) {
//        Log.d(TAG, "handleActionComment(): " + comment);
//
//        if (comment != null) {
//
//            // TODO: Asynchronously save your message to Database and servers.
//
////           / *
////             * У вас есть два варианта обновления уведомления (этот класс использует приближения # 2):
////             *
////             * 1. Используйте новый NotificationCompatBuilder создать уведомление. Этот подход
////             * требует, чтобы вы получили * ВСЕ * информацию, которая существовала в предыдущем
////             * Уведомление (и обновления) и передать его в построитель. Это подход, используемый в
////             * Основная деятельность.
////             *
////             * 2. Используйте оригинальный NotificationCompatBuilder для создания Уведомления. Эта
////             * Подход требует от вас хранить ссылку на оригинального застройщика. Преимущество
////             * вам нужна только новая / обновленная информация. В нашем случае комментарий от пользователя
////             * относительно поста (который у нас уже есть).
////             *
////             * ВНИМАНИЕ: Вы не должны сохранять / изменять полученный объект уведомления с помощью
////             * переменные-члены и / или устаревшие API. Если вы хотите сохранить что-либо из обновления
////             * чтобы обновить, сохраните Builder как вариант 2.
////             * /
//            // Retrieves NotificationCompat.Builder used to create initial Notification
//            NotificationCompat.Builder notificationCompatBuilder =
//                    GlobalNotificationBuilder.getNotificationCompatBuilderInstance();
//
//            // Recreate builder from persistent state if app process is killed
//            if (notificationCompatBuilder == null) {
//                // Note: New builder set globally in the method
//                notificationCompatBuilder = recreateBuilderWithBigPictureStyle();
//            }
//
//            // Updates active Notification
//            Notification updatedNotification = notificationCompatBuilder
//                    // Adds a line and comment below content in Notification
//                    .setRemoteInputHistory(new CharSequence[]{comment})
//                    .build();
//
//            // Pushes out the updated Notification
//            NotificationManagerCompat notificationManagerCompat =
//                    NotificationManagerCompat.from(getApplicationContext());
//            notificationManagerCompat.notify(MainActivity.NOTIFICATION_ID, updatedNotification);
//        }
//    }
//
//    /*
//     * Extracts CharSequence created from the RemoteInput associated with the Notification.
//     */
//    private CharSequence getMessage(Intent intent) {
//        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
//        if (remoteInput != null) {
//            return remoteInput.getCharSequence(EXTRA_COMMENT);
//        }
//        return null;
//    }
//
////      / *
////      * Это воссоздает уведомление из постоянного состояния в случае, если процесс приложения был убит.
////      * Это в основном тот же код для создания уведомлений из MainActivity.
////      * /
//    @SuppressLint("WrongConstant")
//    private NotificationCompat.Builder recreateBuilderWithBigPictureStyle() {
//
//           // Основные шаги для создания уведомления BIG_PICTURE_STYLE (для более подробных комментариев
////         // строим это уведомление, проверяем MainActivity.java):
////         // 0. Получите ваши данные
////         // 1. Создаем BIG_PICTURE_STYLE
////         // 2. Настройка основного намерения для уведомления
////         // 3. Настройка RemoteInput, чтобы пользователи могли вводить (клавиатура и голос) из уведомления
////         // 4. Сборка и выдача уведомления
////
////         // 0. Получите ваши данные (все уникально для каждого уведомления)
//        MockDatabase.BigPictureStyleSocialAppData bigPictureStyleSocialAppData =
//                MockDatabase.getBigPictureStyleData();
//
//        // 1. Build the BIG_PICTURE_STYLE
//        BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle()
//                .bigPicture(
//                        BitmapFactory.decodeResource(
//                                getResources(),
//                                bigPictureStyleSocialAppData.getBigImage()))
//                .setBigContentTitle(bigPictureStyleSocialAppData.getBigContentTitle())
//                .setSummaryText(bigPictureStyleSocialAppData.getSummaryText());
//
//      // 2. Настройка основного намерения для уведомления
//        Intent mainIntent = new Intent(this, RegionActivity.class);
//
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(RegionActivity.class);
//        stackBuilder.addNextIntent(mainIntent);
//
//        PendingIntent mainPendingIntent =
//                PendingIntent.getActivity(
//                        this,
//                        0,
//                        mainIntent,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//
//        // 3. Настройка RemoteInput, чтобы пользователи могли вводить (клавиатура и голос)
//        // из уведомления
//        String replyLabel = getString(R.string.reply_label);
//        RemoteInput remoteInput =
//                new RemoteInput.Builder(BigPictureSocialIntentService.EXTRA_COMMENT)
//                        .setLabel(replyLabel)
//                        .setChoices(bigPictureStyleSocialAppData.getPossiblePostResponses())
//                        .build();
//
//        PendingIntent replyActionPendingIntent;
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Intent intent = new Intent(this, BigPictureSocialIntentService.class);
//            intent.setAction(BigPictureSocialIntentService.ACTION_COMMENT);
//            replyActionPendingIntent = PendingIntent.getService(this, 0, intent, 0);
//
//        } else {
//            replyActionPendingIntent = mainPendingIntent;
//        }
//
//        NotificationCompat.Action replyAction =
//                new NotificationCompat.Action.Builder(
//                        R.drawable.ic_reply_white_18dp,
//                        replyLabel,
//                        replyActionPendingIntent)
//                        .addRemoteInput(remoteInput)
//                        .build();
//
//       // 4. Сборка и выдача уведомления
//        NotificationCompat.Builder notificationCompatBuilder =
//                new NotificationCompat.Builder(getApplicationContext());
//
//        GlobalNotificationBuilder.setNotificationCompatBuilderInstance(notificationCompatBuilder);
//
//        notificationCompatBuilder
//                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image))/*Уведомление с изображением*/
//                .setContentTitle(messageTitle)
//                .setContentText(messageBody)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(BitmapFactory.decodeResource(
//                        getResources(),
//                        R.drawable.ic_person_black_48dp))
//                .setContentIntent(mainPendingIntent)
//                .setColor(getResources().getColor(R.color.colorPrimary))
//                .setSubText(Integer.toString(1))
//                .addAction(replyAction)
//                .setCategory(Notification.CATEGORY_SOCIAL)
//                .setPriority(Notification.PRIORITY_HIGH)
//                .setVisibility(Notification.VISIBILITY_PRIVATE);
//
//        // Если телефон находится в режиме «Не беспокоить», пользователь все равно будет
//        // уведомлен, если отправитель отмечен как любимый.
//        for (String name : bigPictureStyleSocialAppData.getParticipants()) {
//            notificationCompatBuilder.addPerson(name);
//        }
//
//        return notificationCompatBuilder;
//    }
//
//
//
//}
