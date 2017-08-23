package com.jdkgroup.pms.activity;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jdkgroup.customviews.GPSTracker;
import com.jdkgroup.pms.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSTracker gpsTracker;
    private Location mLocation;
    private double latitude;
    private double longitude;
    private LatLng currentLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        gpsTracker = new GPSTracker(getApplicationContext());
        if (gpsTracker != null) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mLocation = gpsTracker.getLocation();
            latitude = mLocation.getLatitude();
            longitude = mLocation.getLongitude();

            currentLatLng = new LatLng(latitude, longitude);
        }

        // Add a marker in Sydney and move the camera
        if(currentLatLng != null) {
            mMap.addMarker(new MarkerOptions().position(currentLatLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
        }
    }
}
