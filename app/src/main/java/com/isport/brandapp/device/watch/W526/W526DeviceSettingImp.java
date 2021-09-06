package com.isport.brandapp.device.watch.W526;

import com.isport.blelibrary.db.action.DeviceSetting.DeviceSettingAction;
import com.isport.blelibrary.db.table.w526.Device_BacklightTimeAndScreenLuminanceModel;
import com.isport.blelibrary.utils.Logger;

public class W526DeviceSettingImp implements IW526DeviceSetting {


    DeviceSettingAction deviceSettingAction;


    public W526DeviceSettingImp() {
        deviceSettingAction = new DeviceSettingAction();
    }


    @Override
    public void saveBackLightTime(String deviceId, String userId, int value) {
        deviceSettingAction.saveOrUpdateBacklightTime(deviceId, userId, value);
    }

    @Override
    public void saveScrenLeve(String deviceId, String userId, int value) {
        deviceSettingAction.saveOrUpdateScreenLeve(deviceId, userId, value);
    }

    @Override
    public Device_BacklightTimeAndScreenLuminanceModel getMode(String deviceId, String userId) {
        Device_BacklightTimeAndScreenLuminanceModel model = deviceSettingAction.findBacklighttimeAndScreenLeve(deviceId, userId);
        Logger.myLog("getMode:" + model);
        return model;
    }
}
