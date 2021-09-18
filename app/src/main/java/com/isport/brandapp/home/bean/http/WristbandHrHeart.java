package com.isport.brandapp.home.bean.http;

import com.isport.brandapp.home.bean.BaseMainData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class WristbandHrHeart extends BaseMainData implements Serializable {


    private String strDate;

    private long buildTime;

    private long lastServerTime;

    private ArrayList<Integer> hrArry;


    private int maxHr, minHr, avgHr;

    private int timeInterval;


    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public long getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(long buildTime) {
        this.buildTime = buildTime;
    }

    public long getLastServerTime() {
        return lastServerTime;
    }

    public void setLastServerTime(long lastServerTime) {
        this.lastServerTime = lastServerTime;
    }

    public ArrayList<Integer> getHrArry() {
        return hrArry;
    }

    public void setHrArry(ArrayList<Integer> hrArry) {
        this.hrArry = hrArry;
    }

    public int getMaxHr() {
        return maxHr;
    }

    public void setMaxHr(int maxHr) {
        this.maxHr = maxHr;
    }

    public int getMinHr() {
        return minHr;
    }

    public void setMinHr(int minHr) {
        this.minHr = minHr;
    }

    public int getAvgHr() {
        return avgHr;
    }

    public void setAvgHr(int avgHr) {
        this.avgHr = avgHr;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

    @Override
    public String toString() {
        return "WristbandHrHeart{" +
                "strDate='" + strDate + '\'' +
                ", buildTime=" + buildTime +
                ", lastServerTime=" + lastServerTime +
                ", hrArry=" + hrArry +
                ", maxHr=" + maxHr +
                ", minHr=" + minHr +
                ", avgHr=" + avgHr +
                ", timeInterval=" + timeInterval +
                '}';
    }
}
