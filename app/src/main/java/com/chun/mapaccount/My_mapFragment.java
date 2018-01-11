package com.chun.mapaccount;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class My_mapFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "Tab1Fragment";

    private GoogleMap mMap;
    private MapView mMapView;
    private View mView;
//    Marker layaBurger, familyMart, dingFong;
//    LatLng latLng_laya, latLng_family, latLng_df;
    private Button btnTEST;
    private ArrayAdapter adapter;
    private ListView cost_show;
    private Cursor res;
    MyDB myDb;
    String s = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.my_mapfragment, container, false);

        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.map);
        cost_show=(ListView) view.findViewById(R.id.cost_show);

        adapter = new ArrayAdapter<String>(getActivity(),R.layout.simple_list_item_1);
        cost_show.setAdapter(adapter);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
//        res = myDb.getCoastData();
//        while (res.moveToNext()) {
//            s = res.getString(3)+" :    $  "+ res.getDouble(2)+"經度"+res.getDouble(4)+ "緯度"+res.getDouble(5) ;
//            adapter.add(s);
//            adapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mMap = googleMap;
        LatLng utaipei = new LatLng(25.035810, 121.513746);
//        latLng_laya = new LatLng(25.037070, 121.514477);
//        latLng_family = new LatLng(25.037009, 121.514254);
//        latLng_df = new LatLng(25.037036, 121.514620);
//        layaBurger = mMap.addMarker(new MarkerOptions().position(latLng_laya).title("拉亞漢堡"));
//        familyMart = mMap.addMarker(new MarkerOptions().position(latLng_family).title("全家就是你家"));
//        dingFong = mMap.addMarker(new MarkerOptions().position(latLng_df).title("鼎豐快餐"));
        mMap.addMarker(new MarkerOptions().position(utaipei).title("天大地大北市大"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(utaipei, 17));
    }

    public void createMarker(){

    }

}
