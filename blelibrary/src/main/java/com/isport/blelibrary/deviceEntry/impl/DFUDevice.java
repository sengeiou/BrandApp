package com.isport.blelibrary.deviceEntry.impl;

import android.content.Context;

import com.isport.blelibrary.deviceEntry.interfaces.IDeviceType;
import com.isport.blelibrary.managers.BaseManager;

public class DFUDevice extends BaseDevice implements IDeviceType {

    public DFUDevice(String name, String mac) {
        super();
        this.deviceName = name;
        this.address = mac;
        setType();
    }

    @Override
    public void disconnect(boolean reConnect) {

    }

    @Override
    public void connect(boolean isConnectByUser) {


    }

    @Override
    public BaseManager getManager(Context context) {
        return null;
    }

    @Override
    public void exit() {

    }

    @Override
    public void getBattery() {

    }

    @Override
    public void setType() {
        this.deviceType = TYPE_DFU;
    }

    @Override
    public void syncTodayData() {

    }

    @Override
    public void queryWatchFace() {

    }
}
