package com.isport.brandapp.bind.bean;

import brandapp.isport.com.basicres.entry.bean.BaseParms;

/**
 * @Author
 * @Date 2019/1/22
 * @Fuction
 */

public class BindInsertOrUpdateBean extends BaseParms {

    private long createTime;
    private String deviceId;
    private int deviceTypeId;
    private String devicetName;

    @Override
    public String toString() {
        return "BindInsertOrUpdateBean{" +
                "createTime=" + createTime +
                ", deviceId='" + deviceId + '\'' +
                ", deviceTypeId=" + deviceTypeId +
                ", devicetName='" + devicetName + '\'' +
                '}';
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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

    public String getDevicetName() {
        return devicetName;
    }

    public void setDevicetName(String devicetName) {
        this.devicetName = devicetName;
    }
}
