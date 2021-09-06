package com.isport.blelibrary.db.table.w811w814;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class W81DeviceDetailData {
    @Id
    private Long id;
    private String userId;
    private String deviceId;
    private String wristbandSportDetailId;
    private String dateStr;
    private Long timestamp;
    private int step;
    private int dis;//单位是米
    private int cal;
    private int totalTime;
    private int restfulTime;
    private int lightTime;
    private int soberTime;
    private String stepArray;
    private String sleepArray;//
    private String hrArray;//心率数据
    private int hasSleep;// HAS 2  NO 3
    private int hasHR;// HAS 0  NO 1
    private int avgHR;
    private int timeInterval;
    public int getTimeInterval() {
        return this.timeInterval;
    }
    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }
    public int getAvgHR() {
        return this.avgHR;
    }
    public void setAvgHR(int avgHR) {
        this.avgHR = avgHR;
    }
    public int getHasHR() {
        return this.hasHR;
    }
    public void setHasHR(int hasHR) {
        this.hasHR = hasHR;
    }
    public int getHasSleep() {
        return this.hasSleep;
    }
    public void setHasSleep(int hasSleep) {
        this.hasSleep = hasSleep;
    }
    public String getHrArray() {
        return this.hrArray;
    }
    public void setHrArray(String hrArray) {
        this.hrArray = hrArray;
    }
    public String getSleepArray() {
        return this.sleepArray;
    }
    public void setSleepArray(String sleepArray) {
        this.sleepArray = sleepArray;
    }
    public String getStepArray() {
        return this.stepArray;
    }
    public void setStepArray(String stepArray) {
        this.stepArray = stepArray;
    }
    public int getSoberTime() {
        return this.soberTime;
    }
    public void setSoberTime(int soberTime) {
        this.soberTime = soberTime;
    }
    public int getLightTime() {
        return this.lightTime;
    }
    public void setLightTime(int lightTime) {
        this.lightTime = lightTime;
    }
    public int getRestfulTime() {
        return this.restfulTime;
    }
    public void setRestfulTime(int restfulTime) {
        this.restfulTime = restfulTime;
    }
    public int getTotalTime() {
        return this.totalTime;
    }
    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
    public int getCal() {
        return this.cal;
    }
    public void setCal(int cal) {
        this.cal = cal;
    }
    public int getDis() {
        return this.dis;
    }
    public void setDis(int dis) {
        this.dis = dis;
    }
    public int getStep() {
        return this.step;
    }
    public void setStep(int step) {
        this.step = step;
    }
    public Long getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
    public String getDateStr() {
        return this.dateStr;
    }
    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }
    public String getWristbandSportDetailId() {
        return this.wristbandSportDetailId;
    }
    public void setWristbandSportDetailId(String wristbandSportDetailId) {
        this.wristbandSportDetailId = wristbandSportDetailId;
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
    @Generated(hash = 317133054)
    public W81DeviceDetailData(Long id, String userId, String deviceId,
            String wristbandSportDetailId, String dateStr, Long timestamp,
            int step, int dis, int cal, int totalTime, int restfulTime,
            int lightTime, int soberTime, String stepArray, String sleepArray,
            String hrArray, int hasSleep, int hasHR, int avgHR, int timeInterval) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.wristbandSportDetailId = wristbandSportDetailId;
        this.dateStr = dateStr;
        this.timestamp = timestamp;
        this.step = step;
        this.dis = dis;
        this.cal = cal;
        this.totalTime = totalTime;
        this.restfulTime = restfulTime;
        this.lightTime = lightTime;
        this.soberTime = soberTime;
        this.stepArray = stepArray;
        this.sleepArray = sleepArray;
        this.hrArray = hrArray;
        this.hasSleep = hasSleep;
        this.hasHR = hasHR;
        this.avgHR = avgHR;
        this.timeInterval = timeInterval;
    }
    @Generated(hash = 1359720147)
    public W81DeviceDetailData() {
    }

    @Override
    public String toString() {
        return "W81DeviceDetailData{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", wristbandSportDetailId='" + wristbandSportDetailId + '\'' +
                ", dateStr='" + dateStr + '\'' +
                ", timestamp=" + timestamp +
                ", step=" + step +
                ", dis=" + dis +
                ", cal=" + cal +
                ", totalTime=" + totalTime +
                ", restfulTime=" + restfulTime +
                ", lightTime=" + lightTime +
                ", soberTime=" + soberTime +
                ", stepArray='" + stepArray + '\'' +
                ", sleepArray='" + sleepArray + '\'' +
                ", hrArray='" + hrArray + '\'' +
                ", hasSleep=" + hasSleep +
                ", hasHR=" + hasHR +
                ", avgHR=" + avgHR +
                ", timeInterval=" + timeInterval +
                '}';
    }
}
