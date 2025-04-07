package com.prayertime.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prayertime.app.R;
import com.prayertime.app.model.PrayerTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Adapter for displaying prayer times in a RecyclerView
 */
public class PrayerTimeAdapter extends RecyclerView.Adapter<PrayerTimeAdapter.ViewHolder> {
    
    private final Context context;
    private PrayerTime prayerTime;
    private final List<PrayerTimeItem> prayerTimeItems = new ArrayList<>();
    
    public PrayerTimeAdapter(Context context) {
        this.context = context;
    }
    
    public void setPrayerTime(PrayerTime prayerTime) {
        this.prayerTime = prayerTime;
        updatePrayerTimeItems();
        notifyDataSetChanged();
    }
    
    private void updatePrayerTimeItems() {
        prayerTimeItems.clear();
        
        if (prayerTime == null) {
            return;
        }
        
        // Add all prayer times to the list
        prayerTimeItems.add(new PrayerTimeItem("Fajr", prayerTime.getFajr(), R.drawable.ic_fajr));
        prayerTimeItems.add(new PrayerTimeItem("Sunrise", prayerTime.getSunrise(), R.drawable.ic_sunrise));
        prayerTimeItems.add(new PrayerTimeItem("Dhuhr", prayerTime.getDohr(), R.drawable.ic_dhuhr));
        prayerTimeItems.add(new PrayerTimeItem("Asr", prayerTime.getAsr(), R.drawable.ic_asr));
        prayerTimeItems.add(new PrayerTimeItem("Maghrib", prayerTime.getMaghreb(), R.drawable.ic_maghrib));
        prayerTimeItems.add(new PrayerTimeItem("Isha", prayerTime.getIchaa(), R.drawable.ic_isha));
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prayer_time, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PrayerTimeItem item = prayerTimeItems.get(position);
        
        holder.tvPrayerName.setText(item.name);
        holder.tvPrayerTime.setText(item.time);
        
        // Calculate and display time remaining
        String timeRemaining = calculateTimeRemaining(item.time);
        holder.tvTimeRemaining.setText(timeRemaining);
        
        // Highlight current prayer time
        if (isCurrentPrayer(item.time)) {
            holder.itemView.setBackgroundResource(R.drawable.bg_current_prayer);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.bg_prayer_item);
        }
    }
    
    @Override
    public int getItemCount() {
        return prayerTimeItems.size();
    }
    
    /**
     * Calculate time remaining until prayer
     */
    private String calculateTimeRemaining(String prayerTimeStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date prayerTime = format.parse(prayerTimeStr);
            
            Calendar now = Calendar.getInstance();
            Calendar prayer = Calendar.getInstance();
            
            if (prayerTime != null) {
                prayer.set(Calendar.HOUR_OF_DAY, prayerTime.getHours());
                prayer.set(Calendar.MINUTE, prayerTime.getMinutes());
                prayer.set(Calendar.SECOND, 0);
                
                // If prayer time has passed today, set it for tomorrow
                if (now.after(prayer)) {
                    prayer.add(Calendar.DAY_OF_MONTH, 1);
                }
                
                // Calculate difference
                long diffMillis = prayer.getTimeInMillis() - now.getTimeInMillis();
                long diffHours = diffMillis / (60 * 60 * 1000);
                long diffMinutes = (diffMillis / (60 * 1000)) % 60;
                
                if (diffHours > 0) {
                    return diffHours + "h " + diffMinutes + "m";
                } else {
                    return diffMinutes + "m";
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return "";
    }
    
    /**
     * Check if this is the current prayer time
     */
    private boolean isCurrentPrayer(String prayerTimeStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date prayerTime = format.parse(prayerTimeStr);
            
            Calendar now = Calendar.getInstance();
            Calendar prayer = Calendar.getInstance();
            
            if (prayerTime != null) {
                prayer.set(Calendar.HOUR_OF_DAY, prayerTime.getHours());
                prayer.set(Calendar.MINUTE, prayerTime.getMinutes());
                
                // Find the next prayer time
                for (PrayerTimeItem item : prayerTimeItems) {
                    Date itemTime = format.parse(item.time);
                    if (itemTime != null) {
                        Calendar itemCal = Calendar.getInstance();
                        itemCal.set(Calendar.HOUR_OF_DAY, itemTime.getHours());
                        itemCal.set(Calendar.MINUTE, itemTime.getMinutes());
                        
                        if (now.before(itemCal)) {
                            // This is the next prayer
                            return item.time.equals(prayerTimeStr);
                        }
                    }
                }
                
                // If all prayers have passed, the first prayer of tomorrow is next
                return prayerTimeStr.equals(prayerTimeItems.get(0).time);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPrayerName;
        TextView tvPrayerTime;
        TextView tvTimeRemaining;
        
        ViewHolder(View itemView) {
            super(itemView);
            tvPrayerName = itemView.findViewById(R.id.tvPrayerName);
            tvPrayerTime = itemView.findViewById(R.id.tvPrayerTime);
            tvTimeRemaining = itemView.findViewById(R.id.tvTimeRemaining);
        }
    }
    
    /**
     * Helper class to hold prayer time data for the adapter
     */
    private static class PrayerTimeItem {
        String name;
        String time;
        int iconResId;
        
        PrayerTimeItem(String name, String time, int iconResId) {
            this.name = name;
            this.time = time;
            this.iconResId = iconResId;
        }
    }
}
