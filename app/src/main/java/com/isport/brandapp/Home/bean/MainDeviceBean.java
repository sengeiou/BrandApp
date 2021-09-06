package com.isport.brandapp.Home.bean;

import java.util.Objects;

public class MainDeviceBean {
    boolean isConn;
    int connState;
    int deviceRes;
    String devicename;
    String deviceTypeName;
    int deviceType;
    int battery;
    int batteryRes;
    float scaleWeight;
    String scaleTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MainDeviceBean that = (MainDeviceBean) o;
        return Objects.equals(devicename, that.devicename) &&
                Objects.equals(deviceTypeName, that.deviceTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isConn, connState, deviceRes, devicename, deviceTypeName, deviceType, battery, batteryRes, scaleWeight, scaleTime);
    }

    /* @Override
    public boolean equals(Object obj) {
        if (obj instanceof MainDeviceBean) {
            return (obj.devicename().equals(this.devicename));
        }
        return false;
    }*/

    public MainDeviceBean() {

    }

    public boolean isConn() {
        return isConn;
    }

    public void setConn(boolean conn) {
        isConn = conn;
    }

    public int getConnState() {
        return connState;
    }

    public void setConnState(int connState) {
        this.connState = connState;
    }

    public int getDeviceRes() {
        return deviceRes;
    }

    public void setDeviceRes(int deviceRes) {
        this.deviceRes = deviceRes;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getBatteryRes() {
        return batteryRes;
    }

    public void setBatteryRes(int batteryRes) {
        this.batteryRes = batteryRes;
    }

    public float getScaleWeight() {
        return scaleWeight;
    }

    public void setScaleWeight(float scaleWeight) {
        this.scaleWeight = scaleWeight;
    }

    public String getScaleTime() {
        return scaleTime;
    }

    public void setScaleTime(String scaleTime) {
        this.scaleTime = scaleTime;
    }

    @Override
    public String toString() {
        return "MainDeviceBean{" +
                "isConn=" + isConn +
                ", connState=" + connState +
                ", deviceRes=" + deviceRes +
                ", devicename='" + devicename + '\'' +
                ", deviceTypeName='" + deviceTypeName + '\'' +
                ", deviceType=" + deviceType +
                ", battery=" + battery +
                ", batteryRes=" + batteryRes +
                ", scaleWeight=" + scaleWeight +
                ", scaleTime='" + scaleTime + '\'' +
                '}';
    }
}
