package com.isport.brandapp.Home.bean.db;

import com.isport.brandapp.Home.bean.BaseMainData;

/**
 * @Author
 * @Date 2019/1/10
 * @Fuction
 */

public class ScaleMainData extends BaseMainData {
    @Override
    public String toString() {
        return "ScaleMainData{" +
                "weight='" + weight + '\'' +
                ", lastSyncTime=" + lastSyncTime +
                ", compareWeight='" + compareWeight + '\'' +
                '}';
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public long getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(long lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public String getCompareWeight() {
        return compareWeight;
    }

    public void setCompareWeight(String compareWeight) {
        this.compareWeight = compareWeight;
    }

    private String weight;//称重kg
    private long lastSyncTime;//上次同步时间
    private String compareWeight;//和上次称重比较，kg
}
