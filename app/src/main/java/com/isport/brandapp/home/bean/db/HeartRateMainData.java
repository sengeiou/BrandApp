package com.isport.brandapp.home.bean.db;

import com.isport.brandapp.home.bean.BaseMainData;

/**
 * @Author
 * @Date 2019/1/10
 * @Fuction
 */

public class HeartRateMainData extends BaseMainData {

    private int heartRate;//平均心率 次/分
    private long lastSyncTime;//上次同步时间
    private int compareHeartRate;//和上次平均心率比较,次/分
    private String dateStr;//当天的日期

    @Override
    public String toString() {
        return "HeartRateMainData{" +
                "heartRate=" + heartRate +
                ", lastSyncTime=" + lastSyncTime +
                ", compareHeartRate=" + compareHeartRate +
                ", dateStr='" + dateStr + '\'' +
                '}';
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public long getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(long lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public int getCompareHeartRate() {
        return compareHeartRate;
    }

    public void setCompareHeartRate(int compareHeartRate) {
        this.compareHeartRate = compareHeartRate;
    }
}
