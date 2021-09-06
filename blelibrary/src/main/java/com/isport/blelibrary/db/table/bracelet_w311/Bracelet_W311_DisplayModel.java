package com.isport.blelibrary.db.table.bracelet_w311;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @Author
 * @Date 2019/2/24
 * @Fuction
 */
@Entity
public class Bracelet_W311_DisplayModel {

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
    private boolean isShowCal;
    private boolean isShowDis;
    private boolean isShowSportTime;
    private boolean isShowPresent;
    private boolean isShowComplete;
    private boolean isShowAlarm;
    public boolean getIsShowAlarm() {
        return this.isShowAlarm;
    }
    public void setIsShowAlarm(boolean isShowAlarm) {
        this.isShowAlarm = isShowAlarm;
    }
    public boolean getIsShowComplete() {
        return this.isShowComplete;
    }
    public void setIsShowComplete(boolean isShowComplete) {
        this.isShowComplete = isShowComplete;
    }
    public boolean getIsShowPresent() {
        return this.isShowPresent;
    }
    public void setIsShowPresent(boolean isShowPresent) {
        this.isShowPresent = isShowPresent;
    }
    public boolean getIsShowSportTime() {
        return this.isShowSportTime;
    }
    public void setIsShowSportTime(boolean isShowSportTime) {
        this.isShowSportTime = isShowSportTime;
    }
    public boolean getIsShowDis() {
        return this.isShowDis;
    }
    public void setIsShowDis(boolean isShowDis) {
        this.isShowDis = isShowDis;
    }
    public boolean getIsShowCal() {
        return this.isShowCal;
    }
    public void setIsShowCal(boolean isShowCal) {
        this.isShowCal = isShowCal;
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
    @Generated(hash = 1956554675)
    public Bracelet_W311_DisplayModel(Long id, String userId, String deviceId,
            boolean isShowCal, boolean isShowDis, boolean isShowSportTime,
            boolean isShowPresent, boolean isShowComplete, boolean isShowAlarm) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.isShowCal = isShowCal;
        this.isShowDis = isShowDis;
        this.isShowSportTime = isShowSportTime;
        this.isShowPresent = isShowPresent;
        this.isShowComplete = isShowComplete;
        this.isShowAlarm = isShowAlarm;
    }
    @Generated(hash = 895177778)
    public Bracelet_W311_DisplayModel() {
    }

}
