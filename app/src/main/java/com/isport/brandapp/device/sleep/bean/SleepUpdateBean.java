package com.isport.brandapp.device.sleep.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author
 * @Date 2018/10/29
 * @Fuction
 */

public class SleepUpdateBean implements Serializable{

    private int userId;

    private int interfaceId;

    private int deviceType;

    private String mac;

    private String time;

    private List<SleepUpdateData> data ;

    public void setUserId(int userId){
        this.userId = userId;
    }
    public int getUserId(){
        return this.userId;
    }
    public void setInterfaceId(int interfaceId){
        this.interfaceId = interfaceId;
    }
    public int getInterfaceId(){
        return this.interfaceId;
    }
    public void setDeviceType(int deviceType){
        this.deviceType = deviceType;
    }
    public int getDeviceType(){
        return this.deviceType;
    }
    public void setMac(String mac){
        this.mac = mac;
    }
    public String getMac(){
        return this.mac;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getTime(){
        return this.time;
    }
    public void setData(List<SleepUpdateData> data){
        this.data = data;
    }
    public List<SleepUpdateData> getData(){
        return this.data;
    }
}
