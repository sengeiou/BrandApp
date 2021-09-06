package com.isport.brandapp.bean;

import java.io.Serializable;

public class DeviceBean implements Serializable {

    /**
     * "deviceId": 29,
     * "deviceType": 0,
     * "mac": "1",
     * "deviceName": "2",
     * "deviceImgurl": null
     */
    public int deviceId;
    public int deviceType;
    public String mac;
    public String deviceName;
    public String deviceID;//本地设备唯一标示
    public String deviceImgurl;
    public long timeTamp;//绑定时间戳
    public int battery;

    public int currentType;
    public boolean connectState;
    public SportBean sportBean;
    public SleepBean sleepBean;
    public WeightBean weightBean;
    public BrandBean brandBean;
    public String scanName;
    public int resId;
    public int index;
    public int resBg;


    public DeviceBean() {

    }


    public DeviceBean(int currentType, String scanName, int resId) {
        this.currentType = currentType;
        this.scanName = scanName;
        this.resId = resId;
    }
    public DeviceBean(int currentType,int bgRes, String scanName, int resId) {
        this.currentType = currentType;
        this.scanName = scanName;
        this.resId = resId;
        this.resBg=bgRes;
    }
    public DeviceBean(int currentType, String scanName, int resId,int index) {
        this.currentType = currentType;
        this.scanName = scanName;
        this.resId = resId;
        this.index=index;
    }

    public DeviceBean(int currentType) {
        this.currentType = currentType;
    }

    public DeviceBean(int currentType, String name) {
        this.currentType = currentType;
        this.deviceName = name;
    }

    public DeviceBean(int currentType, String name, String scanName, int resId) {
        this.currentType = currentType;
        this.deviceName = name;
        this.scanName = scanName;
        this.resId = resId;
        this.index=index;
    }

    @Override
    public String toString() {
        return "DeviceBean{" +
                "deviceId=" + deviceId +
                ", deviceType=" + deviceType +
                ", mac='" + mac + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceID='" + deviceID + '\'' +
                ", deviceImgurl='" + deviceImgurl + '\'' +
                ", timeTamp=" + timeTamp +
                ", battery=" + battery +
                ", currentType=" + currentType +
                ", connectState=" + connectState +
                ", sportBean=" + sportBean +
                ", sleepBean=" + sleepBean +
                ", weightBean=" + weightBean +
                ", brandBean=" + brandBean +
                ", scanName='" + scanName + '\'' +
                ", ResId=" + resId +
                '}';
    }
}
