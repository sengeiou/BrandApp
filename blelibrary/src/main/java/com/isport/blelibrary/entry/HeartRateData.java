package com.isport.blelibrary.entry;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/11/15
 * @Fuction
 */

public class HeartRateData implements Serializable {

    private int heartRate;//
   // private int index;

    @Override
    public String toString() {
        return "HeartRateData{" +
                "heartRate=" + heartRate +
               // ", index=" + index +
                '}';
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }



    public HeartRateData(int heartRate) {

        this.heartRate = heartRate;
    }
}
