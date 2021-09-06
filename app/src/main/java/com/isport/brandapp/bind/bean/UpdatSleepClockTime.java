package com.isport.brandapp.bind.bean;

/**
 * @Author
 * @Date 2019/2/22
 * @Fuction
 */

public class UpdatSleepClockTime {
//    {
//        "sleepbeltConfigId":17,
//            "deviceId":"Z20018610855",
//            "deviceName":null,
//            "devicetType":2,
//            "userId":1150,
//            "mac":"Z20018610855",
//            "sleepTarget":null,
//            "createTime":"2019-02-22T07:01:48.000+0000",
//            "deviceTime":null,
//            "clockTime":"15:01"
//    }

    private Integer sleepbeltConfigId;
    private String deviceId;
    private String deviceName;
    private int devicetType;
    private int userId;
    private String mac;
    private Object sleepTarget;
    private String createTime;
    private Object deviceTime;
    private String clockTime;

    @Override
    public String toString() {
        return "UpdatSleepClockTime{" +
                "sleepbeltConfigId=" + sleepbeltConfigId +
                ", deviceId='" + deviceId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", devicetType=" + devicetType +
                ", userId=" + userId +
                ", mac='" + mac + '\'' +
                ", sleepTarget=" + sleepTarget +
                ", createTime='" + createTime + '\'' +
                ", deviceTime=" + deviceTime +
                ", clockTime='" + clockTime + '\'' +
                '}';
    }

    public Integer getSleepbeltConfigId() {
        return sleepbeltConfigId;
    }

    public void setSleepbeltConfigId(Integer sleepbeltConfigId) {
        this.sleepbeltConfigId = sleepbeltConfigId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getDevicetType() {
        return devicetType;
    }

    public void setDevicetType(int devicetType) {
        this.devicetType = devicetType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Object getSleepTarget() {
        return sleepTarget;
    }

    public void setSleepTarget(Object sleepTarget) {
        this.sleepTarget = sleepTarget;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getDeviceTime() {
        return deviceTime;
    }

    public void setDeviceTime(Object deviceTime) {
        this.deviceTime = deviceTime;
    }

    public String getClockTime() {
        return clockTime;
    }

    public void setClockTime(String clockTime) {
        this.clockTime = clockTime;
    }
}
