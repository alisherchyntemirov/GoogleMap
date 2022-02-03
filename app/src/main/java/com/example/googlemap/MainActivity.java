package com.example.googlemap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.googlemap.databinding.ActivityMainBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    ActivityMainBinding binding;
    private GoogleMap gMap;
    private ArrayList<LatLng> markerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpListeners();
        createMap();

    }

    private void setUpListeners() {
        binding.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gMap !=null) {
                    gMap.clear();

                }
            }
        });
        binding.btnPolyline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (markerList.size()>1 && gMap !=null) {

                    gMap.addPolyline(

                            new PolylineOptions()
                            .color(Color.BLUE)
                    );
                }
            }
        });
    }

    private void createMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        MarkerOptions markerOptions = new MarkerOptions();

        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                markerOptions.position(latLng);
                gMap.clear();
                gMap.addMarker(markerOptions);
                markerList.add(latLng);
                gMap.animateCamera(
                        CameraUpdateFactory.newCameraPosition(
                                CameraPosition.builder()
                                        .zoom(5f)
                                        .target(latLng)
                                        .tilt(0f)
                                        .build()
                        )
                );
            }
        });
    }
}