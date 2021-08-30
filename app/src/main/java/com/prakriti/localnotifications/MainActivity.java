package com.prakriti.localnotifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
// create notification channel for Android 8 (OREO) and above

    private String title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edtTitle = findViewById(R.id.edtTitle);
        EditText edtContent = findViewById(R.id.edtContent);
        Button btnNotify = findViewById(R.id.btnNotify);

        btnNotify.setOnClickListener(v -> {
            if(edtTitle.getText().toString().equals("") || edtContent.getText().toString().equals("")) {
                Toast.makeText(MainActivity.this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            title = edtTitle.getText().toString();
            content = edtContent.getText().toString();
            showNotification();
        });
    }

    private void showNotification() {
        final int NOTIF_ID = 111;
        final String CHANNEL_ID = "My_Channel";
        final CharSequence name = "my_channel";
        final String description = "This is my channel";

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID) // builder(context) is deprecated
            .setSmallIcon(R.drawable.notify)
            .setContentTitle(title)
            .setContentText(content);

        // when user click on notification
        Intent intent = new Intent(this, MainActivity.class); // can also specify other activities
        // pending intent grants an application to perform an action
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // req code - specifies this notif is specific to this activity
        // flag - if pending intent exists, but replace extra data with new intent

        builder.setContentIntent(pendingIntent);
        notificationManager.notify(NOTIF_ID, builder.build());
        // id of notif manager

    }

}