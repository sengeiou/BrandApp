package com.isport.blelibrary.result.impl.watch;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class WatchTempSubResult implements IResult, Serializable {
    @Override
    public String getType() {
        return DEVICE_TEMP_SUB;
    }

    private int value;
    private String mac;

    public WatchTempSubResult(int value, String mac) {
        this.mac = mac;
        this.value = value;
    }


    public int getTempValue() {
        return value;
    }

    public void setWatchFaceIndex(int watchFaceIndex) {
        this.value = watchFaceIndex;
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
                "value=" + value +
                ", mac='" + mac + '\'' +
                '}';
    }
}
