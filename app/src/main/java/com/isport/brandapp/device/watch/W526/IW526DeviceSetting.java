package com.isport.brandapp.device.watch.W526;

import com.isport.blelibrary.db.table.w526.Device_BacklightTimeAndScreenLuminanceModel;

public interface IW526DeviceSetting {

    void saveBackLightTime(String deviceId, String userId, int value);

    void saveScrenLeve(String deviceId, String userId, int value);


    Device_BacklightTimeAndScreenLuminanceModel getMode(String deviceId, String userId);

}
