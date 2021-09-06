package com.isport.brandapp.bind.bean;

/**
 * @Author
 * @Date 2019/1/22
 * @Fuction
 */

public class DeviceState {

    private String deviceId;
    private int deviceTypeId;
    private int userId;
    private String devicetName;
    private String dateStr;
    private String timestamp;
    private long createTime;

    @Override
    public String toString() {
        return "DeviceState{" +
                "deviceId='" + deviceId + '\'' +
                ", deviceTypeId=" + deviceTypeId +
                ", userId=" + userId +
                ", devicetName='" + devicetName + '\'' +
                ", dateStr='" + dateStr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", createTime=" + createTime +
                '}';
    }

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
