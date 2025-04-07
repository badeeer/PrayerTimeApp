package com.prayertime.app.model;

/**
 * Model class representing prayer times for a specific day
 */
public class PrayerTime {
    private String fajr;
    private String sunrise;
    private String dohr;
    private String asr;
    private String maghreb;
    private String ichaa;
    private int day;

    public PrayerTime() {
    }

    public PrayerTime(String fajr, String sunrise, String dohr, String asr, String maghreb, String ichaa, int day) {
        this.fajr = fajr;
        this.sunrise = sunrise;
        this.dohr = dohr;
        this.asr = asr;
        this.maghreb = maghreb;
        this.ichaa = ichaa;
        this.day = day;
    }

    public String getFajr() {
        return fajr;
    }

    public void setFajr(String fajr) {
        this.fajr = fajr;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getDohr() {
        return dohr;
    }

    public void setDohr(String dohr) {
        this.dohr = dohr;
    }

    public String getAsr() {
        return asr;
    }

    public void setAsr(String asr) {
        this.asr = asr;
    }

    public String getMaghreb() {
        return maghreb;
    }

    public void setMaghreb(String maghreb) {
        this.maghreb = maghreb;
    }

    public String getIchaa() {
        return ichaa;
    }

    public void setIchaa(String ichaa) {
        this.ichaa = ichaa;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "PrayerTime{" +
                "fajr='" + fajr + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", dohr='" + dohr + '\'' +
                ", asr='" + asr + '\'' +
                ", maghreb='" + maghreb + '\'' +
                ", ichaa='" + ichaa + '\'' +
                ", day=" + day +
                '}';
    }
}
