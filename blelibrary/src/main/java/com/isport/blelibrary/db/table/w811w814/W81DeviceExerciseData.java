package com.isport.blelibrary.db.table.w811w814;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class W81DeviceExerciseData {
    @Id
    private Long id;
    private String userId;
    private String deviceId;
    private String wristbandSportDetailId;
    private String dateStr;
    private Long startTimestamp;
    private Long endTimestamp;
    private String vaildTimeLength;
    private String exerciseType;
    private String totalSteps;
    private String totalDistance;
    private String totalCalories;
    private String avgHr;
    private String hrArray;//心率数据
    private String stepArray;
    private String distanceArray;
    private String calorieArray;
    private int hasHR;// HAS 0  NO 1
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
    public int getHasHR() {
        return this.hasHR;
    }
    public void setHasHR(int hasHR) {
        this.hasHR = hasHR;
    }
    public String getCalorieArray() {
        return this.calorieArray;
    }
    public void setCalorieArray(String calorieArray) {
        this.calorieArray = calorieArray;
    }
    public String getDistanceArray() {
        return this.distanceArray;
    }
    public void setDistanceArray(String distanceArray) {
        this.distanceArray = distanceArray;
    }
    public String getStepArray() {
        return this.stepArray;
    }
    public void setStepArray(String stepArray) {
        this.stepArray = stepArray;
    }
    public String getHrArray() {
        return this.hrArray;
    }
    public void setHrArray(String hrArray) {
        this.hrArray = hrArray;
    }
    public String getAvgHr() {
        return this.avgHr;
    }
    public void setAvgHr(String avgHr) {
        this.avgHr = avgHr;
    }
    public String getTotalCalories() {
        return this.totalCalories;
    }
    public void setTotalCalories(String totalCalories) {
        this.totalCalories = totalCalories;
    }
    public String getTotalDistance() {
        return this.totalDistance;
    }
    public void setTotalDistance(String totalDistance) {
        this.totalDistance = totalDistance;
    }
    public String getTotalSteps() {
        return this.totalSteps;
    }
    public void setTotalSteps(String totalSteps) {
        this.totalSteps = totalSteps;
    }
    public String getExerciseType() {
        return this.exerciseType;
    }
    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }
    public String getVaildTimeLength() {
        return this.vaildTimeLength;
    }
    public void setVaildTimeLength(String vaildTimeLength) {
        this.vaildTimeLength = vaildTimeLength;
    }
    public Long getEndTimestamp() {
        return this.endTimestamp;
    }
    public void setEndTimestamp(Long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }
    public Long getStartTimestamp() {
        return this.startTimestamp;
    }
    public void setStartTimestamp(Long startTimestamp) {
        this.startTimestamp = startTimestamp;
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
    @Generated(hash = 2136335576)
    public W81DeviceExerciseData(Long id, String userId, String deviceId,
            String wristbandSportDetailId, String dateStr, Long startTimestamp,
            Long endTimestamp, String vaildTimeLength, String exerciseType,
            String totalSteps, String totalDistance, String totalCalories,
            String avgHr, String hrArray, String stepArray, String distanceArray,
            String calorieArray, int hasHR, int timeInterval, long startMeasureTime) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.wristbandSportDetailId = wristbandSportDetailId;
        this.dateStr = dateStr;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.vaildTimeLength = vaildTimeLength;
        this.exerciseType = exerciseType;
        this.totalSteps = totalSteps;
        this.totalDistance = totalDistance;
        this.totalCalories = totalCalories;
        this.avgHr = avgHr;
        this.hrArray = hrArray;
        this.stepArray = stepArray;
        this.distanceArray = distanceArray;
        this.calorieArray = calorieArray;
        this.hasHR = hasHR;
        this.timeInterval = timeInterval;
        this.startMeasureTime = startMeasureTime;
    }
    @Generated(hash = 979195731)
    public W81DeviceExerciseData() {
    }

}
