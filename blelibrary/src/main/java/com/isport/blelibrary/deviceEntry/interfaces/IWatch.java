package com.isport.blelibrary.deviceEntry.interfaces;

/**
 * @Author
 * @Date 2018/11/13
 * @Fuction
 */

public interface IWatch {

     void getSportData();

     void sendTime();

     void sendUserInfo(int heigh, int weight, int age, int sex);

     void sendDeviceInfo(int target, int screenTime, boolean enable);

     void sendScreenTime(int screenTime);

     void sendHandScreen(boolean enable);

     void sendTarget(int target);
}
