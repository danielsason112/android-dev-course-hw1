package com.example.spacerush;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacerush.DAL.UserDAL;
import com.example.spacerush.model.Geolocation;
import com.example.spacerush.model.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class WelcomeActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private Geolocation geo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Get the last known location of the user.
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            geo = new Geolocation(location.getLatitude(), location.getLongitude());
                        }
                    }
                });


    }

    public void enter(View v) {
        LayoutInflater factory = getLayoutInflater();
        View textEntryView = factory.inflate(R.layout.activity_welcome, null);

        // Get name from the TextEdit
        EditText nameInput = textEntryView.findViewById(R.id.nameInput);
        String name = nameInput.getText().toString();

        // Write user details to file
        User user = new User(name, geo);
        UserDAL dal = new UserDAL(user, this);
        dal.writeToFile();

        // Start MainActivity
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
