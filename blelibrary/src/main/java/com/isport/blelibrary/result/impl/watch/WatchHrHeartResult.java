package com.isport.blelibrary.result.impl.watch;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class WatchHrHeartResult implements IResult,Serializable {
    @Override
    public String getType() {
        return WATCH_REALTIME_HR;
    }

    private int heartRate;
    private String mac;

    public WatchHrHeartResult( int heartRate, String mac) {
        this.heartRate = heartRate;
        this.mac = mac;
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

    @Override
    public String toString() {
        return "WatchHrHeartResult{" +
                "heartRate=" + heartRate +
                ", mac='" + mac + '\'' +
                '}';
    }
}
