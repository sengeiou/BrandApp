package com.isport.blelibrary.db.table.sleep;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @Author
 * @Date 2019/1/7
 * @Fuction
 */

@Entity
public class Sleep_Sleepace_SleepNoticeModel {

    @Id
    private Long id;
    private String deviceId;
    private String userId;
    private String sleepNoticeTime;
    private int repeat;
    private boolean isOpen;
    public boolean getIsOpen() {
        return this.isOpen;
    }
    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
    public int getRepeat() {
        return this.repeat;
    }
    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }
    public String getSleepNoticeTime() {
        return this.sleepNoticeTime;
    }
    public void setSleepNoticeTime(String sleepNoticeTime) {
        this.sleepNoticeTime = sleepNoticeTime;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 2140271140)
    public Sleep_Sleepace_SleepNoticeModel(Long id, String deviceId, String userId,
            String sleepNoticeTime, int repeat, boolean isOpen) {
        this.id = id;
        this.deviceId = deviceId;
        this.userId = userId;
        this.sleepNoticeTime = sleepNoticeTime;
        this.repeat = repeat;
        this.isOpen = isOpen;
    }
    @Generated(hash = 1958592349)
    public Sleep_Sleepace_SleepNoticeModel() {
    }


}
