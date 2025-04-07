package com.prayertime.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.prayertime.app.model.PrayerTime;
import com.prayertime.app.repository.PrayerTimeRepository;
import com.prayertime.app.util.NotificationUtil;

/**
 * Broadcast receiver for handling device boot
 * Reschedules prayer time notifications after device reboot
 */
public class BootReceiver extends BroadcastReceiver {
    private static final String PREF_SELECTED_CITY_ID = "selected_city_id";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            // Get selected city ID
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            int selectedCityId = prefs.getInt(PREF_SELECTED_CITY_ID, -1);
            
            if (selectedCityId != -1) {
                // Initialize repository
                PrayerTimeRepository repository = new PrayerTimeRepository(context);
                
                // Load prayer times and reschedule notifications
                repository.getTodayPrayerTimes(selectedCityId, new PrayerTimeRepository.ApiCallback<PrayerTime>() {
                    @Override
                    public void onSuccess(PrayerTime result) {
                        // Schedule notifications
                        NotificationUtil.scheduleNotifications(context, result);
                    }
                    
                    @Override
                    public void onError(String errorMessage) {
                        // Error handling - can't reschedule notifications now
                        // Will try again when app is opened
                    }
                });
            }
        }
    }
}
