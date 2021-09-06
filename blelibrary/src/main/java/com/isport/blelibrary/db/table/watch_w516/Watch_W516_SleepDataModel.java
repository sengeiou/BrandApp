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
public class Watch_W516_SleepDataModel {

//    userId	用户ID	string
//    deviceId	设备ID	string
//    timestamp	同步时间戳	string
//    dateStr	同步日期	string
//    totalSleep	当天总睡眠时间	string
//    totalDeepSleep	当天总深睡眠时间	string
//    totalAwake	当天总清醒时间	string
//    totalLightSleep	当天总浅睡眠时间	string

    @Id
    private Long id;
    private String userId;
    private String deviceId;
    private long timestamp;
    private String dateStr;
    private int totalSleep;
    private int totalDeepSleep;
    private int totalAwake;
    private int totalLightSleep;
    public int getTotalLightSleep() {
        return this.totalLightSleep;
    }
    public void setTotalLightSleep(int totalLightSleep) {
        this.totalLightSleep = totalLightSleep;
    }
    public int getTotalAwake() {
        return this.totalAwake;
    }
    public void setTotalAwake(int totalAwake) {
        this.totalAwake = totalAwake;
    }
    public int getTotalDeepSleep() {
        return this.totalDeepSleep;
    }
    public void setTotalDeepSleep(int totalDeepSleep) {
        this.totalDeepSleep = totalDeepSleep;
    }
    public int getTotalSleep() {
        return this.totalSleep;
    }
    public void setTotalSleep(int totalSleep) {
        this.totalSleep = totalSleep;
    }
    public String getDateStr() {
        return this.dateStr;
    }
    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }
    public long getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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
    @Generated(hash = 1385091208)
    public Watch_W516_SleepDataModel(Long id, String userId, String deviceId,
            long timestamp, String dateStr, int totalSleep, int totalDeepSleep,
            int totalAwake, int totalLightSleep) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.dateStr = dateStr;
        this.totalSleep = totalSleep;
        this.totalDeepSleep = totalDeepSleep;
        this.totalAwake = totalAwake;
        this.totalLightSleep = totalLightSleep;
    }
    @Generated(hash = 1991993172)
    public Watch_W516_SleepDataModel() {
    }


}
