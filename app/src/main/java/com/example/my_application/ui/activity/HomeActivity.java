package com.example.my_application.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.my_application.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    ArrayList barArrayList;
    private Button logout;
    private LinearLayout financeBtn;
    private LinearLayout roommateBtn;
    private LinearLayout waterBtn;
    private LinearLayout electricityBtn;
    private LinearLayout homeBtn;
    private LinearLayout profileBtn;
    private LinearLayout settingBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        financeBtn = (LinearLayout) findViewById(R.id.layoutFinance);
        financeBtn.setOnClickListener(this);

        roommateBtn = (LinearLayout) findViewById(R.id.layoutRoommate);
        roommateBtn.setOnClickListener(this);

        waterBtn = (LinearLayout) findViewById(R.id.layoutWater);
        waterBtn.setOnClickListener(this);

        electricityBtn = (LinearLayout) findViewById(R.id.layoutElectricity);
        electricityBtn.setOnClickListener(this);

        homeBtn = (LinearLayout) findViewById(R.id.layoutHome);
        homeBtn.setOnClickListener(this);

        profileBtn = (LinearLayout) findViewById(R.id.layoutProfile);
        profileBtn.setOnClickListener(this);

        settingBtn = (LinearLayout) findViewById(R.id.layoutSetting);
        settingBtn.setOnClickListener(this);
/*
        //logout function
        logout = (Button) findViewById(R.id.logOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });
*/
        //barchart view
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layoutFinance:
                startActivity(new Intent(HomeActivity.this, FinanceActivity.class));
                break;
            case R.id.layoutRoommate:
                startActivity(new Intent(HomeActivity.this, RoommateActivity.class));
                break;
            case R.id.layoutWater:
                startActivity(new Intent(HomeActivity.this, WaterActivity.class));
                break;
            case R.id.layoutElectricity:
                startActivity(new Intent(HomeActivity.this, ElectricityActivity.class));
                break;
            case R.id.layoutHome:
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                break;
            case R.id.layoutProfile:
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                break;
            case R.id.layoutSetting:
                startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                break;
        }
    }
}