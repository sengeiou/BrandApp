package com.isport.brandapp.home.bean.http;

/**
 * @Author
 * @Date 2019/1/22
 * @Fuction
 */

public class BindDevice {

    private String deviceId;
    private int deviceTypeId;
    private int userId;
    private String devicetName;
    private String dateStr;
    private String mac;
    private long timestamp;
    private long createTime;//绑定时间

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(int deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDevicetName() {
        return devicetName;
    }

    public void setDevicetName(String devicetName) {
        this.devicetName = devicetName;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BindDevice{" +
                "deviceId='" + deviceId + '\'' +
                ", deviceTypeId=" + deviceTypeId +
                ", userId=" + userId +
                ", devicetName='" + devicetName + '\'' +
                ", dateStr='" + dateStr + '\'' +
                ", mac='" + mac + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

}
