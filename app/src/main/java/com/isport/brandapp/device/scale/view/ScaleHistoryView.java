package com.isport.brandapp.device.scale.view;

import com.isport.brandapp.device.scale.bean.ScaleHistoryBean;
import com.isport.brandapp.device.scale.bean.ScaleHistroyNList;

import java.util.ArrayList;
import java.util.List;

import brandapp.isport.com.basicres.mvp.BaseView;

/**
 * @Author
 * @Date 2018/10/23
 * @Fuction
 */

public interface ScaleHistoryView extends BaseView {

    void successRefresh(ArrayList<ScaleHistoryBean> historyBean, boolean isLastData, String lastMonthStr, long nextTimeTamp);

    void successMainScale(ScaleHistroyNList historyBean);
    void successMothList(List<Long> list);

    void successLoadMone(ArrayList<ScaleHistoryBean> historyBean, boolean isLastData, String lastMonthStr, long nextTimeTamp);
}
