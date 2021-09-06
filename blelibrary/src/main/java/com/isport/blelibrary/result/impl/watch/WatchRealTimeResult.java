package com.isport.blelibrary.result.impl.watch;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class WatchRealTimeResult implements IResult,Serializable {
    @Override
    public String getType() {
        return WATCH_REALTIME;
    }

    private int stepNum;
    private float stepKm;
    private int cal;
    private int heartRate;
    private String mac;

    public WatchRealTimeResult(int stepNum, float stepKm, int cal, int heartRate, String mac) {
        this.stepNum = stepNum;
        this.stepKm = stepKm;
        this.cal = cal;
        this.heartRate = heartRate;
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "WatchRealTimeResult{" +
                "stepNum=" + stepNum +
                ", stepKm=" + stepKm +
                ", cal=" + cal +
                ", heartRate=" + heartRate +
                ", mac='" + mac + '\'' +
                '}';
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

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
