package com.isport.brandapp.device.sleep.bean;

/**
 * @Author
 * @Date 2019/1/18
 * @Fuction
 */

public class SleepInsertBean {

    private int averageBreathRate;
    private int averageHeartBeatRate;
    private int bodyMovementTimes;
    private int breathPauseAllTime;
    private int breathPauseTimes;
    private int breathRateFastAllTime;
    private int breathRateSlowAllTime;
    private String dateStr;
    private long timestamp;
    private String deviceId;
    private String userId;
    public int deepSleepPercent;
    private int fallSleepAllTime;
    private int heartBeatPauseAllTime;
    private int heartBeatPauseTimes;
    private int heartBeatRateFastAllTime;
    private int heartBeatRateSlowAllTime;
    private int deepSleepDuration;
    private int leaveBedAllTime;
    private int leaveBedTimes;
    private int maxHeartBeatRate;
    private int minHeartBeatRate;
    private int maxBreathRate;
    private int minBreathRate;
    public int sleepDuration;
    private String trunOverStatusAry;
    private int turnOverTimes;
    private String breathRateValueArray;
    private String heartBeatRateValueArray;

    @Override
    public String toString() {
        return "SleepInsertBean{" +
                "averageBreathRate=" + averageBreathRate +
                ", bodyMovementTimes=" + bodyMovementTimes +
                ", breathPauseAllTime=" + breathPauseAllTime +
                ", breathPauseTimes=" + breathPauseTimes +
                ", breathRateFastAllTime=" + breathRateFastAllTime +
                ", breathRateSlowAllTime=" + breathRateSlowAllTime +
                ", dateStr='" + dateStr + '\'' +
                ", timestamp=" + timestamp +
                ", deviceId='" + deviceId + '\'' +
                ", userId=" + userId +
                ", deepSleepPercent=" + deepSleepPercent +
                ", fallSleepAllTime=" + fallSleepAllTime +
                ", heartBeatPauseAllTime=" + heartBeatPauseAllTime +
                ", heartBeatPauseTimes=" + heartBeatPauseTimes +
                ", heartBeatRateFastAllTime=" + heartBeatRateFastAllTime +
                ", heartBeatRateSlowAllTime=" + heartBeatRateSlowAllTime +
                ", deepSleepDuration=" + deepSleepDuration +
                ", leaveBedAllTime=" + leaveBedAllTime +
                ", leaveBedTimes=" + leaveBedTimes +
                ", maxHeartBeatRate=" + maxHeartBeatRate +
                ", minHeartBeatRate=" + minHeartBeatRate +
                ", maxBreathRate=" + maxBreathRate +
                ", minBreathRate=" + minBreathRate +
                ", sleepDuration=" + sleepDuration +
                ", trunOverStatusAry='" + trunOverStatusAry + '\'' +
                ", turnOverTimes=" + turnOverTimes +
                ", breathRateValueArray='" + breathRateValueArray + '\'' +
                ", heartBeatRateValueArray='" + heartBeatRateValueArray + '\'' +
                ", averageHeartBeatRate=" + averageHeartBeatRate +
                '}';
    }

    public int getAverageBreathRate() {
        return averageBreathRate;
    }

    public void setAverageBreathRate(int averageBreathRate) {
        this.averageBreathRate = averageBreathRate;
    }

    public int getBodyMovementTimes() {
        return bodyMovementTimes;
    }

    public void setBodyMovementTimes(int bodyMovementTimes) {
        this.bodyMovementTimes = bodyMovementTimes;
    }

    public int getBreathPauseAllTime() {
        return breathPauseAllTime;
    }

    public void setBreathPauseAllTime(int breathPauseAllTime) {
        this.breathPauseAllTime = breathPauseAllTime;
    }

    public int getBreathPauseTimes() {
        return breathPauseTimes;
    }

    public void setBreathPauseTimes(int breathPauseTimes) {
        this.breathPauseTimes = breathPauseTimes;
    }

    public int getBreathRateFastAllTime() {
        return breathRateFastAllTime;
    }

    public void setBreathRateFastAllTime(int breathRateFastAllTime) {
        this.breathRateFastAllTime = breathRateFastAllTime;
    }

    public int getBreathRateSlowAllTime() {
        return breathRateSlowAllTime;
    }

