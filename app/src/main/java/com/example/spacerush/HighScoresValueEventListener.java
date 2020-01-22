package com.example.spacerush;

import android.util.Log;
import android.widget.TextView;

import com.example.spacerush.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class HighScoresValueEventListener implements ValueEventListener {

    private GameOverActivity activity;

    public HighScoresValueEventListener(GameOverActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // Get the first child which contains the player with the lowest score
        DataSnapshot child = dataSnapshot.getChildren().iterator().next();
        User last = child.getValue(User.class);
        // Check if the current user's score is higher
        if (activity.getScore() > last.getScore()) {
            // Replace the value of the document with the current user's detailsS
            activity.getDb().child(child.getKey()).setValue(activity.getUser());
            activity.setNewHighScore((TextView) activity.findViewById(R.id.newHighScore));
            activity.getNewHighScore().setText("A New High Score!");
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.w("info", "High scores update canceled", databaseError.toException());
    }

}
