package com.isport.brandapp.device.watch.bean;

/**
 * @创建者 bear
 * @创建时间 2019/3/12 14:46
 * @描述
 */
public class WatchHistoryNBean {
//    {
//        "createTime": "2019-03-11T09:13:07.155Z",
//            "dateStr": "string",
//            "deviceId": "string",
//            "extend1": "string",
//            "extend2": "string",
//            "extend3": "string",
//            "heartRateDetailArray": "string",
//            "sleepDetailArray": "string",
//            "stepDetailArray": "string",
//            "stepTarget": 0,
//            "totalSteps": 0,
//            "userId": 0,
//            "wristbandSportDetailId": 0
//    }

    private String createTime;
    private String dateStr;
    private String deviceId;
    private String extend1;
    private String extend2;
    private String extend3;
    private String heartRateDetailArray;
    private String sleepDetailArray;
    private String stepDetailArray;
    private int stepTarget;
    private int totalSteps;
    private String totalDistance;
    private String totalCalories;
    private String totalSleepTime;
    private String userId;
    private String totalDeep;
    private String totalLight;
    private String wristbandSportDetailId;

    @Override
    public String toString() {
        return "WatchHistoryNBean{" +
                "createTime='" + createTime + '\'' +
                ", dateStr='" + dateStr + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", extend1='" + extend1 + '\'' +
                ", extend2='" + extend2 + '\'' +
                ", extend3='" + extend3 + '\'' +
                ", heartRateDetailArray='" + heartRateDetailArray + '\'' +
                ", sleepDetailArray='" + sleepDetailArray + '\'' +
                ", stepDetailArray='" + stepDetailArray + '\'' +
                ", stepTarget=" + stepTarget +
                ", totalSteps=" + totalSteps +
                ", totalDistance='" + totalDistance + '\'' +
                ", totalCalories='" + totalCalories + '\'' +
                ", totalSleepTime='" + totalSleepTime + '\'' +
                ", userId='" + userId + '\'' +
                ", totalDeep='" + totalDeep + '\'' +
                ", totalLight='" + totalLight + '\'' +
                ", wristbandSportDetailId='" + wristbandSportDetailId + '\'' +
                '}';
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3;
    }

    public String getHeartRateDetailArray() {
        return heartRateDetailArray;
    }

    public void setHeartRateDetailArray(String heartRateDetailArray) {
        this.heartRateDetailArray = heartRateDetailArray;
    }

    public String getSleepDetailArray() {
        return sleepDetailArray;
    }

    public void setSleepDetailArray(String sleepDetailArray) {
        this.sleepDetailArray = sleepDetailArray;
    }

    public String getStepDetailArray() {
        return stepDetailArray;
    }

    public void setStepDetailArray(String stepDetailArray) {
        this.stepDetailArray = stepDetailArray;
    }

    public int getStepTarget() {
        return stepTarget;
    }

    public void setStepTarget(int stepTarget) {
        this.stepTarget = stepTarget;
    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWristbandSportDetailId() {
        return wristbandSportDetailId;
    }

    public void setWristbandSportDetailId(String wristbandSportDetailId) {
        this.wristbandSportDetailId = wristbandSportDetailId;
    }

    public String getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(String totalDistance) {
        this.totalDistance = totalDistance;
    }

    public String getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(String totalCalories) {
        this.totalCalories = totalCalories;
    }

    public String getTotalSleepTime() {
        return totalSleepTime;
    }

    public void setTotalSleepTime(String totalSleepTime) {
        this.totalSleepTime = totalSleepTime;
    }

    public String getTotalDeep() {
        return totalDeep;
    }

    public void setTotalDeep(String totalDeep) {
        this.totalDeep = totalDeep;
    }

    public String getTotalLight() {
        return totalLight;
    }

    public void setTotalLight(String totalLight) {
        this.totalLight = totalLight;
    }
}
