package com.isport.blelibrary.db.table.w811w814;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class OneceHrMode {
    @Id
    private Long id;
    private String userId;
    private String deviceId;
    private String wristbandBloodOxygenId;
    private long timestamp;
    private String strTimes;
    private int bloodOxygen;
    public int getBloodOxygen() {
        return this.bloodOxygen;
    }
    public void setBloodOxygen(int bloodOxygen) {
        this.bloodOxygen = bloodOxygen;
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
    public String getWristbandBloodOxygenId() {
        return this.wristbandBloodOxygenId;
    }
    public void setWristbandBloodOxygenId(String wristbandBloodOxygenId) {
        this.wristbandBloodOxygenId = wristbandBloodOxygenId;
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
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1565772500)
    public OneceHrMode(Long id, String userId, String deviceId,
            String wristbandBloodOxygenId, long timestamp, String strTimes,
            int bloodOxygen) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.wristbandBloodOxygenId = wristbandBloodOxygenId;
        this.timestamp = timestamp;
        this.strTimes = strTimes;
        this.bloodOxygen = bloodOxygen;
    }
    @Generated(hash = 1292326830)
    public OneceHrMode() {
    }

    @Override
    public String toString() {
        return "OneceHrMode{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", wristbandBloodOxygenId='" + wristbandBloodOxygenId + '\'' +
                ", timestamp=" + timestamp +
                ", strTimes='" + strTimes + '\'' +
                ", bloodOxygen=" + bloodOxygen +
                '}';
    }
}
