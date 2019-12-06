package com.example.spacerush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class GameActivity extends AppCompatActivity implements GameOverListener {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        gameView = new GameView(this, size);
        gameView.setListener(this);
        setContentView(gameView);
    }

    // Start the thread in snakeEngine
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    // Stop the thread in snakeEngine
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    public void onGameOver(int score) {
        Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
    }
}
