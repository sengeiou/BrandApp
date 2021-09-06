package com.isport.blelibrary.result.impl.watch;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class WatchGoalDistanceResult implements IResult, Serializable {
    @Override
    public String getType() {
        return DEVICE_GOAL_DISTANCE;
    }

    private int goalDistance;
    private String mac;

    public WatchGoalDistanceResult(int distance, String mac) {
        this.goalDistance = distance;
        this.mac = mac;
    }


    public int getGoalDistance() {
        return goalDistance;
    }

    public void setGoalDistance(int goalDistance) {
        this.goalDistance = goalDistance;
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
                "goalDistance=" + goalDistance +
                ", mac='" + mac + '\'' +
                '}';
    }
}
