package com.isport.blelibrary.deviceEntry.impl;

import android.content.Context;

import com.isport.blelibrary.managers.BaseManager;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Author
 * @Date 2018/10/7
 * @Fuction
 */

public abstract class BaseDevice implements Serializable {

    /**
     * 名字 deviceName
     * mac地址 address
     * 信号值 rssi
     * 哪个类型的设备 deviceType
     */
    public String deviceName;
    public int rssi;
    public String address;
    public int battery;
    public int deviceType;

    public byte[] getScanRecord() {
        return scanRecord;
    }

    public void setScanRecord(byte[] scanRecord) {
        this.scanRecord = scanRecord;
    }

    private byte[] scanRecord;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public BaseDevice() {

    }

    /**
     * 公共
     */

    public abstract void disconnect(boolean reConnect);

    public abstract void connect(boolean isConnectByUser);

    public abstract BaseManager getManager(Context context);

    public abstract void exit();

    public abstract void getBattery();

    public void close() {

    }

    public void getDeviceVersion() {

    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public String toString() {
        return "BaseDevice{" +
                "deviceName='" + deviceName + '\'' +
                ", rssi=" + rssi +
                ", address='" + address + '\'' +
                ", deviceType=" + deviceType +
                ", scanRecord=" + Arrays.toString(scanRecord) +
                '}';
    }
}
