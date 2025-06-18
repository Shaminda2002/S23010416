package com.s23010416;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import java.io.IOException;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    private EditText textInputEditText;
    private Button button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapactivity); // note: this is activity_map.xml

        textInputEditText = findViewById(R.id.textInputEditText);
        button = findViewById(R.id.btn);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);

        button.setOnClickListener(v -> {
            String address = textInputEditText.getText().toString().trim();
            if (!address.isEmpty()) {
                geocodeAddress(address);
            } else {
                Toast.makeText(MapActivity.this, "Please enter an address", Toast.LENGTH_SHORT).show();
            }
        });
        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(v -> {
            Intent intent = new Intent(MapActivity.this, SensorActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
        myMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng openUniversity = new LatLng(6.883023889374675, 79.88660986931379);
        myMap.addMarker(new MarkerOptions().position(openUniversity).title("Open University"));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(openUniversity, 15.0f));
    }

    private void geocodeAddress(String address) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address location = addresses.get(0);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                LatLng latLng = new LatLng(latitude, longitude);
                myMap.clear();
                myMap.addMarker(new MarkerOptions().position(latLng).title("Location"));
                myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));

                Log.i("GOOGLE_MAP_TAG", "Latitude: " + latitude + ", Longitude: " + longitude);
            } else {
                Toast.makeText(this, "Address not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}