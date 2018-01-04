package com.chun.mapaccount;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Year_coast_view extends Fragment {
    private static final String TAG = "Tab1Fragment";

    private Button btnTEST;
    private TextView dateText;
    private ImageButton btnleft;
    private ImageButton btnright;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    private ListView coast_listview,income_listview;
    private TextView all_coast_text,all_income_text,balance_text;
    ArrayAdapter adapter;ArrayAdapter adapter2;
    MyDB myDb;
    Cursor res;
    String s ="";
    Cursor res2;
    private double allcoast,allincome,allbalance;
    DecimalFormat df = new DecimalFormat("#.#");
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.coast_view,container,false);
        btnTEST = (Button) view.findViewById(R.id.btnTEST);
        dateText = (TextView) view.findViewById(R.id.dateText);
        btnleft = (ImageButton) view.findViewById(R.id.btnleft);
        btnright = (ImageButton) view.findViewById(R.id.btnright);
        dateText.setText(getToday());
        dateText.setEnabled(false);

        all_coast_text = (TextView) view.findViewById(R.id.all_coast_text);
        all_income_text = (TextView) view.findViewById(R.id.all_income_text);
        balance_text = (TextView) view.findViewById(R.id.balance_text);
        coast_listview = (ListView) view.findViewById(R.id.coast_listview);
        income_listview = (ListView) view.findViewById(R.id.income_listview);
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.simple_list_item_1);
        coast_listview.setAdapter(adapter);
        adapter2 = new ArrayAdapter<String>(getActivity(),R.layout.simple_list_item_1);
        income_listview.setAdapter(adapter2);

        btnleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDate(-1);
                adapter.clear();
                adapter2.clear();
                listview_show();
                all_text();
            }
        });
        btnright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDate(1);
                adapter.clear();
                adapter2.clear();
                listview_show();
                all_text();
            }
        });
        btnTEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AddItem.class);
                startActivity(intent);
                Toast.makeText(getActivity(), getString(R.string.go_new),Toast.LENGTH_SHORT).show();
            }
        });
        myDb = new MyDB(getActivity());
        listview_show();
        all_text();
        return view;
    }


    private String getToday() {
        return sdf.format(Calendar.getInstance().getTime());
    }
    private void changeDate(int days) {
        Calendar c = Calendar.getInstance();
        String nowDate = dateText.getText().toString();
        try {
            c.setTime(sdf.parse(nowDate));
            c.add(Calendar.YEAR, days);
            dateText.setText(sdf.format(c.getTime()));
        } catch (Exception e) {
            dateText.setText(getToday());
        }
    }
    private void listview_show(){
        res = myDb.getCoastData();
        res2 = myDb.getIncomeData();
        String nowdate = dateText.getText().toString();
        String[] split_date = nowdate.split("/");
        String[] split_dbdate;
        int flag;
        while (res.moveToNext()) {
            String s2 = res.getString(1);
            split_dbdate = s2.split("/");
            flag = split_date[0].compareTo(split_dbdate[0]);
            if(flag==0){
                s = "   "+res.getString(3)+"              $  "+ res.getDouble(2);
                adapter.add(s);
                adapter.notifyDataSetChanged();
            }
        }
        while (res2.moveToNext()) {
            String s2 = res2.getString(1);
            split_dbdate = s2.split("/");
            flag = split_date[0].compareTo(split_dbdate[0]);
            if(flag==0){
                s = "   "+res2.getString(3)+"              $  "+ res2.getDouble(2);
                adapter2.add(s);
                adapter2.notifyDataSetChanged();
            }
        }
    }
    private void all_text(){
        res = myDb.getCoastData();
        res2 = myDb.getIncomeData();
        String nowdate = dateText.getText().toString();
        String[] split_date = nowdate.split("/");
        String[] split_dbdate;
        int flag;
        while (res.moveToNext()) {
            String s2 = res.getString(1);
            split_dbdate = s2.split("/");
            flag = split_date[0].compareTo(split_dbdate[0]);
            if(flag==0){
                allcoast = allcoast + res.getDouble(2);
            }
        }
        while (res2.moveToNext()) {
            String s2 = res2.getString(1);
            split_dbdate = s2.split("/");
            flag = split_date[0].compareTo(split_dbdate[0]);
            if(flag==0){
                allincome = allincome + res2.getDouble(2);
            }
        }
        allbalance = allincome-allcoast;
        allcoast = Double.parseDouble(df.format(allcoast));
        allincome = Double.parseDouble(df.format(allincome));
        allbalance = Double.parseDouble(df.format(allbalance));
        all_coast_text.setText(allcoast+"");
        all_income_text.setText(allincome+"");
        balance_text.setText(allbalance+"");
        allbalance=0;
        allincome=0;
        allcoast=0;
    }
}