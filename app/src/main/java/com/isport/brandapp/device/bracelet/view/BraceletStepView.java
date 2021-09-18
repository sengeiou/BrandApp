package com.isport.brandapp.device.bracelet.view;

import com.isport.brandapp.home.bean.http.Wristbandstep;

import java.util.ArrayList;
import java.util.List;

import brandapp.isport.com.basicres.entry.WatchTargetBean;
import brandapp.isport.com.basicres.mvp.BaseView;

public interface BraceletStepView extends BaseView {
    public void successWeekBraceletSportDetail(List<Wristbandstep> wristbandsteps);

    public void successLastSportsummary(Wristbandstep wristbandstep);

    public void successTargetStep(WatchTargetBean watchTargetBean);

    public void successMonthDate(ArrayList<String> strDates);

    public void succcessLastMontData(String avgStep, String avgDis, String sumGola, String sumFat);

}
