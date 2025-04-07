package com.prayertime.app.repository;

import android.content.Context;
import android.util.Log;

import com.prayertime.app.api.ApiService;
import com.prayertime.app.model.City;
import com.prayertime.app.model.PrayerTime;

import java.util.Calendar;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Repository class to manage prayer time data from API and local storage
 */
public class PrayerTimeRepository {
    private static final String TAG = "PrayerTimeRepository";
    private static final String BASE_URL = "https://habous-prayer-times-api.onrender.com";
    
    private final ApiService apiService;
    private final Context context;
    
    public PrayerTimeRepository(Context context) {
        this.context = context;
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        apiService = retrofit.create(ApiService.class);
    }
    
    /**
     * Interface for API response callbacks
     */
    public interface ApiCallback<T> {
        void onSuccess(T result);
        void onError(String errorMessage);
    }
    
    /**
     * Get all cities from the API
     */
    public void getAllCities(final ApiCallback<Map<String, City>> callback) {
        apiService.getAllCities().enqueue(new Callback<Map<String, City>>() {
            @Override
            public void onResponse(Call<Map<String, City>> call, Response<Map<String, City>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to get cities: " + response.message());
                }
            }
            
            @Override
            public void onFailure(Call<Map<String, City>> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
                Log.e(TAG, "Network error", t);
            }
        });
    }
    
    /**
     * Get prayer times for today for a specific city
     */
    public void getTodayPrayerTimes(int cityId, final ApiCallback<PrayerTime> callback) {
        apiService.getPrayerTimeForToday(cityId, "Africa/Casablanca").enqueue(new Callback<PrayerTime>() {
            @Override
            public void onResponse(Call<PrayerTime> call, Response<PrayerTime> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to get prayer times: " + response.message());
                }
            }
            
            @Override
            public void onFailure(Call<PrayerTime> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
                Log.e(TAG, "Network error", t);
            }
        });
    }
    
    /**
     * Get prayer times for a specific date and city
     */
    public void getPrayerTimeForDate(int cityId, int year, int month, int day, final ApiCallback<PrayerTime> callback) {
        apiService.getPrayerTimeForDate(cityId, year, month, day).enqueue(new Callback<PrayerTime>() {
            @Override
            public void onResponse(Call<PrayerTime> call, Response<PrayerTime> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to get prayer times: " + response.message());
                }
            }
            
            @Override
            public void onFailure(Call<PrayerTime> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
                Log.e(TAG, "Network error", t);
            }
        });
    }
}
