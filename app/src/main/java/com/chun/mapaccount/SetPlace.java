package com.chun.mapaccount;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.support.annotation.NonNull;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.orhanobut.logger.Logger;


/**
 * Created by Wayne on 2017/12/20.
 */

public class SetPlace extends FragmentActivity implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private Button finish_setplace;
    private GoogleMap mMap;
    private Marker myLocation;
    Marker layaBurger, familyMart, dingFong;
    LatLng latLng_laya, latLng_family, latLng_df, nowLocal;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
    private static final int LOCATION_UPDATE_MIN_DISTANCE = 1000;
    private static final int LOCATION_UPDATE_MIN_TIME = 50;
    private int requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_place);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);


        finish_setplace = (Button) findViewById(R.id.finish_setplace);
        finish_setplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng target = mMap.getCameraPosition().target;
                //Toast.makeText(SetPlace.this, "經度:" + target.longitude + ", 緯度:" + target.latitude, Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putDouble("longitude",target.longitude);
                bundle.putDouble("latitude",target.latitude);
                intent.putExtras(bundle);
                setResult(requestCode, intent); //requestCode需跟A.class的一樣
                SetPlace.this.finish();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        nowLocal = new LatLng(25.035810, 121.513746);
        latLng_laya = new LatLng(25.037070, 121.514477);
        latLng_family = new LatLng(25.037009, 121.514254);
        latLng_df = new LatLng(25.037036, 121.514620);
        layaBurger = mMap.addMarker(new MarkerOptions().position(latLng_laya).title("拉亞漢堡"));
        familyMart = mMap.addMarker(new MarkerOptions().position(latLng_family).title("全家就是你家"));
        dingFong = mMap.addMarker(new MarkerOptions().position(latLng_df).title("鼎豐快餐"));
        myLocation = mMap.addMarker(new MarkerOptions().position(nowLocal).title("目前選擇地點"));
//        layaBurger.showInfoWindow();
//        familyMart.showInfoWindow();
//        dingFong.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation.getPosition(), 17));
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                myLocation.setPosition(mMap.getCameraPosition().target);
            }
        });
        mMap.setOnMarkerClickListener(SetPlace.this);
        mMap.setOnInfoWindowClickListener(SetPlace.this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        if (marker.equals(layaBurger)) {
            bundle.putDouble("longitude",latLng_laya.longitude);
            bundle.putDouble("latitude",latLng_laya.latitude);
        }else if(marker.equals(familyMart)){
            bundle.putDouble("longitude",latLng_family.longitude);
            bundle.putDouble("latitude",latLng_family.latitude);
        }else if(marker.equals(dingFong)){
            bundle.putDouble("longitude",latLng_df.longitude);
            bundle.putDouble("latitude",latLng_df.latitude);
        }else if(marker.equals(myLocation)){
            bundle.putDouble("longitude", nowLocal.longitude);
            bundle.putDouble("latitude", nowLocal.latitude);
        }
        intent.putExtras(bundle);
        setResult(requestCode, intent); //requestCode需跟A.class的一樣
        SetPlace.this.finish();
    }


    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return true;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog.newInstance(true).show(getSupportFragmentManager(), "dialog");
    }
}
