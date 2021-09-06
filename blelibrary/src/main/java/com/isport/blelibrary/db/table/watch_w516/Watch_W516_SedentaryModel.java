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
public class Watch_W516_SedentaryModel {

//    手表久坐提醒
//    userId	用户ID	string
//    deviceId	设备ID	string
//    longSitTimeLong	久坐提醒的时长	string

    @Id
    private Long id;
    private String userId;
    private String deviceId;
    private boolean isEnable;
    private int longSitTimeLong;
    private String longSitStartTime;
    private String longSitEndTime;
    public String getLongSitEndTime() {
        return this.longSitEndTime;
    }
    public void setLongSitEndTime(String longSitEndTime) {
        this.longSitEndTime = longSitEndTime;
    }
    public String getLongSitStartTime() {
        return this.longSitStartTime;
    }
    public void setLongSitStartTime(String longSitStartTime) {
        this.longSitStartTime = longSitStartTime;
    }
    public int getLongSitTimeLong() {
        return this.longSitTimeLong;
    }
    public void setLongSitTimeLong(int longSitTimeLong) {
        this.longSitTimeLong = longSitTimeLong;
    }
    public boolean getIsEnable() {
        return this.isEnable;
    }
    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
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
    @Generated(hash = 3493714)
    public Watch_W516_SedentaryModel(Long id, String userId, String deviceId,
            boolean isEnable, int longSitTimeLong, String longSitStartTime,
            String longSitEndTime) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.isEnable = isEnable;
        this.longSitTimeLong = longSitTimeLong;
        this.longSitStartTime = longSitStartTime;
        this.longSitEndTime = longSitEndTime;
    }
    @Generated(hash = 1095004945)
    public Watch_W516_SedentaryModel() {
    }

}
