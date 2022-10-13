package com.example.my_application;

import android.app.Application;

import java.util.UUID;

import cn.com.heaton.blelibrary.ble.Ble;
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Ble.options()
                .setLogBleEnable(true) //Set whether to output and print Bluetooth log
                .setThrowBleException(true) //Set whether to throw a Bluetooth exception
                .setLogTAG("AndroidBLE") //Set global Bluetooth operation log TAG
                .setAutoConnect(false)
                .setIgnoreRepeat(false) //Set whether to filter the scanned devices
                .setConnectFailedRetryCount(3) //When the connection is abnormal (such as Bluetooth protocol stack error), the number of reconnections
                .setConnectTimeout(6 * 1000) //Set the connection timeout period
                .setScanPeriod(15 * 1000) //Set scan duration
                .setUuidService(UUID.fromString("0000f00d-1212-efde-1523-785fef13d123"))
                .setUuidWriteCha(UUID.fromString("00000000-1212-efde-1523-785fef13d123"))
                .create(getBaseContext());
    }
}
