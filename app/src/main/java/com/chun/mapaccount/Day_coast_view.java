package com.chun.mapaccount;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
                Toast.makeText(getActivity(), getString(R.string.go_new),Toast.LENGTH_SHORT).show();
            }
        });

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

