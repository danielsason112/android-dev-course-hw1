package com.example.spacerush;

import android.content.Context;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.spacerush.model.HighScores;
import com.example.spacerush.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Stack;

public class HighScoresActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("players");
        final Query query = db.orderByChild("score");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Stack<User> stack = new Stack<User>();
                for (DataSnapshot player : dataSnapshot.getChildren()) {
                    stack.push(player.getValue(User.class));

                }

                if (stack.size() == HighScores.NUM_OF_RECORDS) {
                    User player;
                    while (!stack.isEmpty()) {
                        player = stack.pop();
                        TableLayout tl = findViewById(R.id.highScoresTable);
                        TableRow tr = new TableRow(getContext());
                        TextView name = new TextView(getContext());
                        name.setText(player.getName());
                        TextView scoreTv = new TextView(getContext());
                        scoreTv.setText(String.format("%8d", player.getScore()));
                        TextView location = new TextView(getContext());
                        location.setText(String.format("%.3f, %.3f", player.getGeolocation().getLat(), player.getGeolocation().getLng()));
                        tr.addView(name);
                        tr.addView(scoreTv);
                        tr.addView(location);
                        tl.addView(tr);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private Context getContext() {
        return this;
    }
}
