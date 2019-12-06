package com.example.spacerush;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;
import java.util.Timer;

public class GameView extends SurfaceView implements Runnable {

    private Thread gameThread;
    private Context context;

    private Player player;
    private Enemy[] enemies;
    private Enemy lockedEnemy;

    private int screenX;
    private int screenY;
    private int surfaceY;

    private final long FPS = 60;
    private final long MILLIS_PER_SECOND = 1000;

    private int score;

    private volatile boolean isPlaying;
    private volatile boolean lock;

    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Paint paint;

    public final int PLAYER_SIZE = 100;
    public final int ENEMY_SIZE = 150;
    public final int PADDING_Y = 300;
    public final int NUM_OF_PATHS = 3;
    public final int ENEMIES_PADDING = 500;
    public final int ENEMIES_SPEED = 10;

    private GameOverListener listener;

    public GameView(Context context, Point size) {
        super(context);

        this.context = context;
        this.lock = false;
        this.lockedEnemy = null;

        screenX = size.x;
        screenY = size.y;

        float startingX = (screenX - PLAYER_SIZE) / 2;
        surfaceY = screenY - PADDING_Y;
        player = new Player(startingX, surfaceY, PLAYER_SIZE);

        enemies = new Enemy[NUM_OF_PATHS];
        for (int i=0; i<NUM_OF_PATHS; i++) {
            int enemyStartingY = -(ENEMIES_PADDING + (i * ENEMIES_PADDING));
            enemies[i] = new Enemy(enemyStartingY, ENEMY_SIZE, ENEMIES_SPEED);
            enemies[i].moveToRandX(screenX, NUM_OF_PATHS);
        }

        surfaceHolder = getHolder();
        paint = new Paint();
    }

    @Override
    public void run() {
        while (isPlaying) {
            control();
            update();
            draw();
        }
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            // Background
            canvas.drawColor(Color.BLACK);

            // Score & lives
            paint.setColor(Color.WHITE);
            paint.setTextSize(50f);
            canvas.drawText("Score: " + score, 50, 50, paint);
            canvas.drawText("Lives: " + player.getLives(), screenX - 500, 50, paint);

            // Player
            paint.setColor(Color.BLUE);
            canvas.drawRect(player.getRect(), paint);

            // Enemies
            paint.setColor(Color.RED);
            for (int i=0; i<enemies.length; i++) {
                canvas.drawRect(enemies[i].getRect(), paint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void update() {
        score++;

        player.update();

        for (int i=0; i<enemies.length; i++) {
            enemies[i].update();

            // Set enemies position back to top
            if (enemies[i].getPosY() >= screenY) {
                enemies[i].setPosY(-ENEMIES_PADDING);
                enemies[i].moveToRandX(screenX, NUM_OF_PATHS);
            }
        }

        // Release collision lock
        if (lockedEnemy != null && lockedEnemy.getPosY() < 0) {
            lockedEnemy = null;
            lock = false;
        }
    }

    private void control() {
        try {
            // Check for impact
            for (int i=0; i<enemies.length; i++) {
                if (Rect.intersects(enemies[i].getRect(), player.getRect()) && !lock) {
                    lock = true;
                    lockedEnemy = enemies[i];
                    player.collision();

                    if (player.getLives() == 0) {
                        isPlaying = false;
                        listener.onGameOver(score);
                    }
                }
            }
            gameThread.sleep(MILLIS_PER_SECOND / FPS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setListener(GameOverListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:

                if(event.getX() < screenX / 2) {
                    // Move left
                    if (player.getPosX() > 0 + player.getSize()) {
                        player.move(-(screenX / NUM_OF_PATHS) - PLAYER_SIZE);
                    }
                } else {
                    // Move right
                    if (player.getPosX() + player.getSize() < screenX - PLAYER_SIZE) {
                        player.move((screenX / NUM_OF_PATHS) + PLAYER_SIZE);
                    }
                }

                break;
        }

        return true;
    }

    public void pause() {
        isPlaying = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void resume() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

}
