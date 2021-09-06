package com.isport.blelibrary.interfaces;

import com.isport.blelibrary.deviceEntry.impl.BaseDevice;

import java.util.ArrayList;
import java.util.Map;

/**
 * @Author
 * @Date 2018/10/10
 * @Fuction  搜索设备返回
 */

public interface ScanBackListener {
    void onScanResult(ArrayList<BaseDevice> mBleDevices);
    void onScanResult(Map<String,BaseDevice> listDevicesMap);
    void onScanFinish();
}
