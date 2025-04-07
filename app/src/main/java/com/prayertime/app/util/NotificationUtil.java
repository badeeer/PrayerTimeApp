package com.prayertime.app.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.prayertime.app.model.PrayerTime;
import com.prayertime.app.receiver.NotificationReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Utility class for managing prayer time notifications
 */
public class NotificationUtil {
    private static final String TAG = "NotificationUtil";
    
    // Prayer types
    public static final String PRAYER_FAJR = "fajr";
    public static final String PRAYER_SUNRISE = "sunrise";
    public static final String PRAYER_DOHR = "dohr";
    public static final String PRAYER_ASR = "asr";
    public static final String PRAYER_MAGHREB = "maghreb";
    public static final String PRAYER_ICHAA = "ichaa";
    
    // Shared preferences keys
    private static final String PREF_NOTIFICATION_ENABLED = "notification_enabled";
    private static final String PREF_NOTIFICATION_MINUTES_BEFORE = "notification_minutes_before";
    private static final String PREF_NOTIFICATION_PREFIX = "notification_";
    
    /**
     * Schedule notifications for all prayer times
     */
    public static void scheduleNotifications(Context context, PrayerTime prayerTime) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean notificationsEnabled = prefs.getBoolean(PREF_NOTIFICATION_ENABLED, true);
        
        if (!notificationsEnabled) {
            return;
        }
        
        int minutesBefore = prefs.getInt(PREF_NOTIFICATION_MINUTES_BEFORE, 15);
        
        // Schedule individual prayer notifications if enabled
        if (prefs.getBoolean(PREF_NOTIFICATION_PREFIX + PRAYER_FAJR, true)) {
            scheduleNotification(context, PRAYER_FAJR, prayerTime.getFajr(), minutesBefore);
        }
        
        if (prefs.getBoolean(PREF_NOTIFICATION_PREFIX + PRAYER_DOHR, true)) {
            scheduleNotification(context, PRAYER_DOHR, prayerTime.getDohr(), minutesBefore);
        }
        
        if (prefs.getBoolean(PREF_NOTIFICATION_PREFIX + PRAYER_ASR, true)) {
            scheduleNotification(context, PRAYER_ASR, prayerTime.getAsr(), minutesBefore);
        }
        
        if (prefs.getBoolean(PREF_NOTIFICATION_PREFIX + PRAYER_MAGHREB, true)) {
            scheduleNotification(context, PRAYER_MAGHREB, prayerTime.getMaghreb(), minutesBefore);
        }
        
        if (prefs.getBoolean(PREF_NOTIFICATION_PREFIX + PRAYER_ICHAA, true)) {
            scheduleNotification(context, PRAYER_ICHAA, prayerTime.getIchaa(), minutesBefore);
        }
    }
    
    /**
     * Schedule a notification for a specific prayer time
     */
    private static void scheduleNotification(Context context, String prayerType, String prayerTimeStr, int minutesBefore) {
        try {
            // Parse the prayer time
            SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date prayerTime = format.parse(prayerTimeStr);
            
            // Create calendar for the notification time
            Calendar calendar = Calendar.getInstance();
            Calendar prayerCalendar = Calendar.getInstance();
            
            if (prayerTime != null) {
                prayerCalendar.setTime(prayerTime);
                
                // Set the notification time to today with the prayer time
                calendar.set(Calendar.HOUR_OF_DAY, prayerCalendar.get(Calendar.HOUR_OF_DAY));
                calendar.set(Calendar.MINUTE, prayerCalendar.get(Calendar.MINUTE));
                calendar.set(Calendar.SECOND, 0);
                
                // Subtract the minutes before
                calendar.add(Calendar.MINUTE, -minutesBefore);
                
                // If the time has already passed today, don't schedule
                if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
                    return;
                }
                
                // Create the intent for the notification
                Intent intent = new Intent(context, NotificationReceiver.class);
                intent.putExtra("prayer_type", prayerType);
                intent.putExtra("prayer_time", prayerTimeStr);
                
                // Create a unique ID for each prayer type
                int notificationId = getPrayerNotificationId(prayerType);
                
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context,
                        notificationId,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );
                
                // Schedule the notification
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                if (alarmManager != null) {
                    alarmManager.setExact(
                            AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis(),
                            pendingIntent
                    );
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Get a unique notification ID for each prayer type
     */
    private static int getPrayerNotificationId(String prayerType) {
        switch (prayerType) {
            case PRAYER_FAJR:
                return 1001;
            case PRAYER_SUNRISE:
                return 1002;
            case PRAYER_DOHR:
                return 1003;
            case PRAYER_ASR:
                return 1004;
            case PRAYER_MAGHREB:
                return 1005;
            case PRAYER_ICHAA:
                return 1006;
            default:
                return 1000;
        }
    }
    
    /**
     * Cancel all scheduled prayer notifications
     */
    public static void cancelAllNotifications(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            return;
        }
        
        // Cancel each prayer notification
        cancelNotification(context, PRAYER_FAJR);
        cancelNotification(context, PRAYER_SUNRISE);
        cancelNotification(context, PRAYER_DOHR);
        cancelNotification(context, PRAYER_ASR);
        cancelNotification(context, PRAYER_MAGHREB);
        cancelNotification(context, PRAYER_ICHAA);
    }
    
    /**
     * Cancel a specific prayer notification
     */
    private static void cancelNotification(Context context, String prayerType) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        int notificationId = getPrayerNotificationId(prayerType);
        
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                notificationId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
