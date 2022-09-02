package com.example.my_application.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.my_application.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ArrayList barArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //setContentView(binding.getRoot());


        BarChart barChart = findViewById(R.id.barchart);

        getData();

        BarDataSet barDataSet = new BarDataSet(barArrayList, "Bill");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);
    }

    private void getData() {
        barArrayList = new ArrayList();
        barArrayList.add(new BarEntry(2f, 10));
        barArrayList.add(new BarEntry(3f, 20));
        barArrayList.add(new BarEntry(4f, 30));
        barArrayList.add(new BarEntry(5f, 40));
        barArrayList.add(new BarEntry(6f, 50));
    }
}