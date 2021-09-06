package com.isport.blelibrary.result.impl.watch;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class DeviceSendCmdResult implements IResult, Serializable {



    @Override
    public String getType() {
        return DEVICE_SEND_CMD;
    }

    private int sendCmdType;
    private int deviceType ;
    private String deviceName;
    private String mac;
    private int isSuccess;

    public DeviceSendCmdResult(int sendCmdType, int deviceType,String deviceName,String mac,int isSuccess) {
        this.sendCmdType = sendCmdType;
        this.deviceType = deviceType;
        this.deviceName = deviceName;
        this.mac=mac;
        this.isSuccess=isSuccess;
    }

    public int getSendCmdType() {
        return sendCmdType;
    }

    public void setSendCmdType(int sendCmdType) {
        this.sendCmdType = sendCmdType;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int isSuccess() {
        return isSuccess;
    }

    public void setSuccess(int success) {
        isSuccess = success;
    }

    @Override
    public String toString() {
        return "DeviceSendCmdResult{" +
                "sendCmdType=" + sendCmdType +
                ", deviceType=" + deviceType +
                ", deviceName='" + deviceName + '\'' +
                ", mac='" + mac + '\'' +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
