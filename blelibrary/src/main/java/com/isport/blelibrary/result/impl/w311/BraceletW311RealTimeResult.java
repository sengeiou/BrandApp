package com.isport.blelibrary.result.impl.w311;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class BraceletW311RealTimeResult implements IResult, Serializable {
    @Override
    public String getType() {
        return BRACELET_W311_REALTIME;
    }

    private int stepNum;
    private float stepKm;
    private int cal;
    private String mac;

    public BraceletW311RealTimeResult(int stepNum, float stepKm, int cal,String mac) {
        this.stepNum = stepNum;
        this.stepKm = stepKm;
        this.cal = cal;
        this.mac=mac;
    }

    @Override
    public String toString() {
        return "BraceletW311RealTimeResult{" +
                "stepNum=" + stepNum +
                ", stepKm=" + stepKm +
                ", cal=" + cal +
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

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
