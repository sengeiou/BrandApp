package com.isport.blelibrary.db.table.w526;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Device_BacklightTimeAndScreenLuminanceModel {
    @Id
    private Long id;
    private String userId;
    private String deviceId;
    private int valuseBacklightTime;
    private int valuseScreenLeve;
    public int getValuseScreenLeve() {
        return this.valuseScreenLeve;
    }
    public void setValuseScreenLeve(int valuseScreenLeve) {
        this.valuseScreenLeve = valuseScreenLeve;
    }
    public int getValuseBacklightTime() {
        return this.valuseBacklightTime;
    }
    public void setValuseBacklightTime(int valuseBacklightTime) {
        this.valuseBacklightTime = valuseBacklightTime;
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
    @Generated(hash = 646011943)
    public Device_BacklightTimeAndScreenLuminanceModel(Long id, String userId,
            String deviceId, int valuseBacklightTime, int valuseScreenLeve) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.valuseBacklightTime = valuseBacklightTime;
        this.valuseScreenLeve = valuseScreenLeve;
    }
    @Generated(hash = 1430823850)
    public Device_BacklightTimeAndScreenLuminanceModel() {
    }

    @Override
    public String toString() {
        return "Device_BacklightTimeAndScreenLuminanceModel{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", valuseBacklightTime=" + valuseBacklightTime +
                ", valuseScreenLeve=" + valuseScreenLeve +
                '}';
    }
}
