package com.isport.blelibrary.db.table.w811w814;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class W81DeviceExerciseHrData {
    @Id
    private Long id;
    private String userId;
    private String deviceId;
    private int avgHr;
    private String hrArray;//心率数据
    private int timeInterval;
    private long startMeasureTime;
    public long getStartMeasureTime() {
        return this.startMeasureTime;
    }
    public void setStartMeasureTime(long startMeasureTime) {
        this.startMeasureTime = startMeasureTime;
    }
    public int getTimeInterval() {
        return this.timeInterval;
    }
    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }
    public String getHrArray() {
        return this.hrArray;
    }
    public void setHrArray(String hrArray) {
        this.hrArray = hrArray;
    }
    public int getAvgHr() {
        return this.avgHr;
    }
    public void setAvgHr(int avgHr) {
        this.avgHr = avgHr;
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
    @Generated(hash = 189682030)
    public W81DeviceExerciseHrData(Long id, String userId, String deviceId,
            int avgHr, String hrArray, int timeInterval, long startMeasureTime) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.avgHr = avgHr;
        this.hrArray = hrArray;
        this.timeInterval = timeInterval;
        this.startMeasureTime = startMeasureTime;
    }
    @Generated(hash = 1567030579)
    public W81DeviceExerciseHrData() {
    }

    @Override
    public String toString() {
        return "W81DeviceExerciseHrData{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", avgHr=" + avgHr +
                ", hrArray='" + hrArray + '\'' +
                ", timeInterval=" + timeInterval +
                ", startMeasureTime=" + startMeasureTime +
                '}';
    }
}
