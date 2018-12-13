package com.andrukhiv.mynavigationdrawer;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//        MyFirebaseMessagingService.java расширяет FirebaseMessagingService. Он содержит
//        переопределенный метод onMessageReceived (RemoteMessage remoteMessage). Если вы хотите,
//        чтобы приложения с приоритетными (все еще запущенными) получали уведомления или сообщения
//        данных, вам  нужно написать код для обработки обратного вызова onMessageReceived.
//        Метод onMessageReceived (...) не будет вызываться, если приложение находится в фоновом
//        режиме или убито, когда сообщение отправляется через Firebase Console. Но когда сообщение
//        отправляется через API (выполнить POST по следующему URL-адресу: https:
//        fcm.googleapis.com/fcm/send), будет вызван метод onMessageReceived (...).
//        Мы будем показывать оба способа отправки уведомлений.

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessageService";
    Bitmap bitmap;

    /**
     * Вызывается при получении сообщения.
     *
     * @param remoteMessage Объект, представляющий сообщение, полученное от Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        Существует два типа сообщений с сообщениями и сообщениями с сообщениями. Обработка
//        сообщений данных здесь, в onMessageReceived, является ли приложение на переднем плане
//        или на заднем плане. Сообщения данных являются типом традиционно используется с GCM.
//        Сообщения уведомления принимаются только здесь, в onMessageReceived, когда приложение
//        находится на переднем плане. Когда приложение находится в фоновом режиме, отображается
//        автоматически созданное уведомление.
//        Когда пользователь отбирает уведомление, он возвращается в приложение. Сообщения,
//        содержащие оба уведомления полезная нагрузка данных рассматриваются как уведомления.
//        Консоль Firebase всегда отправляет уведомлениесообщения. Подробнее см.
//        Https://firebase.google.com/docs/cloud-messaging/concept-options.

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Проверьте, содержит ли сообщение полезную нагрузку данных.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Проверьте, содержит ли сообщение полезную нагрузку уведомления.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Сообщение, которое я отправляю, будет иметь ключи с именем [title, message, image, CalendarActivity]
        // и соответствующие значения.

        // Заголовок сповіщення
        String title = remoteMessage.getData().get("title");
        // Текст сповіщення
        String message = remoteMessage.getData().get("message");
        // imageUri будет содержать URL-адрес изображения, которое будет отображаться с уведомлением
        String imageUri = remoteMessage.getData().get("image");
        // Если ключ CalendarActivity имеет значение True, а затем, когда пользователь нажимает
        // на уведомление, в приложении CalendarActivity будет открыто.
        // Если ключ CalendarActivity имеет значение False, тогда, когда пользователь нажимает
        // на уведомление, в приложении будет открыто todo: CalendarActivity.
        String TrueOrFlase = remoteMessage.getData().get("RegionActivity");

        // Чтобы получить растровое изображение из полученного URL-адреса
        bitmap = getBitmapfromUrl(imageUri);

        sendNotification(title, message, bitmap, TrueOrFlase);
    }

    // Создайте и покажите простое уведомление, содержащее принятое сообщение FCM.
    private void sendNotification(String messageTitle, String messageBody, Bitmap image, String TrueOrFalse) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("RegionActivity", TrueOrFalse);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Запрашиваемый код */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder((this), "Message" )
                .setLargeIcon(image)/*Notification icon image*/
                .setSmallIcon(R.drawable.ic_search_icon)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image))/*Уведомление с изображением*/
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1 /* Идентификатор уведомления - канал */, notificationBuilder.build());
    }

    // Чтобы получить растровое изображение из полученного URL-адреса
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Автоматически генерируемый блок catch
            e.printStackTrace();
            return null;
        }
    }
}


