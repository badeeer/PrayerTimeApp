package com.prayertime.app.receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.prayertime.app.MainActivity;
import com.prayertime.app.R;
import com.prayertime.app.util.NotificationUtil;

/**
 * Broadcast receiver for handling prayer time notifications
 */
public class NotificationReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "prayer_time_channel";
    private static final String CHANNEL_NAME = "Prayer Time Notifications";
    private static final String CHANNEL_DESCRIPTION = "Notifications for prayer times";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get prayer information from intent
        String prayerType = intent.getStringExtra("prayer_type");
        String prayerTime = intent.getStringExtra("prayer_time");
        
        if (prayerType == null || prayerTime == null) {
            return;
        }
        
        // Create notification channel for Android O and above
        createNotificationChannel(context);
        
        // Create intent for when notification is tapped
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        
        // Get notification title and message based on prayer type
        String title = getPrayerTitle(prayerType);
        String message = getPrayerMessage(prayerType, prayerTime);
        
        // Get notification sound
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        
        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification) // You'll need to create this resource
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        
        // Show the notification
        NotificationManager notificationManager = 
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        
        if (notificationManager != null) {
            notificationManager.notify(NotificationUtil.getPrayerNotificationId(prayerType), builder.build());
        }
    }
    
    /**
     * Create notification channel for Android O and above
     */
    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(CHANNEL_DESCRIPTION);
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);
            channel.enableVibration(true);
            
            NotificationManager notificationManager = 
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
    
    /**
     * Get notification title based on prayer type
     */
    private String getPrayerTitle(String prayerType) {
        switch (prayerType) {
            case NotificationUtil.PRAYER_FAJR:
                return "Fajr Prayer";
            case NotificationUtil.PRAYER_SUNRISE:
                return "Sunrise";
            case NotificationUtil.PRAYER_DOHR:
                return "Dhuhr Prayer";
            case NotificationUtil.PRAYER_ASR:
                return "Asr Prayer";
            case NotificationUtil.PRAYER_MAGHREB:
                return "Maghrib Prayer";
            case NotificationUtil.PRAYER_ICHAA:
                return "Isha Prayer";
            default:
                return "Prayer Time";
        }
    }
    
    /**
     * Get notification message based on prayer type and time
     */
    private String getPrayerMessage(String prayerType, String prayerTime) {
        switch (prayerType) {
            case NotificationUtil.PRAYER_FAJR:
                return "It's time for Fajr prayer at " + prayerTime;
            case NotificationUtil.PRAYER_SUNRISE:
                return "Sunrise is at " + prayerTime;
            case NotificationUtil.PRAYER_DOHR:
                return "It's time for Dhuhr prayer at " + prayerTime;
            case NotificationUtil.PRAYER_ASR:
                return "It's time for Asr prayer at " + prayerTime;
            case NotificationUtil.PRAYER_MAGHREB:
                return "It's time for Maghrib prayer at " + prayerTime;
            case NotificationUtil.PRAYER_ICHAA:
                return "It's time for Isha prayer at " + prayerTime;
            default:
                return "Prayer time at " + prayerTime;
        }
    }
}
