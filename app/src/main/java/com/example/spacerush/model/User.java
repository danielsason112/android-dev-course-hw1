package com.example.spacerush.model;

public class User {

    private String ID;
    private String name;
    private Geolocation geolocation;
    private int score;

    public User(String ID, String name, Geolocation geolocation, int score) {
        this.ID = ID;
        this.name = name;
        this.geolocation = geolocation;
        this.score = score;
    }

    public User(String name, Geolocation geolocation) {
        this.name = name;
        this.geolocation = geolocation;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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
