package com.isport.brandapp.device.bracelet.braceletModel;

import brandapp.isport.com.basicres.entry.WatchTargetBean;

public interface IDeviceGoalModel {
    void saveDeviceGoalValue(String userId,String deviceId, int value, int type);

    WatchTargetBean getDeviceGoalValue(String userId, String devcieId);
}
