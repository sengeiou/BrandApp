package com.isport.blelibrary.deviceEntry.impl;

import android.content.Context;

import com.isport.blelibrary.InitDeviceManager;
import com.isport.blelibrary.deviceEntry.interfaces.IDeviceType;
import com.isport.blelibrary.managers.BaseManager;
import com.isport.blelibrary.managers.ScaleBleManager;

/**
 * @Author
 * @Date 2018/10/13
 * @Fuction
 */

public class ScaleDevice extends BaseDevice implements IDeviceType {
    /**
     * 名字 deviceName
     * mac地址 address
     * 信号值 rssi
     * 哪个类型的设备 deviceType
     */
    public ScaleDevice() {

    }

    public ScaleDevice(String name, String mac) {
        super();
        this.deviceName = name;
        this.address = mac;
        setType();
    }

    public ScaleDevice(String mac) {
        super();
        this.address = mac;
        setType();
    }

    @Override
    public void disconnect(boolean reconnect) {
        ScaleBleManager.getInstance().disconnect(reconnect);
    }

    @Override
    public void connect(boolean isConnectByUser) {
        ScaleBleManager.getInstance().connectNRF(this, isConnectByUser);
    }

    @Override
    public BaseManager getManager(Context context) {
        return ScaleBleManager.getInstance(context);
    }

    @Override
    public void getBattery() {
        ScaleBleManager.getInstance().getBattery();
    }

    @Override
    public void exit() {
        ScaleBleManager.getInstance().exit();
    }

    @Override
    public void close() {
        ScaleBleManager.getInstance().close();
    }

    public void getDeviceVersion() {
        ScaleBleManager.getInstance().getDeviceVersion();
    }

    @Override
    public void setType() {
        this.deviceType = TYPE_SCALE;
    }

    @Override
    public void syncTodayData() {

    }

    @Override
    public void queryWatchFace() {

    }
}
