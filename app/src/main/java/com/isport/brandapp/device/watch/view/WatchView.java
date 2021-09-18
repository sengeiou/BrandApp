package com.isport.brandapp.device.watch.view;

import android.view.View;

import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SedentaryModel;
import com.isport.brandapp.home.bean.http.WatchSleepDayData;
import com.isport.brandapp.bean.DeviceBean;

import brandapp.isport.com.basicres.mvp.BaseView;

/**
 * @Author flyly
 * @Date 2018/11/1
 * @Fuction
 */

public interface WatchView extends BaseView {
    void dataSetSuccess(View view, String select, String data);

    void onUnBindSuccess();

    void successDayDate(WatchSleepDayData watchSleepDayData);

    void updateWatchHistoryDataSuccess(DeviceBean deviceBean);

    void updateFail();

    void seccessGetDeviceSedentaryReminder(Watch_W516_SedentaryModel watch_w516_sedentaryModel);
}
