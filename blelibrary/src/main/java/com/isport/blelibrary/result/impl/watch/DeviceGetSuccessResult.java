package com.isport.blelibrary.result.impl.watch;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class DeviceGetSuccessResult implements IResult, Serializable {
    @Override
    public String getType() {
        return DEVICE_GET_SETTING;
    }


    public DeviceGetSuccessResult(String mac,int dataType,int isSuccess){
        this.mac=mac;
        this.dataType=dataType;
        this.isSuccess=isSuccess;

    }

    private String mac;
    private int dataType;
    private int isSuccess;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(int isSuccess) {
        this.isSuccess = isSuccess;
    }

    @Override
    public String toString() {
        return "DeviceGetSuccessResult{" +
                "mac='" + mac + '\'' +
                ", dataType=" + dataType +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
