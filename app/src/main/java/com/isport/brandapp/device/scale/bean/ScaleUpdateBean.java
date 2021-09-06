package com.isport.brandapp.device.scale.bean;

/**
 * @Author
 * @Date 2018/10/24
 * @Fuction 上传体脂称记录
 */

public class ScaleUpdateBean {
    private int userId;

    private int interfaceId;

    private int deviceType;

    private String mac;

    private String time;

    private FatSteelyardData fatSteelyardData;

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
    public void setFatSteelyardData(FatSteelyardData fatSteelyardData){
        this.fatSteelyardData = fatSteelyardData;
    }
    public FatSteelyardData getFatSteelyardData(){
        return this.fatSteelyardData;
    }
}
