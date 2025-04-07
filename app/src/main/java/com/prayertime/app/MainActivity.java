package com.prayertime.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.prayertime.app.adapter.PrayerTimeAdapter;
import com.prayertime.app.model.PrayerTime;
import com.prayertime.app.repository.PrayerTimeRepository;
import com.prayertime.app.util.NotificationUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String PREF_SELECTED_CITY_ID = "selected_city_id";
    
    private PrayerTimeRepository repository;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private TextView tvCityName;
    private TextView tvDate;
    private PrayerTimeAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Initialize views
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        tvCityName = findViewById(R.id.tvCityName);
        tvDate = findViewById(R.id.tvDate);
        
        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PrayerTimeAdapter(this);
        recyclerView.setAdapter(adapter);
        
        // Initialize repository
        repository = new PrayerTimeRepository(this);
        
        // Set up swipe refresh
        swipeRefreshLayout.setOnRefreshListener(this::loadPrayerTimes);
        
        // Set current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault());
        tvDate.setText(dateFormat.format(new Date()));
        
        // Check if city is selected
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedCityId = prefs.getInt(PREF_SELECTED_CITY_ID, -1);
        
        if (selectedCityId == -1) {
            // No city selected, go to city selection
            startCitySelection();
        } else {
            // Load prayer times for selected city
            loadPrayerTimes();
        }
        
        // Set click listener for city name to change city
        tvCityName.setOnClickListener(v -> startCitySelection());
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        loadPrayerTimes();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_settings) {
            // Open settings activity
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * Start city selection activity
     */
    private void startCitySelection() {
        Intent intent = new Intent(this, CitySelectionActivity.class);
        startActivity(intent);
    }
    
    /**
     * Load prayer times for selected city
     */
    private void loadPrayerTimes() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedCityId = prefs.getInt(PREF_SELECTED_CITY_ID, -1);
        String selectedCityName = prefs.getString("selected_city_name", "");
        
        if (selectedCityId == -1) {
            return;
        }
        
        // Show city name
        tvCityName.setText(selectedCityName);
        
        // Show loading indicator
        swipeRefreshLayout.setRefreshing(true);
        
        // Load prayer times from repository
        repository.getTodayPrayerTimes(selectedCityId, new PrayerTimeRepository.ApiCallback<PrayerTime>() {
            @Override
            public void onSuccess(PrayerTime result) {
                // Hide loading indicator
                swipeRefreshLayout.setRefreshing(false);
                
                // Update adapter with prayer times
                adapter.setPrayerTime(result);
                
                // Schedule notifications
                NotificationUtil.scheduleNotifications(MainActivity.this, result);
            }
            
            @Override
            public void onError(String errorMessage) {
                // Hide loading indicator
                swipeRefreshLayout.setRefreshing(false);
                
                // Show error message
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
