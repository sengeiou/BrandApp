package com.isport.brandapp.device.bracelet.braceletModel;

import com.isport.brandapp.AppConfiguration;

import brandapp.isport.com.basicres.action.BaseAction;
import brandapp.isport.com.basicres.action.WatchTargetBeanAction;
import brandapp.isport.com.basicres.commonutil.ThreadPoolUtils;
import brandapp.isport.com.basicres.entry.WatchTargetBean;
import brandapp.isport.com.basicres.gen.WatchTargetBeanDao;

public class DeviceGoalModelImp implements IDeviceGoalModel {
    @Override
    public void saveDeviceGoalValue(String userId, String deviceId, int value, int type) {

        ThreadPoolUtils.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                WatchTargetBeanDao watchTargetBeanDao = BaseAction.getWatchTargetBeanDao();
                WatchTargetBean watchTargetBean = getDeviceGoalValue(userId, deviceId);
                if (watchTargetBean == null) {
                    watchTargetBean = new WatchTargetBean();
                    watchTargetBean.setDeviceId(deviceId);
                    watchTargetBean.setUserId(userId);
                    watchTargetBean.setTargetStep(6000);
                    watchTargetBean.setTargetDistance(1000);
                    watchTargetBean.setTargetCalorie(10);
                }

                if (type == 0) {
                    watchTargetBean.setTargetStep(value);
                } else if (type == 1) {
                    watchTargetBean.setTargetDistance(value);
                } else if (type == 2) {
                    watchTargetBean.setTargetCalorie(value);
                }
                watchTargetBeanDao.insertOrReplace(watchTargetBean);
                com.isport.blelibrary.utils.Logger.myLog("saveDeviceGoalValue:" + watchTargetBean.toString());
            }
        });
    }

    @Override
    public WatchTargetBean getDeviceGoalValue(String userId, String devcieId) {
        WatchTargetBean watchTargetBean = WatchTargetBeanAction.getWatchTargetBean(userId, AppConfiguration.braceletID);
        return watchTargetBean;
    }
}
