package com.example.spacerush;

import android.graphics.Rect;

import java.util.Random;

public class Element {

    private int posX;
    private int posY;
    private int size;
    private int speed;

    public Element(float posY, int size, int speed) {
        this.posY = (int) posY;
        this.posX = 0;
        this.size = size;
        this.speed = speed;
    }

    public void update() {
        posY+=speed;
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

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void moveToRandX(int screenX, int numOfPaths) {
        posX = new Random().nextInt(numOfPaths) * (screenX / (numOfPaths - 1));
        if (posX >= screenX) {
            posX -= size;
        } else if (posX >= screenX / 2) {
            posX -= size / 2;
        }
    }

    public Rect getRect() {
        return new Rect(getPosX(), getPosY(), getPosX() + getSize(), getPosY() + getSize());
    }

}
