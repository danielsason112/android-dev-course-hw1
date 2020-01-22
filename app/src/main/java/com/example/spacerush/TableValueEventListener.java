package com.example.spacerush;

import androidx.annotation.NonNull;

import com.example.spacerush.model.HighScores;
import com.example.spacerush.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class TableValueEventListener implements ValueEventListener {

    private HighScoresActivity activity;

    public TableValueEventListener(HighScoresActivity activity) {
        super();
        this.activity = activity;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot player : dataSnapshot.getChildren()) {
            activity.getStack().push(player.getValue(User.class));
        }

        if (activity.getStack().size() == HighScores.NUM_OF_RECORDS) {
            activity.populateTable();
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
