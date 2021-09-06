package com.isport.blelibrary.db.table.sleep;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * @Author
 * @Date 2019/1/7
 * @Fuction   睡眠数据
 */

@Entity
public class Sleep_Sleepace_DataModel implements Serializable{

    @Id
    private Long id;
    private String deviceId;
    private String userId;
    private String dateStr;
    private long timestamp;
    private String reportId;
    private String trunOverStatusAry;
    private String breathRateAry;
    private String heartRateAry;
    private int averageBreathRate;
    private int averageHeartBeatRate;
    private int leaveBedTimes;
    private int turnOverTimes;
    private int bodyMovementTimes;
    private int heartBeatPauseTimes;
    private int breathPauseTimes;
    private int heartBeatPauseAllTime;
    private int breathPauseAllTime;
    private int leaveBedAllTime;
    private int maxHeartBeatRate;
    private int minHeartBeatRate;
    private int maxBreathRate;
    private int minBreathRate;
    private int heartBeatRateFastAllTime;
    private int heartBeatRateSlowAllTime;
    private int breathRateFastAllTime;
    private int breathRateSlowAllTime;

    private int fallAlseepAllTime;
    public int sleepDuration;
    public int deepSleepAllTime;
    public int deepSleepPercent;
    public int getDeepSleepPercent() {
        return this.deepSleepPercent;
    }
    public void setDeepSleepPercent(int deepSleepPercent) {
        this.deepSleepPercent = deepSleepPercent;
    }
    public int getDeepSleepAllTime() {
        return this.deepSleepAllTime;
    }
    public void setDeepSleepAllTime(int deepSleepAllTime) {
        this.deepSleepAllTime = deepSleepAllTime;
    }
    public int getSleepDuration() {
        return this.sleepDuration;
    }
    public void setSleepDuration(int sleepDuration) {
        this.sleepDuration = sleepDuration;
    }
    public int getFallAlseepAllTime() {
        return this.fallAlseepAllTime;
    }
    public void setFallAlseepAllTime(int fallAlseepAllTime) {
        this.fallAlseepAllTime = fallAlseepAllTime;
    }
    public int getBreathRateSlowAllTime() {
        return this.breathRateSlowAllTime;
    }
    public void setBreathRateSlowAllTime(int breathRateSlowAllTime) {
        this.breathRateSlowAllTime = breathRateSlowAllTime;
    }
    public int getBreathRateFastAllTime() {
        return this.breathRateFastAllTime;
    }
    public void setBreathRateFastAllTime(int breathRateFastAllTime) {
        this.breathRateFastAllTime = breathRateFastAllTime;
    }
    public int getHeartBeatRateSlowAllTime() {
        return this.heartBeatRateSlowAllTime;
    }
    public void setHeartBeatRateSlowAllTime(int heartBeatRateSlowAllTime) {
        this.heartBeatRateSlowAllTime = heartBeatRateSlowAllTime;
    }
    public int getHeartBeatRateFastAllTime() {
        return this.heartBeatRateFastAllTime;
    }
    public void setHeartBeatRateFastAllTime(int heartBeatRateFastAllTime) {
        this.heartBeatRateFastAllTime = heartBeatRateFastAllTime;
    }
    public int getMinBreathRate() {
        return this.minBreathRate;
    }
    public void setMinBreathRate(int minBreathRate) {
        this.minBreathRate = minBreathRate;
    }
    public int getMaxBreathRate() {
        return this.maxBreathRate;
    }
    public void setMaxBreathRate(int maxBreathRate) {
        this.maxBreathRate = maxBreathRate;
    }
    public int getMinHeartBeatRate() {
        return this.minHeartBeatRate;
    }
    public void setMinHeartBeatRate(int minHeartBeatRate) {
        this.minHeartBeatRate = minHeartBeatRate;
    }
    public int getMaxHeartBeatRate() {
        return this.maxHeartBeatRate;
    }
    public void setMaxHeartBeatRate(int maxHeartBeatRate) {
        this.maxHeartBeatRate = maxHeartBeatRate;
    }
    public int getLeaveBedAllTime() {
        return this.leaveBedAllTime;
    }
    public void setLeaveBedAllTime(int leaveBedAllTime) {
        this.leaveBedAllTime = leaveBedAllTime;
    }
    public int getBreathPauseAllTime() {
        return this.breathPauseAllTime;
    }
    public void setBreathPauseAllTime(int breathPauseAllTime) {
        this.breathPauseAllTime = breathPauseAllTime;
    }
    public int getHeartBeatPauseAllTime() {
        return this.heartBeatPauseAllTime;
    }
    public void setHeartBeatPauseAllTime(int heartBeatPauseAllTime) {
        this.heartBeatPauseAllTime = heartBeatPauseAllTime;
    }
    public int getBreathPauseTimes() {
        return this.breathPauseTimes;
    }
    public void setBreathPauseTimes(int breathPauseTimes) {
        this.breathPauseTimes = breathPauseTimes;
    }
    public int getHeartBeatPauseTimes() {
        return this.heartBeatPauseTimes;
    }
    public void setHeartBeatPauseTimes(int heartBeatPauseTimes) {
        this.heartBeatPauseTimes = heartBeatPauseTimes;
    }
    public int getBodyMovementTimes() {
        return this.bodyMovementTimes;
    }
    public void setBodyMovementTimes(int bodyMovementTimes) {
        this.bodyMovementTimes = bodyMovementTimes;
    }
    public int getTurnOverTimes() {
        return this.turnOverTimes;
    }
    public void setTurnOverTimes(int turnOverTimes) {
        this.turnOverTimes = turnOverTimes;
    }
    public int getLeaveBedTimes() {
        return this.leaveBedTimes;
    }
    public void setLeaveBedTimes(int leaveBedTimes) {
        this.leaveBedTimes = leaveBedTimes;
    }
    public int getAverageHeartBeatRate() {
        return this.averageHeartBeatRate;
    }
    public void setAverageHeartBeatRate(int averageHeartBeatRate) {
        this.averageHeartBeatRate = averageHeartBeatRate;
    }
    public int getAverageBreathRate() {
        return this.averageBreathRate;
    }
    public void setAverageBreathRate(int averageBreathRate) {
        this.averageBreathRate = averageBreathRate;
    }
    public String getHeartRateAry() {
        return this.heartRateAry;
    }
    public void setHeartRateAry(String heartRateAry) {
        this.heartRateAry = heartRateAry;
    }
    public String getBreathRateAry() {
        return this.breathRateAry;
    }
    public void setBreathRateAry(String breathRateAry) {
        this.breathRateAry = breathRateAry;
    }
    public String getTrunOverStatusAry() {
        return this.trunOverStatusAry;
    }
    public void setTrunOverStatusAry(String trunOverStatusAry) {
        this.trunOverStatusAry = trunOverStatusAry;
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
    public String getDateStr() {
        return this.dateStr;
    }
    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 681642476)
    public Sleep_Sleepace_DataModel(Long id, String deviceId, String userId,
            String dateStr, long timestamp, String reportId,
            String trunOverStatusAry, String breathRateAry, String heartRateAry,
            int averageBreathRate, int averageHeartBeatRate, int leaveBedTimes,
            int turnOverTimes, int bodyMovementTimes, int heartBeatPauseTimes,
            int breathPauseTimes, int heartBeatPauseAllTime,
            int breathPauseAllTime, int leaveBedAllTime, int maxHeartBeatRate,
            int minHeartBeatRate, int maxBreathRate, int minBreathRate,
            int heartBeatRateFastAllTime, int heartBeatRateSlowAllTime,
            int breathRateFastAllTime, int breathRateSlowAllTime,
            int fallAlseepAllTime, int sleepDuration, int deepSleepAllTime,
            int deepSleepPercent) {
        this.id = id;
        this.deviceId = deviceId;
        this.userId = userId;
        this.dateStr = dateStr;
        this.timestamp = timestamp;
        this.reportId = reportId;
        this.trunOverStatusAry = trunOverStatusAry;
        this.breathRateAry = breathRateAry;
        this.heartRateAry = heartRateAry;
        this.averageBreathRate = averageBreathRate;
        this.averageHeartBeatRate = averageHeartBeatRate;
        this.leaveBedTimes = leaveBedTimes;
        this.turnOverTimes = turnOverTimes;
        this.bodyMovementTimes = bodyMovementTimes;
        this.heartBeatPauseTimes = heartBeatPauseTimes;
        this.breathPauseTimes = breathPauseTimes;
        this.heartBeatPauseAllTime = heartBeatPauseAllTime;
        this.breathPauseAllTime = breathPauseAllTime;
        this.leaveBedAllTime = leaveBedAllTime;
        this.maxHeartBeatRate = maxHeartBeatRate;
        this.minHeartBeatRate = minHeartBeatRate;
        this.maxBreathRate = maxBreathRate;
        this.minBreathRate = minBreathRate;
        this.heartBeatRateFastAllTime = heartBeatRateFastAllTime;
        this.heartBeatRateSlowAllTime = heartBeatRateSlowAllTime;
        this.breathRateFastAllTime = breathRateFastAllTime;
        this.breathRateSlowAllTime = breathRateSlowAllTime;
        this.fallAlseepAllTime = fallAlseepAllTime;
        this.sleepDuration = sleepDuration;
        this.deepSleepAllTime = deepSleepAllTime;
        this.deepSleepPercent = deepSleepPercent;
    }
    @Generated(hash = 1734351888)
    public Sleep_Sleepace_DataModel() {
    }

}
