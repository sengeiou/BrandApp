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
public class Bracelet_W311_24HDataModel {

//    userId	用户ID	string
//    deviceId	设备ID	string
//    timestamp	同步时间戳	string
//    dateStr	同步日期	string
//    totalSteps	当天总步数	long
//    totalDistance	当天总距离	float
//    totalCalories	当天总卡路里	long
//    todayDetailDataDic	"详细数据(一分钟的步数值，
//    心率值，睡眠状态)"	string	stepArray,sleepArray,hrArray

    @Id
    private Long    id;
    private String     userId;
    private String  deviceId;
    private long    timestamp;
    private String     reportId;
    private String  dateStr;
    private long    totalSteps;
    private float   totalDistance;
    private long    totalCalories;
    private String  stepArray;
    private String  sleepArray;
    private String  hrArray;
    private int hasSleep;// HAS 2  NO 3
    private int hasHR;// HAS 0  NO 1
    private int     avgHR;
    private String  totalSleepTime;
    public String getTotalSleepTime() {
        return this.totalSleepTime;
    }
    public void setTotalSleepTime(String totalSleepTime) {
        this.totalSleepTime = totalSleepTime;
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
    public long getTotalCalories() {
        return this.totalCalories;
    }
    public void setTotalCalories(long totalCalories) {
        this.totalCalories = totalCalories;
    }
    public float getTotalDistance() {
        return this.totalDistance;
    }
    public void setTotalDistance(float totalDistance) {
        this.totalDistance = totalDistance;
    }
    public long getTotalSteps() {
        return this.totalSteps;
    }
    public void setTotalSteps(long totalSteps) {
        this.totalSteps = totalSteps;
    }
    public String getDateStr() {
        return this.dateStr;
    }
    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }
    public String getReportId() {
        return this.reportId;
    }
    public void setReportId(String reportId) {
        this.reportId = reportId;
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
    @Generated(hash = 1631851927)
    public Bracelet_W311_24HDataModel(Long id, String userId, String deviceId,
            long timestamp, String reportId, String dateStr, long totalSteps,
            float totalDistance, long totalCalories, String stepArray,
            String sleepArray, String hrArray, int hasSleep, int hasHR, int avgHR,
            String totalSleepTime) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.reportId = reportId;
        this.dateStr = dateStr;
        this.totalSteps = totalSteps;
        this.totalDistance = totalDistance;
        this.totalCalories = totalCalories;
        this.stepArray = stepArray;
        this.sleepArray = sleepArray;
        this.hrArray = hrArray;
        this.hasSleep = hasSleep;
        this.hasHR = hasHR;
        this.avgHR = avgHR;
        this.totalSleepTime = totalSleepTime;
    }
    @Generated(hash = 1544035611)
    public Bracelet_W311_24HDataModel() {
    }


    @Override
    public String toString() {
        return "Bracelet_W311_24HDataModel{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", timestamp=" + timestamp +
                ", reportId='" + reportId + '\'' +
                ", dateStr='" + dateStr + '\'' +
                ", totalSteps=" + totalSteps +
                ", totalDistance=" + totalDistance +
                ", totalCalories=" + totalCalories +
                ", stepArray='" + stepArray + '\'' +
                ", sleepArray='" + sleepArray + '\'' +
                ", hrArray='" + hrArray + '\'' +
                ", hasSleep=" + hasSleep +
                ", hasHR=" + hasHR +
                ", avgHR=" + avgHR +
                ", totalSleepTime='" + totalSleepTime + '\'' +
                '}';
    }
}
