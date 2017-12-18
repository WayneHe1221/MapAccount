package com.chun.mapaccount;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class In_putFragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    private Button btnTEST;
    private TextView dateText;
    private ImageButton btnleft;
    private ImageButton btnright;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.in_putfragment,container,false);
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
                Toast.makeText(getActivity(), getString(R.string.new_finish),Toast.LENGTH_SHORT).show();
            }
        });
        mViewPager = (ViewPager) view.findViewById(R.id.coast_container);
        tabLayout = (TabLayout) view.findViewById(R.id.day_tabs);
        initdata(mViewPager);
        tabLayout.setupWithViewPager(mViewPager);

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
    private void initdata(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new Day_coast_view(),getString(R.string.year));
        adapter.addFragment(new Day_coast_view(), getString(R.string.month));
        adapter.addFragment(new Day_coast_view(),getString(R.string.day));
        viewPager.setAdapter(adapter);
    }
}