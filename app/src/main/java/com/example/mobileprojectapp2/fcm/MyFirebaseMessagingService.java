package com.example.mobileprojectapp2.fcm;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.loading.LoadingActivity;
import com.example.mobileprojectapp2.activity.loginregister.LoginActivity;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.applications.MyNotification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {

        super.onMessageReceived(message);
        Map<String, String> stringMap = message.getData();
        Log.d("TAG", "onMessageReceived: id: " + stringMap.get("id") + " title: "+ stringMap.get("title"));
        int id = Integer.parseInt(stringMap.get("id"));
        String title = stringMap.get("title");
        String content = stringMap.get("content");
        notification(R.drawable.notification_selector, title, content, id);


    }
//    private void requestPermisstion(int icon, String title, String content, int id){
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
//            notification(icon, title, content, id);
//        }
//        else {
//            if (ContextCompat.checkSelfPermission(
//                    MyFirebaseMessagingService.this, Manifest.permission.POST_NOTIFICATIONS) ==
//                    PackageManager.PERMISSION_GRANTED){
//                notification(icon, title, content, id);
//            }
//            else {
//                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 111);
//            }
//        }
//    }
    private void notification(int icon, String title, String content, int id){
        Intent intent = new Intent(this, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2003, intent, PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(MyFirebaseMessagingService.this, MyNotification.CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(icon)
                .setColor(getResources().getColor(R.color.main_color_app_light, MyFirebaseMessagingService.this.getTheme()))
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) MyFirebaseMessagingService.this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(id, notification);
        }
    }

    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("TAG", "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {

    }


}
