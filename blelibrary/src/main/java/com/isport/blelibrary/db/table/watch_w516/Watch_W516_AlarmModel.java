package com.isport.blelibrary.db.table.watch_w516;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author
 * @Date 2019/2/24
 * @Fuction
 */
@Entity
public class Watch_W516_AlarmModel {

//    手表闹钟的数据库
//    userId	用户ID	string
//    deviceId	设备ID	string
//    repeatCount	重复	string
//    timeString	闹钟时间 hh:mm	string
//    messageString	闹钟信息	string

    @Id
    private Long id;
    private String userId;
    private String deviceId;
    private int repeatCount;
    private String timeString;
    private String messageString;
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
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 483998456)
    public Watch_W516_AlarmModel(Long id, String userId, String deviceId,
            int repeatCount, String timeString, String messageString) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.repeatCount = repeatCount;
        this.timeString = timeString;
        this.messageString = messageString;
    }
    @Generated(hash = 989689953)
    public Watch_W516_AlarmModel() {
    }

    @Override
    public String toString() {
        return "Watch_W516_AlarmModel{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", repeatCount=" + repeatCount +
                ", timeString='" + timeString + '\'' +
                ", messageString='" + messageString + '\'' +
                '}';
    }
}
