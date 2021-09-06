package com.isport.blelibrary.db.table.bracelet_w311;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Bracelet_w311_hrModel {
    @Id
    private Long id;
    private String userId;
    private String deviceId;
    private boolean isOpen;
    private int times;
    public int getTimes() {
        return this.times;
    }
    public void setTimes(int times) {
        this.times = times;
    }
    public boolean getIsOpen() {
        return this.isOpen;
    }
    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
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
    @Generated(hash = 1497833129)
    public Bracelet_w311_hrModel(Long id, String userId, String deviceId,
            boolean isOpen, int times) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.isOpen = isOpen;
        this.times = times;
    }
    @Generated(hash = 1206689759)
    public Bracelet_w311_hrModel() {
    }

    @Override
    public String toString() {
        return "Bracelet_w311_hrModel{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", isOpen=" + isOpen +
                ", times=" + times +
                '}';
    }
}
