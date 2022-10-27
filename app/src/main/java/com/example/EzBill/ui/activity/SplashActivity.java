package com.example.EzBill.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.EzBill.R;

public class SplashActivity extends AppCompatActivity {
    private Button eBtnskip;
    private Handler eHandler = new Handler();
    private Runnable eRunnable = new Runnable() {
        @Override
        public void run() {
            toLoginActivity();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initEvent();
        eHandler.postDelayed(eRunnable, 3000);
    }

    private void initEvent() {
        eBtnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLoginActivity();
                eHandler.removeCallbacks(eRunnable);
            }
        });
    }

    private void initView() {
        eBtnskip = findViewById(R.id.id_btn_skip);
    }

    public void toLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eHandler.removeCallbacks(eRunnable);
    }
}