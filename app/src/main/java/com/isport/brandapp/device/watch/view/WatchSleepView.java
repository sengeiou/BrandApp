package com.isport.brandapp.device.watch.view;

import com.isport.brandapp.Home.bean.http.WatchSleepDayData;

import java.util.ArrayList;

import brandapp.isport.com.basicres.mvp.BaseView;

/**
 * @创建者 bear
 * @创建时间 2019/3/9 16:22
 * @描述
 */
public interface WatchSleepView extends BaseView {
    public void successMonthDate(ArrayList<String> strDates);
    public void successDayDate(WatchSleepDayData watchSleepDayData);
}
