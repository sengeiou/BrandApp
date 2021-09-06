package com.isport.brandapp.wu.bean;

import com.isport.blelibrary.utils.Logger;

public class OnceHrInfo {
    private String singleHeartRateId;

    private String deviceId;

    private String userId;

    private Long timestamp;

    private String heartValue;


    private String extend1;

    private String extend2;

    private String extend3;

    private String strDate;

    public String getSingleHeartRateId() {
        return singleHeartRateId;
    }

    public void setSingleHeartRateId(String singleHeartRateId) {
        this.singleHeartRateId = singleHeartRateId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getHeartValue() {
        return heartValue;
    }

    public void setHeartValue(String heartValue) {
        this.heartValue = heartValue == null ? null : heartValue.trim();
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }


    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1 == null ? null : extend1.trim();
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2 == null ? null : extend2.trim();
    }

    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3 == null ? null : extend3.trim();
    }

    @Override
    public String toString() {
        return "OnceHrInfo{" +
                "singleHeartRateId=" + singleHeartRateId +
                ", deviceId='" + deviceId + '\'' +
                ", userId='" + userId + '\'' +
                ", timestamp=" + timestamp +
                ", heartValue='" + heartValue + '\'' +
                ", extend1='" + extend1 + '\'' +
                ", extend2='" + extend2 + '\'' +
                ", extend3='" + extend3 + '\'' +
                ", strDate='" + strDate + '\'' +
                '}';
    }
}
