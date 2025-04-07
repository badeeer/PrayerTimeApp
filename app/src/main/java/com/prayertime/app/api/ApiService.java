package com.prayertime.app.api;

import com.prayertime.app.model.City;
import com.prayertime.app.model.PrayerTime;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit interface for the Morocco Prayer Times API
 */
public interface ApiService {
    
    /**
     * Get all cities
     * @return Map of city IDs to City objects
     */
    @GET("/api/cities")
    Call<Map<String, City>> getAllCities();
    
    /**
     * Get a specific city by ID
     * @param id City ID
     * @return City object
     */
    @GET("/api/cities/{id}")
    Call<City> getCityById(@Path("id") int id);
    
    /**
     * Get prayer times for a specific date
     * @param city City ID
     * @param year Year
     * @param month Month
     * @param day Day
     * @return PrayerTime object
     */
    @GET("/api/{city}/{year}/{month}/{day}")
    Call<PrayerTime> getPrayerTimeForDate(
            @Path("city") int city,
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);
    
    /**
     * Get prayer times for the current day
     * @param city City ID
     * @param timezone Optional timezone
     * @return PrayerTime object
     */
    @GET("/api/{city}/today")
    Call<PrayerTime> getPrayerTimeForToday(
            @Path("city") int city,
            @Query("timezone") String timezone);
}
