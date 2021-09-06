package com.isport.blelibrary.deviceEntry.interfaces;

/**
 * @Author
 * @Date 2018/11/13
 * @Fuction
 */

 public interface ISleep {

     void setAutoCollection(boolean enable, int hour, int minute, int repeat);

     void setCollectionEnable(boolean enable);

     void getCollectionStatus();

     void setRealTimeEnable(boolean enable);

     void setOriginalEnable(boolean enable);

     void historyDownload(int startTime, int endTime, int sex);

     void getEnvironmentalData();

     void upgradeDevice1();

     void upgradeDevice2();

}
