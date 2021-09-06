package com.isport.blelibrary.result.impl.watch;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class WatchFACEResult implements IResult, Serializable {
    @Override
    public String getType() {
        return DEVICE_WATCH_FACE;
    }

    private int watchFaceIndex;
    private String mac;

    public WatchFACEResult(int watchFaceIndex, String mac) {
        this.mac = mac;
        this.watchFaceIndex = watchFaceIndex;
    }


    public int getWatchFaceIndex() {
        return watchFaceIndex;
    }

    public void setWatchFaceIndex(int watchFaceIndex) {
        this.watchFaceIndex = watchFaceIndex;
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
                "watchFaceIndex=" + watchFaceIndex +
                ", mac='" + mac + '\'' +
                '}';
    }
}
