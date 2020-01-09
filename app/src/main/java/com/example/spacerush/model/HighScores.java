package com.example.spacerush.model;

public class HighScores {

    public static final int NUM_OF_RECORDS = 10;

    private User[] users;

    public HighScores() {
        this.users = new User[NUM_OF_RECORDS];
    }
}
