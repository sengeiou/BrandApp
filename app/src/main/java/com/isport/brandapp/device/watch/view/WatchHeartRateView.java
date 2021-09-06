package com.isport.brandapp.device.watch.view;

import com.isport.brandapp.Home.bean.http.WristbandHrHeart;

import java.util.ArrayList;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface WatchHeartRateView extends BaseView {

    public void successMonthStrDate(ArrayList<String> strDate);

    public void successDayHrDate(WristbandHrHeart wristbandHrHeart);
}
