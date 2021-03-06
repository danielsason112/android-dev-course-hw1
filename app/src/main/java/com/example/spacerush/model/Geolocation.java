package com.example.spacerush.model;

public class Geolocation {

    private double lat;
    private double lng;

    public static final double DEFAULT_LAT = 50.0;
    public static final double DEFAULT_LNG = -50.0;

    public Geolocation() {
    }

    public Geolocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
