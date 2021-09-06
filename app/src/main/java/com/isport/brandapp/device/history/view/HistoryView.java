package com.isport.brandapp.device.history.view;

import com.isport.brandapp.device.band.bean.BandDayBean;
import com.isport.brandapp.device.sleep.bean.SleepHistoryList;

import java.util.ArrayList;

import brandapp.isport.com.basicres.mvp.BaseView;

/**
 * @Author flyly
 * @Date 2018/11/1
 * @Fuction
 */

public interface HistoryView extends BaseView {
    void successSportRefresh(ArrayList<BandDayBean> historyBean);

    void successSportLoadMore(ArrayList<BandDayBean> historyBean);

}
