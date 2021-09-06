package com.isport.blelibrary.db.table.w526;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Device_TempTable {
    @Id
    private Long id;
    private String userId;
    private String wristbandTemperatureId;
    private String deviceId;
    private String centigrade;
    private String fahrenheit;
    private String strDate;
    private long timestamp;
    public long getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public String getStrDate() {
        return this.strDate;
    }
    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }
    public String getFahrenheit() {
        return this.fahrenheit;
    }
    public void setFahrenheit(String fahrenheit) {
        this.fahrenheit = fahrenheit;
    }
    public String getCentigrade() {
        return this.centigrade;
    }
    public void setCentigrade(String centigrade) {
        this.centigrade = centigrade;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getWristbandTemperatureId() {
        return this.wristbandTemperatureId;
    }
    public void setWristbandTemperatureId(String wristbandTemperatureId) {
        this.wristbandTemperatureId = wristbandTemperatureId;
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
    @Generated(hash = 1461315649)
    public Device_TempTable(Long id, String userId, String wristbandTemperatureId,
            String deviceId, String centigrade, String fahrenheit, String strDate,
            long timestamp) {
        this.id = id;
        this.userId = userId;
        this.wristbandTemperatureId = wristbandTemperatureId;
        this.deviceId = deviceId;
        this.centigrade = centigrade;
        this.fahrenheit = fahrenheit;
        this.strDate = strDate;
        this.timestamp = timestamp;
    }
    @Generated(hash = 2132818574)
    public Device_TempTable() {
    }

    @Override
    public String toString() {
        return "Device_TempTable{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", wristbandTemperatureId='" + wristbandTemperatureId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", centigrade='" + centigrade + '\'' +
                ", fahrenheit='" + fahrenheit + '\'' +
                ", strDate='" + strDate + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
