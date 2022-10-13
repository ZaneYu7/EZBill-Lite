package com.example.my_application.ui.activity;

import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.my_application.R;

import java.io.Serializable;
import java.util.List;

import cn.com.heaton.blelibrary.ble.Ble;
import cn.com.heaton.blelibrary.ble.callback.BleConnectCallback;
import cn.com.heaton.blelibrary.ble.callback.BleWriteCallback;
import cn.com.heaton.blelibrary.ble.model.BleDevice;

public class ControlActivity extends AppCompatActivity {
    private static final String TAG = "ControlActivity";

    private TextView tvPowerLevel, tvPowerUsed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        tvPowerLevel = findViewById(R.id.tv_level);
        tvPowerUsed = findViewById(R.id.tv_used);
        initTitle();
        Intent intent = getIntent();
        if (intent != null){
            BleDevice device = getIntent().getParcelableExtra("device");
            TextView tvAddress = findViewById(R.id.tv_address);
            tvAddress.setText(device.getBleAddress());
            Ble.getInstance().connect(device, new BleConnectCallback<BleDevice>() {
                @Override
                public void onConnectionChanged(BleDevice device) {
                    TextView tvState = findViewById(R.id.tv_state);
                    String state = "State Connecting";
                    if (device.isConnected()){
                        state = "State Connected";
                    }else if (device.isDisconnected()){
                        state = "State Disconnect";
                    }
                    tvState.setText(state);
                }
            });
        }
    }

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
                }
            }
        });
    }

    public void low(View view) {
        tvPowerLevel.setText("Power level: 500");
        write(new byte[]{0x10});
    }

    public void mid(View view) {
        tvPowerLevel.setText("Power level: 1000");
        write(new byte[]{0x11});
    }

    public void high(View view) {
        tvPowerLevel.setText("Power level: 2000");
        write(new byte[]{0x12});
    }

    private void write(byte[]data){
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
