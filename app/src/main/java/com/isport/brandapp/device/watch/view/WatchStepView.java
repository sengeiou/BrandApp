package com.isport.brandapp.device.watch.view;

import com.isport.brandapp.home.bean.http.Wristbandstep;

import java.util.ArrayList;

import brandapp.isport.com.basicres.entry.WatchTargetBean;
import brandapp.isport.com.basicres.mvp.BaseView;

public interface WatchStepView extends BaseView {
    public void successLastSportsummary(Wristbandstep wristbandstep);
    public void successTargetStep(WatchTargetBean watchTargetBean);
    public void successMonthDate(ArrayList<String> strDates);

    public void succcessLastMontData(String avgStep,String avgDis,String sumGola,String sumFat);

}
