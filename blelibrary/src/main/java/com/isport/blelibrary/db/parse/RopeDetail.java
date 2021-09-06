package com.isport.blelibrary.db.parse;

public class RopeDetail {
    /**
     * "averageFrequency": 0,
     * "averageHeartRate": 0,
     * "deviceId": "string",
     * "endTime": "2020-09-16T08:01:38.942Z",
     * "exerciseType": 0,
     * "frequencyArray": "string",
     * "heartRateDetailArray": "string",
     * "maxFrequency": 0,
     * "singleMaxSkippingNum": 0,
     * "skippingDuration": 0,
     * "skippingNum": 0,
     * "startTime": "2020-09-16T08:01:38.942Z",
     * "stumbleArray": "string",
     * "stumbleNum": 0,
     * "totalCalories": 0,
     * "userId": 0
     */
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
    int challengeType;

    public int getAverageFrequency() {
        return averageFrequency;
    }

    public void setAverageFrequency(int averageFrequency) {
        this.averageFrequency = averageFrequency;
    }

    public int getAverageHeartRate() {
        return averageHeartRate;
    }

    public void setAverageHeartRate(int averageHeartRate) {
        this.averageHeartRate = averageHeartRate;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(int exerciseType) {
        this.exerciseType = exerciseType;
    }

    public int getMaxFrequency() {
        return maxFrequency;
    }

    public void setMaxFrequency(int maxFrequency) {
        this.maxFrequency = maxFrequency;
    }

    public int getSingleMaxSkippingNum() {
        return singleMaxSkippingNum;
    }

    public void setSingleMaxSkippingNum(int singleMaxSkippingNum) {
        this.singleMaxSkippingNum = singleMaxSkippingNum;
    }

    public int getSkippingDuration() {
        return skippingDuration;
    }

    public void setSkippingDuration(int skippingDuration) {
        this.skippingDuration = skippingDuration;
    }

    public int getSkippingNum() {
        return skippingNum;
    }

    public void setSkippingNum(int skippingNum) {
        this.skippingNum = skippingNum;
    }

    public int getStumbleNum() {
        return stumbleNum;
    }

    public void setStumbleNum(int stumbleNum) {
        this.stumbleNum = stumbleNum;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    public String getFrequencyArray() {
        return frequencyArray;
    }

    public void setFrequencyArray(String frequencyArray) {
        this.frequencyArray = frequencyArray;
    }

    public String getHeartRateDetailArray() {
        return heartRateDetailArray;
    }

    public void setHeartRateDetailArray(String heartRateDetailArray) {
        this.heartRateDetailArray = heartRateDetailArray;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStumbleArray() {
        return stumbleArray;
    }

    public void setStumbleArray(String stumbleArray) {
        this.stumbleArray = stumbleArray;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getChallengeType() {
        return challengeType;
    }

    public void setChallengeType(int challengeType) {
        this.challengeType = challengeType;
    }

    @Override
    public String toString() {
        return "RopeDetail{" +
                "averageFrequency=" + averageFrequency +
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
                ", challengeType=" + challengeType +
                '}';
    }
}
