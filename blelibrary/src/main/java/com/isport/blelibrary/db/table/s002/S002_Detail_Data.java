package com.isport.blelibrary.db.table.s002;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class S002_Detail_Data {
    @Id
    private Long id;
    int averageFrequency;
    int averageHeartRate;
    String deviceId;
    String endTime;
    int exerciseType;
    int maxFrequency;
    int singleMaxSkippingNum;
    int skippingDuration;
    int skippingNum;
    int stumbleNum;
    int totalCalories;
    String frequencyArray;
    String heartRateDetailArray;
    String startTime;
    String stumbleArray;
    String userId;
    long timestamp;
    int upgradeState;
    int challengeType;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStumbleArray() {
        return this.stumbleArray;
    }

    public void setStumbleArray(String stumbleArray) {
        this.stumbleArray = stumbleArray;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getHeartRateDetailArray() {
        return this.heartRateDetailArray;
    }

    public void setHeartRateDetailArray(String heartRateDetailArray) {
        this.heartRateDetailArray = heartRateDetailArray;
    }

    public String getFrequencyArray() {
        return this.frequencyArray;
    }

    public void setFrequencyArray(String frequencyArray) {
        this.frequencyArray = frequencyArray;
    }

    public int getTotalCalories() {
        return this.totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    public int getStumbleNum() {
        return this.stumbleNum;
    }

    public void setStumbleNum(int stumbleNum) {
        this.stumbleNum = stumbleNum;
    }

    public int getSkippingNum() {
        return this.skippingNum;
    }

    public void setSkippingNum(int skippingNum) {
        this.skippingNum = skippingNum;
    }

    public int getSkippingDuration() {
        return this.skippingDuration;
    }

    public void setSkippingDuration(int skippingDuration) {
        this.skippingDuration = skippingDuration;
    }

    public int getSingleMaxSkippingNum() {
        return this.singleMaxSkippingNum;
    }

    public void setSingleMaxSkippingNum(int singleMaxSkippingNum) {
        this.singleMaxSkippingNum = singleMaxSkippingNum;
    }

    public int getMaxFrequency() {
        return this.maxFrequency;
    }

    public void setMaxFrequency(int maxFrequency) {
        this.maxFrequency = maxFrequency;
    }

    public int getExerciseType() {
        return this.exerciseType;
    }

    public void setExerciseType(int exerciseType) {
        this.exerciseType = exerciseType;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getAverageHeartRate() {
        return this.averageHeartRate;
    }

    public void setAverageHeartRate(int averageHeartRate) {
        this.averageHeartRate = averageHeartRate;
    }

    public int getAverageFrequency() {
        return this.averageFrequency;
    }

    public void setAverageFrequency(int averageFrequency) {
        this.averageFrequency = averageFrequency;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUpgradeState() {
        return this.upgradeState;
    }

    public void setUpgradeState(int upgradeState) {
        this.upgradeState = upgradeState;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Generated(hash = 2140300074)
    public S002_Detail_Data(Long id, int averageFrequency, int averageHeartRate, String deviceId,
                            String endTime, int exerciseType, int maxFrequency, int singleMaxSkippingNum,
                            int skippingDuration, int skippingNum, int stumbleNum, int totalCalories,
                            String frequencyArray, String heartRateDetailArray, String startTime, String stumbleArray,
                            String userId, long timestamp, int upgradeState, int challengeType) {
        this.id = id;
        this.averageFrequency = averageFrequency;
        this.averageHeartRate = averageHeartRate;
        this.deviceId = deviceId;
        this.endTime = endTime;
        this.exerciseType = exerciseType;
        this.maxFrequency = maxFrequency;
        this.singleMaxSkippingNum = singleMaxSkippingNum;
        this.skippingDuration = skippingDuration;
        this.skippingNum = skippingNum;
        this.stumbleNum = stumbleNum;
        this.totalCalories = totalCalories;
        this.frequencyArray = frequencyArray;
        this.heartRateDetailArray = heartRateDetailArray;
        this.startTime = startTime;
        this.stumbleArray = stumbleArray;
        this.userId = userId;
        this.timestamp = timestamp;
        this.upgradeState = upgradeState;
        this.challengeType = challengeType;
    }

    @Generated(hash = 819254279)
    public S002_Detail_Data() {
    }

    @Override
    public String toString() {
        return "S002_Detail_Data{" +
                "id=" + id +
                ", averageFrequency=" + averageFrequency +
                ", averageHeartRate=" + averageHeartRate +
                ", deviceId='" + deviceId + '\'' +
                ", endTime='" + endTime + '\'' +
                ", exerciseType=" + exerciseType +
                ", maxFrequency=" + maxFrequency +
                ", singleMaxSkippingNum=" + singleMaxSkippingNum +
                ", skippingDuration=" + skippingDuration +
                ", skippingNum=" + skippingNum +
                ", stumbleNum=" + stumbleNum +
                ", totalCalories=" + totalCalories +
                ", frequencyArray='" + frequencyArray + '\'' +
                ", heartRateDetailArray='" + heartRateDetailArray + '\'' +
                ", startTime='" + startTime + '\'' +
                ", stumbleArray='" + stumbleArray + '\'' +
                ", userId='" + userId + '\'' +
                ", timestamp=" + timestamp +
                ", upgradeState=" + upgradeState +
                ", challengeType=" + challengeType +
                '}';
    }

    public int getChallengeType() {
        return this.challengeType;
    }

    public void setChallengeType(int challengeType) {
        this.challengeType = challengeType;
    }
}
