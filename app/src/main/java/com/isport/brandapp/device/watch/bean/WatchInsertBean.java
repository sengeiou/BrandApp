package com.isport.brandapp.device.watch.bean;

/**
 * @创建者 bear
 * @创建时间 2019/3/12 10:25
 * @描述
 */
public class WatchInsertBean {

//              "dateStr":"string",
//              "deviceId":"string",
//              "heartRateDetailArray":"string",
//              "sleepDetailArray":"string",
//              "stepDetailArray":"string",
//              "totalSteps":0,
//              "userId":0,

    private String dateStr;
    private String deviceId;
    private String userId;
    private int totalSteps;
    private String stepDetailArray;
    private String sleepDetailArray;
    private String heartRateDetailArray;
    private String totalSleepTime;
    private String totalDistance;
    private String totalCalories;
    private String isHaveHeartRate;
    private String totalDeep;
    private String totalLight;

    @Override
    public String toString() {
        return "WatchInsertBean{" +
                "dateStr='" + dateStr + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", userId='" + userId + '\'' +
                ", totalSteps=" + totalSteps +
                ", stepDetailArray='" + stepDetailArray + '\'' +
                ", sleepDetailArray='" + sleepDetailArray + '\'' +
                ", heartRateDetailArray='" + heartRateDetailArray + '\'' +
                ", totalSleepTime='" + totalSleepTime + '\'' +
                ", totalDistance='" + totalDistance + '\'' +
                ", totalCalories='" + totalCalories + '\'' +
                ", isHaveHeartRate='" + isHaveHeartRate + '\'' +
                ", totalDeep='" + totalDeep + '\'' +
                ", totalLight='" + totalLight + '\'' +
                '}';
    }

    public String getTotalLight() {
        return totalLight;
    }

    public void setTotalLight(String totalLight) {
        this.totalLight = totalLight;
    }

    public String getTotalDeep() {

        return totalDeep;
    }

    public void setTotalDeep(String totalDeep) {
        this.totalDeep = totalDeep;
    }

    public String getIsHaveHeartRate() {
        return isHaveHeartRate;
    }

    public void setIsHaveHeartRate(String isHaveHeartRate) {
        this.isHaveHeartRate = isHaveHeartRate;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
    }

    public String getStepDetailArray() {
        return stepDetailArray;
    }

    public void setStepDetailArray(String stepDetailArray) {
        this.stepDetailArray = stepDetailArray;
    }

    public String getSleepDetailArray() {
        return sleepDetailArray;
    }

    public void setSleepDetailArray(String sleepDetailArray) {
        this.sleepDetailArray = sleepDetailArray;
    }

    public String getHeartRateDetailArray() {
        return heartRateDetailArray;
    }

    public void setHeartRateDetailArray(String heartRateDetailArray) {
        this.heartRateDetailArray = heartRateDetailArray;
    }

    public String getTotalSleepTime() {
        return totalSleepTime;
    }

    public void setTotalSleepTime(String totalSleepTime) {
        this.totalSleepTime = totalSleepTime;
    }

    public String getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(String totalCalories) {
        this.totalCalories = totalCalories;
    }

    public String getTotalDistance() {

        return totalDistance;
    }

    public void setTotalDistance(String totalDistance) {
        this.totalDistance = totalDistance;
    }
}
