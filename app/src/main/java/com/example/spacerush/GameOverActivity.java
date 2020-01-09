package com.example.spacerush;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacerush.DAL.UserDAL;
import com.example.spacerush.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class GameOverActivity extends AppCompatActivity {

    private int score = 0;
    private User user;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get score from GameActivity
        Bundle b = getIntent().getExtras();
        if (b != null) {
            this.score = b.getInt("score");
        }

        setContentView(R.layout.activity_game_over);
        // Show score on the view
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText(scoreTextView.getText() + String.valueOf(score));

        // Read Current user details from file
        UserDAL dal = new UserDAL(null, this);
        user = dal.readFromFileToUser();
        // Set the user's score
        user.setScore(score);

        // Set the database reference and the query
        db = FirebaseDatabase.getInstance().getReference().child("players");
        final Query query = db.orderByChild("score");

        // Query for all the players in the high scores ordered by score
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get the first child which contains the player with the lowest score
                DataSnapshot child = dataSnapshot.getChildren().iterator().next();
                User last = child.getValue(User.class);
                // Check if the current user's score is higher
                if (score > last.getScore()) {
                    // Replace the value of the document with the current user's detailsS
                    db.child(child.getKey()).setValue(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("info", "High scores update canceled", databaseError.toException());
            }
        });
    }

    public void restartGame(View v) {
        Intent intent = new Intent(GameOverActivity.this, GameActivity.class);
        startActivity(intent);
    }
}
