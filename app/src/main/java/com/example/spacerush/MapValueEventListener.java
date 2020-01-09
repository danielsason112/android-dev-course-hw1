package com.example.spacerush;

import androidx.annotation.NonNull;

import com.example.spacerush.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MapValueEventListener implements ValueEventListener {

    private HighScoresActivity activity;

    public MapValueEventListener(HighScoresActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            User player = child.getValue(User.class);
            LatLng location = new LatLng(player.getGeolocation().getLat(),
                    player.getGeolocation().getLng());
            activity.getmMap().addMarker(new MarkerOptions().position(location).title(player.getName()));
            activity.getmMap().moveCamera(CameraUpdateFactory.newLatLng(location));
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

}
