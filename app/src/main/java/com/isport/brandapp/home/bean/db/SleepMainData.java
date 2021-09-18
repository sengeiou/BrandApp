package com.isport.brandapp.home.bean.db;

import com.isport.brandapp.home.bean.BaseMainData;

/**
 * @Author
 * @Date 2019/1/10
 * @Fuction
 */

public class SleepMainData extends BaseMainData {

    private int minute;//睡眠的总分钟
    private long lastSyncTime;//上次同步时间
    private float compareSleepTime;//和上次睡眠时长比较，分钟
    private String lastSyncDate;

    @Override
    public String toString() {
        return "SleepMainData{" +
                "minute=" + minute +
                ", lastSyncTime=" + lastSyncTime +
                ", compareSleepTime=" + compareSleepTime +
                ", lastSyncDate='" + lastSyncDate + '\'' +
                '}';
    }

    public String getLastSyncDate() {
        return lastSyncDate;
    }

    public void setLastSyncDate(String lastSyncDate) {
        this.lastSyncDate = lastSyncDate;
    }

    public SleepMainData() {
    }

    public SleepMainData(int minute, long lastSyncTime, float compareSleepTime) {
        this.minute = minute;
        this.lastSyncTime = lastSyncTime;
        this.compareSleepTime = compareSleepTime;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public long getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(long lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public float getCompareSleepTime() {
        return compareSleepTime;
    }

    public void setCompareSleepTime(float compareSleepTime) {
        this.compareSleepTime = compareSleepTime;
    }
}
