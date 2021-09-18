package com.isport.brandapp.home.bean.db;

import java.io.Serializable;

/**
 * @Author
 * @Date 2019/1/15
 * @Fuction
 */

public class WatchSportMainData implements Serializable{
    private String step;//当前步数/步
    private long lastSyncTime;//上次同步时间,暂时没用
    private String compareStep;//和上次称重比较，kg
    private String distance;//距离/km
    private String cal;//卡路里/kcal
    private String lastStep;
    private String dateStr;//当前数据的日期

    @Override
    public String toString() {
        return "WatchSportMainData{" +
                "step='" + step + '\'' +
                ", lastSyncTime=" + lastSyncTime +
                ", compareStep='" + compareStep + '\'' +
                ", distance='" + distance + '\'' +
                ", cal='" + cal + '\'' +
                ", lastStep='" + lastStep + '\'' +
                ", dateStr='" + dateStr + '\'' +
                '}';
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public long getLastSyncTime() {
        return lastSyncTime;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public void setLastSyncTime(long lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public String getCompareStep() {
        return compareStep;
    }

    public void setCompareStep(String compareStep) {
        this.compareStep = compareStep;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCal() {
        return cal;
    }

    public void setCal(String cal) {
        this.cal = cal;
    }

    public String getLastStep() {
        return lastStep;
    }

    public void setLastStep(String lastStep) {
        this.lastStep = lastStep;
    }
}