    public void setBreathRateSlowAllTime(int breathRateSlowAllTime) {
        this.breathRateSlowAllTime = breathRateSlowAllTime;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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

    public int getDeepSleepPercent() {
        return deepSleepPercent;
    }

    public void setDeepSleepPercent(int deepSleepPercent) {
        this.deepSleepPercent = deepSleepPercent;
    }

    public int getFallSleepAllTime() {
        return fallSleepAllTime;
    }

    public void setFallSleepAllTime(int fallSleepAllTime) {
        this.fallSleepAllTime = fallSleepAllTime;
    }

    public int getHeartBeatPauseAllTime() {
        return heartBeatPauseAllTime;
    }

    public void setHeartBeatPauseAllTime(int heartBeatPauseAllTime) {
        this.heartBeatPauseAllTime = heartBeatPauseAllTime;
    }

    public int getHeartBeatPauseTimes() {
        return heartBeatPauseTimes;
    }

    public void setHeartBeatPauseTimes(int heartBeatPauseTimes) {
        this.heartBeatPauseTimes = heartBeatPauseTimes;
    }

    public int getHeartBeatRateFastAllTime() {
        return heartBeatRateFastAllTime;
    }

    public void setHeartBeatRateFastAllTime(int heartBeatRateFastAllTime) {
        this.heartBeatRateFastAllTime = heartBeatRateFastAllTime;
    }

    public int getHeartBeatRateSlowAllTime() {
        return heartBeatRateSlowAllTime;
    }

    public void setHeartBeatRateSlowAllTime(int heartBeatRateSlowAllTime) {
        this.heartBeatRateSlowAllTime = heartBeatRateSlowAllTime;
    }

    public int getDeepSleepDuration() {
        return deepSleepDuration;
    }

    public void setDeepSleepDuration(int deepSleepDuration) {
        this.deepSleepDuration = deepSleepDuration;
    }

    public int getLeaveBedAllTime() {
        return leaveBedAllTime;
    }

    public void setLeaveBedAllTime(int leaveBedAllTime) {
        this.leaveBedAllTime = leaveBedAllTime;
    }

    public int getLeaveBedTimes() {
        return leaveBedTimes;
    }

    public void setLeaveBedTimes(int leaveBedTimes) {
        this.leaveBedTimes = leaveBedTimes;
    }

    public int getMaxHeartBeatRate() {
        return maxHeartBeatRate;
    }

    public void setMaxHeartBeatRate(int maxHeartBeatRate) {
        this.maxHeartBeatRate = maxHeartBeatRate;
    }

    public int getMinHeartBeatRate() {
        return minHeartBeatRate;
    }

    public void setMinHeartBeatRate(int minHeartBeatRate) {
        this.minHeartBeatRate = minHeartBeatRate;
    }

    public int getMaxBreathRate() {
        return maxBreathRate;
    }

    public void setMaxBreathRate(int maxBreathRate) {
        this.maxBreathRate = maxBreathRate;
    }

    public int getMinBreathRate() {
        return minBreathRate;
    }

    public void setMinBreathRate(int minBreathRate) {
        this.minBreathRate = minBreathRate;
    }

    public int getSleepDuration() {
        return sleepDuration;
    }

    public void setSleepDuration(int sleepDuration) {
        this.sleepDuration = sleepDuration;
    }

    public String getTrunOverStatusAry() {
        return trunOverStatusAry;
    }

    public void setTrunOverStatusAry(String trunOverStatusAry) {
        this.trunOverStatusAry = trunOverStatusAry;
    }

    public int getTurnOverTimes() {
        return turnOverTimes;
    }

    public void setTurnOverTimes(int turnOverTimes) {
        this.turnOverTimes = turnOverTimes;
    }

    public String getBreathRateValueArray() {
        return breathRateValueArray;
    }

    public void setBreathRateValueArray(String breathRateValueArray) {
        this.breathRateValueArray = breathRateValueArray;
    }

    public String getHeartBeatRateValueArray() {
        return heartBeatRateValueArray;
    }

    public void setHeartBeatRateValueArray(String heartBeatRateValueArray) {
        this.heartBeatRateValueArray = heartBeatRateValueArray;
    }

    public int getAverageHeartBeatRate() {
        return averageHeartBeatRate;
    }

    public void setAverageHeartBeatRate(int averageHeartBeatRate) {
        this.averageHeartBeatRate = averageHeartBeatRate;
    }
}
