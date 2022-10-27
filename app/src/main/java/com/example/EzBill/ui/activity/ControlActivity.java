package com.example.EzBill.ui.activity;

import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.EzBill.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

import cn.com.heaton.blelibrary.ble.Ble;
import cn.com.heaton.blelibrary.ble.callback.BleConnectCallback;
import cn.com.heaton.blelibrary.ble.callback.BleWriteCallback;
import cn.com.heaton.blelibrary.ble.model.BleDevice;

public class ControlActivity extends AppCompatActivity {
    private static final String TAG = "ControlActivity";

    private TextView tvPowerLevel, tvPowerUsed;
    private Handler handler = new Handler(Looper.myLooper());
    private BleDevice device;
    private double powerUsed = 0;
    private int powerLevel = 0;
    private String BLE;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_control);
        tvPowerLevel = findViewById(R.id.tv_level);
        tvPowerUsed = findViewById(R.id.tv_used);
        initTitle();
        Intent intent = getIntent();
        if (intent != null){
            device = getIntent().getParcelableExtra("device");
            TextView tvAddress = findViewById(R.id.tv_address);
            tvAddress.setText(device.getBleAddress());
            Ble.getInstance().connect(device, connectCallback);
        }
    }

    private BleConnectCallback<BleDevice> connectCallback = new BleConnectCallback<BleDevice>() {
        @Override
        public void onConnectionChanged(BleDevice device) {
            TextView tvState = findViewById(R.id.tv_state);
            TextView sub_title = findViewById(R.id.tv_right);
            String state = "Connecting";
            String sub_state = "Disconnect";
            if (device.isConnected()){
                state = "Connected";
                sub_state = "Disconnect";
            }else if (device.isDisconnected()){
                state = "Disconnect";
                sub_state = "Connected";
            }
            tvState.setText("State: "+state);
            sub_title.setText(sub_state);
        }
    };

    private Runnable electronicRunnable = new Runnable() {
        @Override
        public void run() {
            List<BleDevice> connectedDevices = Ble.getInstance().getConnectedDevices();
            if (connectedDevices.isEmpty()){
                return;
            }
            powerUsed += (double) powerLevel / 100 / 3600;
            tvPowerUsed.setText("Power used: "+ String.format("%.4f", powerUsed) + "kw·h");
            handler.postDelayed(this, 1000);

            //save data to firebase database.
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("user", FirebaseAuth.getInstance().getCurrentUser().getEmail());
            hashMap.put("powerUsed", powerUsed);
            databaseReference.child("BLE").child(FirebaseAuth.getInstance().getUid()).setValue(hashMap);
        }
    };

    private void initTitle() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView title = findViewById(R.id.tv_title);
        title.setText("Devices");
        TextView sub_title = findViewById(R.id.tv_right);
        sub_title.setText("Disconnect");
        sub_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<BleDevice> connectedDevices = Ble.getInstance().getConnectedDevices();
                if (!connectedDevices.isEmpty()){
                    Ble.getInstance().disconnect(connectedDevices.get(0));
                }else {
                    Ble.getInstance().connect(device, connectCallback);
                }
            }
        });
    }

    public void low(View view) {
        powerLevel = 500;
        tvPowerLevel.setText("Power level: 500");
        write(new byte[]{0x10});
    }

    public void mid(View view) {
        powerLevel = 1000;
        tvPowerLevel.setText("Power level: 1000");
        write(new byte[]{0x11});
    }

    public void high(View view) {
        powerLevel = 2000;
        tvPowerLevel.setText("Power level: 2000");
        write(new byte[]{0x12});
    }

    private void write(byte[]data){
//        isRunning =  true;
        handler.removeCallbacks(electronicRunnable);
        handler.post(electronicRunnable);
        List<BleDevice> connectedDevices = Ble.getInstance().getConnectedDevices();
        if (!connectedDevices.isEmpty()){
            Ble.getInstance().write(connectedDevices.get(0), data, new BleWriteCallback<BleDevice>() {
                @Override
                public void onWriteSuccess(BleDevice device, BluetoothGattCharacteristic characteristic) {
                    Log.e(TAG, "onWriteSuccess: ");
                }
            });
        }
    }
}
