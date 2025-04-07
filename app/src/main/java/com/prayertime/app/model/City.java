package com.prayertime.app.model;

/**
 * Model class representing a city in Morocco
 */
public class City {
    private int id;
    private String nameFR;
    private String nameAR;

    public City() {
    }

    public City(int id, String nameFR, String nameAR) {
        this.id = id;
        this.nameFR = nameFR;
        this.nameAR = nameAR;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameFR() {
        return nameFR;
    }

    public void setNameFR(String nameFR) {
        this.nameFR = nameFR;
    }

    public String getNameAR() {
        return nameAR;
    }

    public void setNameAR(String nameAR) {
        this.nameAR = nameAR;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", nameFR='" + nameFR + '\'' +
                ", nameAR='" + nameAR + '\'' +
                '}';
    }
}
