package com.isport.blelibrary.db.table.bracelet_w311;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * @Author
 * @Date 2019/2/24
 * @Fuction
 */
@Entity
public class Bracelet_W311_AlarmModel implements Serializable{

//    手表闹钟的数据库
//    userId	用户ID	string
//    deviceId	设备ID	string
//    repeatCount	重复	string
//    timeString	闹钟时间 hh:mm	string
//    messageString	闹钟信息	string
//    isOpen	是否打开	boolean

    @Id
    private Long id;
    private int alarmId;
    private String userId;
    private String deviceId;
    private int repeatCount;
    private String timeString;
    private String messageString;
    private Boolean isOpen;
    public Boolean getIsOpen() {
        return this.isOpen;
    }
    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }
    public String getMessageString() {
        return this.messageString;
    }
    public void setMessageString(String messageString) {
        this.messageString = messageString;
    }
    public String getTimeString() {
        return this.timeString;
    }
    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }
    public int getRepeatCount() {
        return this.repeatCount;
    }
    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
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
    public int getAlarmId() {
        return this.alarmId;
    }
    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1207923514)
    public Bracelet_W311_AlarmModel(Long id, int alarmId, String userId,
            String deviceId, int repeatCount, String timeString,
            String messageString, Boolean isOpen) {
        this.id = id;
        this.alarmId = alarmId;
        this.userId = userId;
        this.deviceId = deviceId;
        this.repeatCount = repeatCount;
        this.timeString = timeString;
        this.messageString = messageString;
        this.isOpen = isOpen;
    }
    @Generated(hash = 1044926660)
    public Bracelet_W311_AlarmModel() {
    }


    @Override
    public String toString() {
        return "Bracelet_W311_AlarmModel{" +
                "id=" + id +
                ", alarmId=" + alarmId +
                ", userId='" + userId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", repeatCount=" + repeatCount +
                ", timeString='" + timeString + '\'' +
                ", messageString='" + messageString + '\'' +
                ", isOpen=" + isOpen +
                '}';
    }
}
