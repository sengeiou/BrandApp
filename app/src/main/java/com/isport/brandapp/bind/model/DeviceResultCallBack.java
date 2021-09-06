package com.isport.brandapp.bind.model;


import com.isport.blelibrary.deviceEntry.impl.BaseDevice;

import java.util.ArrayList;
import java.util.Map;

public interface DeviceResultCallBack {

    void onScanResult(ArrayList<BaseDevice> mBleDevices);

    void onScanResult(Map<String, BaseDevice> listDevicesMap);


    void onScanFinish();
}
