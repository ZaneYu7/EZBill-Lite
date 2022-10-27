package com.example.EzBill.adapter;

import android.content.Context;

import com.example.EzBill.R;

import java.util.List;

import cn.com.heaton.blelibrary.ble.model.BleDevice;

public class ScanAdapter extends RecyclerAdapter<BleDevice>{
    public ScanAdapter(Context context, List<BleDevice> datas) {
        super(context, R.layout.item_scan, datas);
    }

    @Override
    public void convert(RecyclerViewHolder holder, BleDevice bleDevice) {
        holder.setText(R.id.tv_name, bleDevice.getBleName());
        holder.setText(R.id.tv_address, bleDevice.getBleAddress());
    }
}
