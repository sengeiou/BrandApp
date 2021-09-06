package com.isport.blelibrary.db.table.watch;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @Author
 * @Date 2019/1/8
 * @Fuction
 */

@Entity
public class Watch_SmartBand_ScreenTimeModel {

    @Id
    private Long id;
    private String deviceId;
    private String userId;
    private int time;
    public int getTime() {
        return this.time;
    }
    public void setTime(int time) {
        this.time = time;
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
    @Generated(hash = 2074153266)
    public Watch_SmartBand_ScreenTimeModel(Long id, String deviceId, String userId,
            int time) {
        this.id = id;
        this.deviceId = deviceId;
        this.userId = userId;
        this.time = time;
    }
    @Generated(hash = 1734109190)
    public Watch_SmartBand_ScreenTimeModel() {
    }


}
