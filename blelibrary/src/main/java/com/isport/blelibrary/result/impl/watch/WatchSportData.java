package com.isport.blelibrary.result.impl.watch;

/**
 * @Author
 * @Date 2018/11/6
 * @Fuction
 */

public class WatchSportData {
    private int stepNum;
    private float stepKm;
    private int cal;
    private String dateStr;
    private int sportTime;
    private int maxHR;
    private int minHR;
    private String mac;

    @Override
    public String toString() {
        return "WatchSportData{" +
                "stepNum=" + stepNum +
                ", stepKm=" + stepKm +
                ", cal=" + cal +
                ", dateStr='" + dateStr + '\'' +
                ", sportTime=" + sportTime +
                ", maxHR=" + maxHR +
                ", minHR=" + minHR +
                ", mac='" + mac + '\'' +
                '}';
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public WatchSportData(int stepNum, float stepKm, int cal, String dateStr, int sportTime, int maxHR, int minHR,
                          String mac) {

        this.stepNum = stepNum;
        this.stepKm = stepKm;
        this.cal = cal;
        this.dateStr = dateStr;
        this.sportTime = sportTime;
        this.maxHR = maxHR;
        this.minHR = minHR;
        this.mac = mac;
    }

    public int getStepNum() {
        return stepNum;
    }

    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }

    public float getStepKm() {
        return stepKm;
    }

    public void setStepKm(float stepKm) {
        this.stepKm = stepKm;
    }

    public int getCal() {
        return cal;
    }

    public void setCal(int cal) {
        this.cal = cal;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public int getSportTime() {
        return sportTime;
    }

    public void setSportTime(int sportTime) {
        this.sportTime = sportTime;
    }

    public int getMaxHR() {
        return maxHR;
    }

    public void setMaxHR(int maxHR) {
        this.maxHR = maxHR;
    }

    public int getMinHR() {
        return minHR;
    }

    public void setMinHR(int minHR) {
        this.minHR = minHR;
    }

}
