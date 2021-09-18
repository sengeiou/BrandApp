package com.isport.brandapp.home.bean.http;

import java.util.List;

/**
 * @Author
 * @Date 2018/11/6
 * @Fuction
 */

public class UpdateWatchBean {

    private int userId;

    private int interfaceId;

    private int deviceType;

    private String mac;

    private String time;

    private List<WatchDayData> data;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setInterfaceId(int interfaceId) {
        this.interfaceId = interfaceId;
    }

    public int getInterfaceId() {
        return this.interfaceId;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getDeviceType() {
        return this.deviceType;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMac() {
        return this.mac;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }

    public void setData(List<WatchDayData> data) {
        this.data = data;
    }

    public List<WatchDayData> getData() {
        return this.data;
    }
}
