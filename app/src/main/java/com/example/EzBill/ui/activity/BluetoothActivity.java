package com.example.EzBill.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.EzBill.R;
import com.example.EzBill.adapter.ScanAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.heaton.blelibrary.ble.Ble;
import cn.com.heaton.blelibrary.ble.callback.BleScanCallback;
import cn.com.heaton.blelibrary.ble.callback.BleStatusCallback;
import cn.com.heaton.blelibrary.ble.model.BleDevice;

public class BluetoothActivity extends AppCompatActivity {

    private String TAG = BluetoothActivity.class.getSimpleName();
    public static final int REQUEST_PERMISSION_LOCATION = 2;
    public static final int REQUEST_GPS = 4;
    private RecyclerView recyclerView;
    private ScanAdapter adapter;
    private List<BleDevice> bleDevices;
    private Ble<BleDevice> ble = Ble.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        initTitle();
        initView();
        initAdapter();
        initLinsenter();
        initBleStatus();
        ble.startScan(scanCallback);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
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
    }

    private void initAdapter() {
        bleDevices = new ArrayList<>();
        adapter = new ScanAdapter(this, bleDevices);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void initLinsenter() {
        findViewById(R.id.btn_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rescan();
            }
        });
        adapter.setOnItemClickListener((parent, view, bleDevice, position) -> {
            Intent intent = new Intent(this, ControlActivity.class);
            intent.putExtra("device", bleDevice);
            startActivity(intent);
        });
    }

    //Monitor bluetooth switch status
    private void initBleStatus() {
        ble.setBleStatusCallback(new BleStatusCallback() {
            @Override
            public void onBluetoothStatusChanged(boolean isOn) {
                Log.i(TAG, "onBluetoothStatusOn: 蓝牙是否打开>>>>:" + isOn);
            }
        });
    }

    private void rescan() {
        if (ble != null && !ble.isScanning()) {
            bleDevices.clear();
            adapter.notifyDataSetChanged();
            ble.startScan(scanCallback);
        }
    }

    private BleScanCallback<BleDevice> scanCallback = new BleScanCallback<BleDevice>() {
        @Override
        public void onLeScan(final BleDevice device, int rssi, byte[] scanRecord) {
            if (TextUtils.isEmpty(device.getBleName())){
                return;
            }
            synchronized (ble.getLocker()) {
                for (int i = 0; i < bleDevices.size(); i++) {
                    BleDevice rssiDevice = bleDevices.get(i);
                    if (TextUtils.equals(rssiDevice.getBleAddress(), device.getBleAddress())){
                        return;
                    }
                }
                bleDevices.add(device);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.e(TAG, "onScanFailed: "+errorCode);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == Ble.REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
        } else if (requestCode == Ble.REQUEST_ENABLE_BT && resultCode == Activity.RESULT_OK) {
            ble.startScan(scanCallback);
        }else if (requestCode == REQUEST_GPS){

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}