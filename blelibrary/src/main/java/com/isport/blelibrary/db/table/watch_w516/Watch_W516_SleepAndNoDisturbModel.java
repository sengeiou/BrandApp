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
public class Watch_W516_SleepAndNoDisturbModel {

//    手表自动睡眠/免打扰模式
//    userId	用户ID	string
//    deviceId	设备ID	string
//    isAutomaticSleep	自动睡眠：开/关	bool
//    isSleepRemind	睡眠提醒：开/关	bool
//    isOpenNoDisturb	免打扰模式：开/关	bool
//    sleepStartTime	睡眠开始时间	string
//    sleepEndTime	睡眠结束时间	string
//    noDisturbStartTime	免打扰开始时间	string
//    noDisturbEndTime	免打扰结束时间	string

    @Id
    private Long id;
    private String userId;
    private String deviceId;
    private boolean automaticSleep;
    private boolean sleepRemind;
    private boolean openNoDisturb;
    private String sleepStartTime;
    private String sleepEndTime;
    private String noDisturbStartTime;
    private String noDisturbEndTime;
    public String getNoDisturbEndTime() {
        return this.noDisturbEndTime;
    }
    public void setNoDisturbEndTime(String noDisturbEndTime) {
        this.noDisturbEndTime = noDisturbEndTime;
    }
    public String getNoDisturbStartTime() {
        return this.noDisturbStartTime;
    }
    public void setNoDisturbStartTime(String noDisturbStartTime) {
        this.noDisturbStartTime = noDisturbStartTime;
    }
    public String getSleepEndTime() {
        return this.sleepEndTime;
    }
    public void setSleepEndTime(String sleepEndTime) {
        this.sleepEndTime = sleepEndTime;
    }
    public String getSleepStartTime() {
        return this.sleepStartTime;
    }
    public void setSleepStartTime(String sleepStartTime) {
        this.sleepStartTime = sleepStartTime;
    }
    public boolean getOpenNoDisturb() {
        return this.openNoDisturb;
    }
    public void setOpenNoDisturb(boolean openNoDisturb) {
        this.openNoDisturb = openNoDisturb;
    }
    public boolean getSleepRemind() {
        return this.sleepRemind;
    }
    public void setSleepRemind(boolean sleepRemind) {
        this.sleepRemind = sleepRemind;
    }
    public boolean getAutomaticSleep() {
        return this.automaticSleep;
    }
    public void setAutomaticSleep(boolean automaticSleep) {
        this.automaticSleep = automaticSleep;
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
    @Generated(hash = 1630150278)
    public Watch_W516_SleepAndNoDisturbModel(Long id, String userId,
            String deviceId, boolean automaticSleep, boolean sleepRemind,
            boolean openNoDisturb, String sleepStartTime, String sleepEndTime,
            String noDisturbStartTime, String noDisturbEndTime) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.automaticSleep = automaticSleep;
        this.sleepRemind = sleepRemind;
        this.openNoDisturb = openNoDisturb;
        this.sleepStartTime = sleepStartTime;
        this.sleepEndTime = sleepEndTime;
        this.noDisturbStartTime = noDisturbStartTime;
        this.noDisturbEndTime = noDisturbEndTime;
    }
    @Generated(hash = 244222861)
    public Watch_W516_SleepAndNoDisturbModel() {
    }

    @Override
    public String toString() {
        return "Watch_W516_SleepAndNoDisturbModel{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", automaticSleep=" + automaticSleep +
                ", sleepRemind=" + sleepRemind +
                ", openNoDisturb=" + openNoDisturb +
                ", sleepStartTime='" + sleepStartTime + '\'' +
                ", sleepEndTime='" + sleepEndTime + '\'' +
                ", noDisturbStartTime='" + noDisturbStartTime + '\'' +
                ", noDisturbEndTime='" + noDisturbEndTime + '\'' +
                '}';
    }
}
