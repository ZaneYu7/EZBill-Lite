package com.example.my_application.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.graphics.Color;
import com.example.my_application.R;
import com.example.my_application.beam.Data;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ElectricityActivity extends AppCompatActivity {
    //init
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity);

        //Assign variable
        pieChart = findViewById(R.id.pie_chart);

        initData();

    }

    private void initData() {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            //Convert to float
            float value = (float) (i * 10.0);
            //initialize pie chart entry

        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("BLE")
                ;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Data data= null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Data event = snapshot.getValue(Data.class);
                    if (event.getUser().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        data = event;

                    }

                }
                if (data!=null) {
                    PieEntry pieEntry = new PieEntry((float) data.getPowerUsed(), "Your Usage");
                    //Add values in array list
                    pieEntries.add(pieEntry);
                    PieEntry pieEntry2 = new PieEntry((float) (1-data.getPowerUsed()), "others");
                    //Add values in array list
                    pieEntries.add(pieEntry2);
                    //Initialize pie data set
                    PieDataSet pieDataSet = new PieDataSet(pieEntries, "Tenants");
                    //set color
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieChart.setDrawHoleEnabled(false);
                    PieData pieData = new PieData(pieDataSet);
                    pieData.setValueTextSize(18f);
                    pieData.setValueTextColor(Color.WHITE);
                    pieChart.setData(pieData);
                    //set animation
                    pieChart.animateXY(500, 500);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });







    }
}