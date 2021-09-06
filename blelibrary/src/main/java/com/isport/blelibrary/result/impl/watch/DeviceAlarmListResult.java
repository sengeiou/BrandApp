package com.isport.blelibrary.result.impl.watch;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class DeviceAlarmListResult implements IResult, Serializable {
    @Override
    public String getType() {
        return DEVICE_ALARM_LIST;
    }

    private String mac;
    private ArrayList list;

    public DeviceAlarmListResult(String mac) {
        this.mac = mac;
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
                ", mac='" + mac + '\'' +
                '}';
    }
}
