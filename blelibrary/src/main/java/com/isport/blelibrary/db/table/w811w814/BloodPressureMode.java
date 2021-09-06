package com.isport.blelibrary.db.table.w811w814;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BloodPressureMode {
    @Id
    private Long id;
    private String wristbandBloodPressureId;
    private String userId;
    private String deviceId;
    private long timestamp;
    private String strTimes;
    private int systolicBloodPressure;
    private int diastolicBloodPressure;

    public int getDiastolicBloodPressure() {
        return this.diastolicBloodPressure;
    }

    public void setDiastolicBloodPressure(int diastolicBloodPressure) {
        this.diastolicBloodPressure = diastolicBloodPressure;
    }

    public int getSystolicBloodPressure() {
        return this.systolicBloodPressure;
    }

    public void setSystolicBloodPressure(int systolicBloodPressure) {
        this.systolicBloodPressure = systolicBloodPressure;
    }

    public String getStrTimes() {
        return this.strTimes;
    }

    public void setStrTimes(String strTimes) {
        this.strTimes = strTimes;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWristbandBloodPressureId() {
        return this.wristbandBloodPressureId;
    }

    public void setWristbandBloodPressureId(String wristbandBloodPressureId) {
        this.wristbandBloodPressureId = wristbandBloodPressureId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 897761954)
    public BloodPressureMode(Long id, String wristbandBloodPressureId,
                             String userId, String deviceId, long timestamp, String strTimes,
                             int systolicBloodPressure, int diastolicBloodPressure) {
        this.id = id;
        this.wristbandBloodPressureId = wristbandBloodPressureId;
        this.userId = userId;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.strTimes = strTimes;
        this.systolicBloodPressure = systolicBloodPressure;
        this.diastolicBloodPressure = diastolicBloodPressure;
    }

    @Generated(hash = 1399184628)
    public BloodPressureMode() {
    }


    @Override
    public String toString() {
        return "BloodPressureMode{" +
                "id=" + id +
                ", wristbandBloodPressureId='" + wristbandBloodPressureId + '\'' +
                ", userId='" + userId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", timestamp=" + timestamp +
                ", strTimes='" + strTimes + '\'' +
                ", systolicBloodPressure=" + systolicBloodPressure +
                ", diastolicBloodPressure=" + diastolicBloodPressure +
                '}';
    }
}
