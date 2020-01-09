package com.example.spacerush;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.spacerush.model.User;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Stack;

public class HighScoresActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Stack<User> stack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        this.stack = new Stack<User>();

        // Set high scores table
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("players");
        final Query query = db.orderByChild("score");
        query.addListenerForSingleValueEvent(new TableValueEventListener(this));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    protected void populateTable() {
        User player;
        while (!stack.isEmpty()) {
            player = stack.pop();
            TableLayout tl = findViewById(R.id.highScoresTable);
            TableRow tr = new TableRow(getContext());

            TextView name = tableTextViewGenerator(player.getName());
            TextView scoreTv = tableTextViewGenerator(String.format("%8d", player.getScore()));
            TextView location = tableTextViewGenerator(String.format("%.3f, %.3f", player.getGeolocation()
                    .getLat(), player.getGeolocation().getLng()));

            tr.addView(name);
            tr.addView(scoreTv);
            tr.addView(location);
            tl.addView(tr);
        }
    }

    private TextView tableTextViewGenerator(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0f);
        tv.setPadding(3, 3, 3, 3);

        return tv;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("players");
        final Query query = db.orderByChild("score");

        query.addListenerForSingleValueEvent(new MapValueEventListener(this));
    }

    private Context getContext() {
        return this;
    }

    public Stack<User> getStack() {
        return stack;
    }

    public GoogleMap getmMap() {
        return mMap;
    }
}
