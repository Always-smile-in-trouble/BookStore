package com.bookstore.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bookstore.MyApplication;
import com.bookstore.R;
import com.bookstore.databinding.GoogleMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private String userId;
    private GoogleMapBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_map);
        mapFragment.getMapAsync(this);

        setupBottomNavigation();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng location = new LatLng(10.8411276, 106.809883);
        googleMap.addMarker(new MarkerOptions().position(location).title("FPT University"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_location);
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            // Handle navigation based on the selected item
            if ( itemId == R.id.nav_location) {
                return true;
            } else if (itemId == R.id.nav_home) {
                Intent locationIntent = new Intent(GoogleMapActivity.this, HomePageActivity.class);
                startActivity(locationIntent);
                return true;
            } else if (itemId == R.id.nav_explore) {
                // Handle navigation to Explore/Search
                Intent exploreIntent = new Intent(GoogleMapActivity.this, SearchBookActivity.class);
                startActivity(exploreIntent);
                return true;
            } else if (itemId == R.id.nav_login) {
                // Retrieve userId from MyApplication or SharedPreferences
                userId = MyApplication.getUserId();
                if (userId == null || userId.isEmpty()) {
                    Intent loginIntent = new Intent(GoogleMapActivity.this, AuthActivity.class);
                    startActivity(loginIntent);
                } else {
                    Intent profileIntent = new Intent(GoogleMapActivity.this, ProfileActivity.class);
                    startActivity(profileIntent);
                }
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null; // Prevent memory leaks
    }
}