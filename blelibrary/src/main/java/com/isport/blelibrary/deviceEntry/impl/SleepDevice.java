package com.isport.blelibrary.deviceEntry.impl;

import android.content.Context;

import com.isport.blelibrary.deviceEntry.interfaces.IDeviceType;
import com.isport.blelibrary.deviceEntry.interfaces.ISleep;
import com.isport.blelibrary.managers.BaseManager;
import com.isport.blelibrary.managers.SleepBleManager;

/**
 * @Author
 * @Date 2018/10/13
 * @Fuction
 */

public class SleepDevice extends BaseDevice implements IDeviceType, ISleep {


    public SleepDevice() {
    }

    public SleepDevice(String name, String mac) {
        super();
        this.deviceName = name;
        this.address = mac;
        setType();
    }

    @Override
    public void disconnect(boolean reconnect) {
        SleepBleManager.getInstance().disconnect();
    }

    @Override
    public void connect(boolean isConnectByUser) {
        SleepBleManager.getInstance().login(this, "9-0", 1);
    }

    @Override
    public BaseManager getManager(Context context) {
        return SleepBleManager.getInstance(context);
    }


    @Override
    public void getBattery() {
        SleepBleManager.getInstance().getBattery();
    }

    @Override
    public void exit() {
        SleepBleManager.getInstance().exit();
    }

    @Override
    public void close() {
        SleepBleManager.getInstance().exit();
    }


    public void getDeviceVersion() {
        SleepBleManager.getInstance().getDeviceVersion();
    }


    public void setAutoCollection(boolean enable, int hour, int minute, int repeat) {
        SleepBleManager.getInstance().setAutoCollection(enable, hour, minute, repeat);
    }

    /**
     * 手动开始监测
     * timeout	int	超时时间,单位：毫秒
     * cb	IDataCallback<Void>	回调接口
     */

    private void startCollection() {
        SleepBleManager.getInstance().startCollection();
    }

    /**
     * 手动结束监测
     * timeout	int	超时时间,单位：毫秒
     * cb	IDataCallback<Void>	回调接口
     */

    private boolean stopCollection() {
        return SleepBleManager.getInstance().stopCollection();
    }

    public void setCollectionEnable(boolean enable) {
        if (enable) {
            startCollection();
        } else {
            stopCollection();
        }
    }

    public void getCollectionStatus() {
        SleepBleManager.getInstance().getCollectionStatus();
    }

    /**
     * 开始查看监测实时数据
     * timeout	int	超时时间,单位：毫秒
     * cb	IDataCallback<RealTimeData>	回调接口,每秒返回一次;详细返回见RealTimeData
     */
    private boolean startRealTimeData() {
        return SleepBleManager.getInstance().startRealTimeData();
    }

    /**
     * 停止查看监测实时数据
     * timeout	int	超时时间,单位：毫秒
     * cb	IDataCallback<RealTimeData>	回调接口,每秒返回一次;详细返回见RealTimeData
     */
    private boolean stopRealTimeData() {
        return SleepBleManager.getInstance().stopRealTimeData();
    }

    public void setRealTimeEnable(boolean enable) {
        if (enable) {
            startRealTimeData();
        } else {
            stopRealTimeData();
        }
    }

    /**
     * 开始查看监测信号强度
     * timeout	int	超时时间,单位：毫秒
     * cb	IDataCallback<OriginalData>	回调接口,设备1秒产生100个点，100毫秒上报一次;详细返回见OriginalData
     */
    private boolean startOriginalData() {
        return SleepBleManager.getInstance().startOriginalData();
    }

    /**
     * 停止查看监测信号强度
     * timeout	int	超时时间,单位：毫秒
     * cb	IDataCallback<Void>	回调接口
     */
    private boolean stopOriginalData() {
        return SleepBleManager.getInstance().stopOriginalData();
    }

    public void setOriginalEnable(boolean enable) {
        if (enable) {
            startOriginalData();
        } else {
            stopOriginalData();
        }
    }

    public void historyDownload(int startTime, int endTime, int sex) {
        SleepBleManager.getInstance().historyDownload(startTime, endTime, sex);
    }

    public void getEnvironmentalData() {
        SleepBleManager.getInstance().getEnvironmentalData();
    }

    public void upgradeDevice1() {
        SleepBleManager.getInstance().upgradeDevice1();
    }

    public void upgradeDevice2() {
        SleepBleManager.getInstance().upgradeDevice2();
    }

    @Override
    public void setType() {
        this.deviceType = TYPE_SLEEP;
    }

    @Override
    public void syncTodayData() {

    }

    @Override
    public void queryWatchFace() {

    }

}
