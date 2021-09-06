package com.isport.blelibrary.result.impl.watch;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class DeviceTakePhotoResult implements IResult, Serializable {
    @Override
    public String getType() {
        return DEVICE_TAKE_PHOTO;
    }

    private String mac;

    public DeviceTakePhotoResult( String mac) {
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
        return "DeviceTakePhotoResult{" +
                ", mac='" + mac + '\'' +
                '}';
    }
}
