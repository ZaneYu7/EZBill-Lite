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

    private LinearLayout financeBtn;
    private LinearLayout roommateBtn;
    private LinearLayout bluetoothBtn;
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

        bluetoothBtn = (LinearLayout) findViewById(R.id.layoutBluetooth);
        bluetoothBtn.setOnClickListener(this);

        electricityBtn = (LinearLayout) findViewById(R.id.layoutElectricity);
        electricityBtn.setOnClickListener(this);

        homeBtn = (LinearLayout) findViewById(R.id.layoutHome);
        homeBtn.setOnClickListener(this);

        profileBtn = (LinearLayout) findViewById(R.id.layoutProfile);
        profileBtn.setOnClickListener(this);

        settingBtn = (LinearLayout) findViewById(R.id.layoutSetting);
        settingBtn.setOnClickListener(this);
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
            case R.id.layoutBluetooth:
                startActivity(new Intent(HomeActivity.this, BluetoothActivity.class));
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