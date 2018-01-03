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

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Day_coast_view extends Fragment {
    private static final String TAG = "Tab1Fragment";

    private Button btnTEST;
    private TextView dateText;
    private ImageButton btnleft;
    private ImageButton btnright;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private ListView coast_listview,income_listview;
    private TextView all_coast_text,all_income_text,balance_text;
    ArrayAdapter adapter;ArrayAdapter adapter2;
    MyDB myDb;
    Cursor res;
    String s ="";
    Cursor res2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.coast_view,container,false);
        btnTEST = (Button) view.findViewById(R.id.btnTEST);
        dateText = (TextView) view.findViewById(R.id.dateText);
        btnleft = (ImageButton) view.findViewById(R.id.btnleft);
        btnright = (ImageButton) view.findViewById(R.id.btnright);

        coast_listview = (ListView) view.findViewById(R.id.coast_listview);
        income_listview = (ListView) view.findViewById(R.id.income_listview);
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.simple_list_item_1);
        coast_listview.setAdapter(adapter);
        adapter2 = new ArrayAdapter<String>(getActivity(),R.layout.simple_list_item_1);
        income_listview.setAdapter(adapter2);

        dateText.setText(getToday());
        dateText.setEnabled(false);

        btnleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDate(-1);
            }
        });
        btnright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDate(1);
            }
        });
        btnTEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AddItem.class);
                startActivity(intent);
                getActivity().finish();
                Toast.makeText(getActivity(), getString(R.string.go_new),Toast.LENGTH_SHORT).show();
            }
        });
        all_coast_text = (TextView) view.findViewById(R.id.all_coast_text);
        all_income_text = (TextView) view.findViewById(R.id.all_income_text);
        myDb = new MyDB(getActivity());

        res = myDb.getCoastData();
        while (res.moveToNext()) {
                s = "   "+res.getString(3)+"              $  "+ res.getDouble(2);
                adapter.add(s);
                adapter.notifyDataSetChanged();
        }
        res2 = myDb.getIncomeData();
        while (res2.moveToNext()) {
            s = "   "+res2.getString(3)+"              $  "+ res2.getDouble(2);
            adapter2.add(s);
            adapter2.notifyDataSetChanged();
        }
        all_coast_text.setText(myDb.getDBcount()+"");
        all_income_text.setText(myDb.getDBcount2()+"");
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
            c.add(Calendar.DATE, days);
            dateText.setText(sdf.format(c.getTime()));
        } catch (Exception e) {
            dateText.setText(getToday());
        }
    }

}

