package com.isport.blelibrary.scanner;

import android.bluetooth.BluetoothDevice;

import com.isport.blelibrary.bluetooth.scanner.BleLeScanCallback;

import java.util.ArrayList;

public interface ScanResultsCallBack {
    void onBatchScanResults(BluetoothDevice device, int rssi, byte[] scanRecord );

    void onScanFailed(int errorCode);

    void onScanResult(BleLeScanCallback.ScanResult result);

    void onScanFinished();
}
