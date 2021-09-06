package com.isport.blelibrary.db.table.w811w814;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class DeviceTimeFormat {
    @Id
    private Long id;
    private String userId;
    private String deviceId;
    private int timeformate;//12小时还是24小时
    public int getTimeformate() {
        return this.timeformate;
    }
    public void setTimeformate(int timeformate) {
        this.timeformate = timeformate;
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
    @Generated(hash = 216636100)
    public DeviceTimeFormat(Long id, String userId, String deviceId, int timeformate) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.timeformate = timeformate;
    }
    @Generated(hash = 1039556484)
    public DeviceTimeFormat() {
    }

    @Override
    public String toString() {
        return "DeviceTimeFormat{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", timeformate=" + timeformate +
                '}';
    }
}
