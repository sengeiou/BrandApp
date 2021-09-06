package com.isport.blelibrary.result.impl.watch;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class WatchGoalCalorieResult implements IResult, Serializable {
    @Override
    public String getType() {
        return DEVICE_GOAL_CALORIE;
    }

    private int goalCalorie;
    private String mac;

    public WatchGoalCalorieResult(int calorie, String mac) {
        this.goalCalorie = calorie;
        this.mac = mac;
    }


    public int getGoalCalorie() {
        return goalCalorie;
    }

    public void setGoalCalorie(int calorie) {
        this.goalCalorie = calorie;
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
                "goalCalorie=" + goalCalorie +
                ", mac='" + mac + '\'' +
                '}';
    }
}
