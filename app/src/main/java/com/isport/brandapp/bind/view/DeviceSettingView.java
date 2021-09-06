package com.isport.brandapp.bind.view;

import com.isport.brandapp.bean.DeviceBean;

import brandapp.isport.com.basicres.mvp.BaseView;

/**
 * @Author
 * @Date 2018/10/17
 * @Fuction
 */

public interface DeviceSettingView extends BaseView {
    void onUnBindSuccess();
    void dataSetSuccess(String select, String data);
    void getClockTimeSuccess(String clockTime);
    void setClockTimeSuccess();
    void updateWatchDataSuccess(DeviceBean deviceBean);
    void updateSleepDataSuccess(DeviceBean deviceBean);
}
