package com.isport.brandapp.device.sleep.view;

import com.isport.brandapp.device.sleep.bean.SleepHistoryList;

import java.util.ArrayList;

import brandapp.isport.com.basicres.mvp.BaseView;

/**
 * @Author
 * @Date 2018/10/29
 * @Fuction
 */

public interface SleepHistoryView extends BaseView {
    void successRefresh(ArrayList<SleepHistoryList> historyBean,boolean islastdata);
    void successLoadMore(ArrayList<SleepHistoryList> historyBean,boolean islastdata);
}
