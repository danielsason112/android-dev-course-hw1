package com.example.spacerush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        int score = 0;
        if (b != null) {
            score = b.getInt("score");
        }
        setContentView(R.layout.activity_game_over);
        TextView scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        scoreTextView.setText(scoreTextView.getText() + String.valueOf(score));
    }

    public void restartGame(View v) {
        Intent intent = new Intent(GameOverActivity.this, GameActivity.class);
        startActivity(intent);
    }
}
