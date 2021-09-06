package com.isport.brandapp.bind.view;

import com.isport.brandapp.bean.DeviceBean;

import brandapp.isport.com.basicres.mvp.BaseView;

/**
 * @Author
 * @Date 2018/10/15
 * @Fuction
 */

public interface BindBaseView extends BaseView {
    void onUnBindSuccess();
    void updateWatchDataSuccess(DeviceBean deviceBean);
    void updateSleepDataSuccess(DeviceBean deviceBean);
    void updateWatchHistoryDataSuccess(DeviceBean deviceBean);
    void updateFail();

}
