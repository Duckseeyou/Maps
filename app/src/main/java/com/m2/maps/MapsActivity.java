package com.m2.maps;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import android.widget.Toast;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.m2.maps.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMyLocationButtonClickListener, OnMyLocationClickListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;
    private GoogleMap gMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        LatLng victory = new LatLng(42.87919900019253, 74.6183660989834);
        gMap.addMarker(new MarkerOptions().position(victory).title("Victory"));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(victory, 18),1000, null );
        gMap.setOnMyLocationClickListener(this);
        accessMyLocation();
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(false);
    }

    @SuppressLint("MissingPermission")
    private void accessMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            gMap.setMyLocationEnabled(true);
        } else {
            PermissionUtils.requestLocationPermissions(MapsActivity.this, LOCATION_PERMISSION_REQUEST_CODE, true);
        }

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG)
                .show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION) || PermissionUtils
                .isPermissionGranted(permissions, grantResults,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
            accessMyLocation();
        } else {
            permissionDenied = true;
        }
    }
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            PermissionUtils.PermissionDeniedDialog
                    .newInstance(true).show(getSupportFragmentManager(), "dialog");
            permissionDenied = false;
        }


    }


}