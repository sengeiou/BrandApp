package com.isport.blelibrary.result.impl.watch;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class DeviceMessureDataResult implements IResult, Serializable {



    @Override
    public String getType() {
        return DEVICE_MESSURE;
    }

    private int messureType;
    private String mac;

    public DeviceMessureDataResult(int messureType, String mac) {
        this.messureType = messureType;
        this.mac = mac;
    }


    public int getMessureType() {
        return messureType;
    }

    public void setMessureType(int messureType) {
        this.messureType = messureType;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "DeviceMessureDataResult{" +
                "messureType=" + messureType +
                ", mac='" + mac + '\'' +
                '}';
    }
}
