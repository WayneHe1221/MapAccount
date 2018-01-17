package com.chun.mapaccount;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Arrays;
import java.util.Collections;

public class My_mapFragment extends DialogFragment implements OnMapReadyCallback {
    private static final String TAG = "Tab1Fragment";

    private GoogleMap mMap;
    private MapView mMapView;
    private View mView;
//    Marker layaBurger, familyMart, dingFong;
//    LatLng latLng_laya, latLng_family, latLng_df;
    private Button btnTEST;
    private LinearLayout cost_show;
    private Cursor res;
    MyDB myDb ;
    String s = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.my_mapfragment, container, false);
        cost_show=(LinearLayout) mView.findViewById(R.id.cost_show);
        myDb = new MyDB(getActivity());
        show_cost_to_click();
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.map);


        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

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
    private void show_cost_to_click(){
        res = myDb.getCoastData();
        int[] datearrary= new int[100];
        int count = 0;
        boolean putid = false;
        int[] idarrary = new int[100];
        String[] splitarrary = new String[3];
        for (int i = cost_show.getChildCount(); i >= 0; i--) {
            cost_show.removeView(cost_show.getChildAt(i));
        }
        while (res.moveToNext()) {
            String s2 = res.getString(1);
            splitarrary = s2.split("/");
            int year = Integer.parseInt(splitarrary[0]);
            int month = Integer.parseInt(splitarrary[1]);
            int day = Integer.parseInt(splitarrary[2]);
            datearrary[count]=year*10000+month*100+day;
            count++;
        }
        Arrays.sort(datearrary);
        count=0;
        for(int i=datearrary.length;i>0;i--){
            int k = datearrary[i-1];
            res = myDb.getCoastData();
            while (res.moveToNext()){
                String s2 = res.getString(1);
                splitarrary = s2.split("/");
                int year = Integer.parseInt(splitarrary[0]);
                int month = Integer.parseInt(splitarrary[1]);
                int day = Integer.parseInt(splitarrary[2]);
                int date=year*10000+month*100+day;
                if(date == k){
                    putid = true;
                    for(int j = 0 ; j < idarrary.length;j++){
                        if(idarrary[j]==res.getInt(0)){
                            putid = false;
                        }
                    }
                    if(putid){
                        idarrary[count]=res.getInt(0);
                        putid = false;
                        count++;
                    }
                }
            }
        }
        for(int i = 0 ; i<idarrary.length;i++){
            if(idarrary[i]==0){}
            res = myDb.getCoastData();
            while (res.moveToNext()) {
                if(idarrary[i]==res.getInt(0)){
                    add_table_show(res.getString(1),res.getString(3),res.getDouble(2),res.getString(6));
                }
            }
        }
    }
    private void add_table_show(String datee,String itemm, double numm,String placee) {
        final String date = datee;
        final String item = itemm;
        final double num = numm;
        final String place = placee;
        LinearLayout tr = new LinearLayout(getContext());
        tr.setClickable(true);
        tr.setOrientation(LinearLayout.VERTICAL);
        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),item+"  $ "+num, Toast.LENGTH_SHORT).show();
                createMarker();
            }
        });
        tr.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                60));
        LinearLayout ttr1 = new LinearLayout(getContext());
        ttr1.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                60));
        LinearLayout ttr2 = new LinearLayout(getContext());
        ttr2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                60));
                        /* Create a Button to be the row-content. */
        TextView tv0 = new TextView(getContext());
        tv0.setTextSize(20);
        tv0.setText(date);
        tv0.setTextColor(getResources().getColor(R.color.holo_blue_dark));
        tv0.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT,1));
        TextView tv1 = new TextView(getContext());
        tv1.setTextSize(20);
        tv1.setText(item);
        tv1.setTextColor(getResources().getColor(R.color.holo_blue_dark));
        tv1.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT,1));
        TextView tv2 = new TextView(getContext());
        tv2.setTextSize(20);
        tv2.setText("$ "+num);
        tv2.setTextColor(getResources().getColor(R.color.holo_blue_dark));
        tv2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT,1));
        TextView tv3 = new TextView(getContext());
        tv3.setTextSize(20);
        tv3.setText("地點:  "+place);
        tv3.setTextColor(getResources().getColor(R.color.colorAccent));
        tv3.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT,1));
                        /* Add Button to row. */
        ttr1.addView(tv0);
        ttr1.addView(tv1);
        ttr1.addView(tv2);
        ttr2.addView(tv3);
        tr.addView(ttr1);
        tr.addView(ttr2);
              /* Add row to TableLayout. */
        cost_show.addView(tr,new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                120));
    }
}
