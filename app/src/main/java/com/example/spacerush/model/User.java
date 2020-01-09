package com.example.spacerush.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    private String name;
    private Geolocation geolocation;
    private int score;

    public User() {
        this.name = null;
        this.geolocation = null;
        this.score = 0;
    }

    public User(String name, Geolocation geolocation, int score) {
        this.name = name;
        this.geolocation = geolocation;
        this.score = score;
    }

    public User(String name, Geolocation geolocation) {
        this.name = name;
        this.geolocation = geolocation;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
