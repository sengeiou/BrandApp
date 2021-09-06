package com.isport.blelibrary.result.impl.watch;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class WatchGOALSTEPResult implements IResult, Serializable {
    @Override
    public String getType() {
        return DEVICE_GOAL_STEP;
    }

    private int goalStep;
    private String mac;

    public WatchGOALSTEPResult(int heartRate, String mac) {
        this.goalStep = heartRate;
        this.mac = mac;
    }


    public int getGoalStep() {
        return goalStep;
    }

    public void setHeartRate(int heartRate) {
        this.goalStep = heartRate;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "WatchGOALSTEPResult{" +
                "goalStep=" + goalStep +
                ", mac='" + mac + '\'' +
                '}';
    }
}
