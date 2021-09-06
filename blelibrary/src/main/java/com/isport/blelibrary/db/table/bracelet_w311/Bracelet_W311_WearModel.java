package com.isport.blelibrary.db.table.bracelet_w311;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Bracelet_W311_WearModel {
    //    界面显示的数据库
//    userId	用户ID	string
//    deviceId	设备ID	string
//    isShowCal	显示卡路里	string
//    isShowDis	显示距离 hh:mm	string
//    isShowSportTime	显示运动时间	string
//    isShowPresent	显示百分比	string
//    isShowComplete	显示完成情况	string
//    isShowAlarm	显示闹钟	string

    @Id
    private Long id;
    private String userId;
    private String deviceId;
    private boolean isLeft;//左手还是右手
    public boolean getIsLeft() {
        return this.isLeft;
    }
    public void setIsLeft(boolean isLeft) {
        this.isLeft = isLeft;
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
    @Generated(hash = 1154876193)
    public Bracelet_W311_WearModel(Long id, String userId, String deviceId,
            boolean isLeft) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.isLeft = isLeft;
    }
    @Generated(hash = 1971093356)
    public Bracelet_W311_WearModel() {
    }

}
