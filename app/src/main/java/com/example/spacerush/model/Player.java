package com.example.spacerush.model;

import android.graphics.Rect;

public class Player {

    private int posX;
    private int posY;
    private int size;
    private int lives;

    public Player(float startingX, float startingY, int size) {
        this.posX = (int) startingX;
        this.posY = (int) startingY;
        this.size = size;
        this.lives = 3;
    }

    public void update(){

    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getSize() {
        return size;
    }

    public int getLives() {
        return lives;
    }

    public void collision() {
        lives--;
    }

    public Rect getRect() {
        return new Rect(posX, posY, posX + size, posY + size);
    }

    public void move(int newX) {
        posX += newX;
    }
}
