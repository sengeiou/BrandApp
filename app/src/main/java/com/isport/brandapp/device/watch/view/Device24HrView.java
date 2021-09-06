package com.isport.brandapp.device.watch.view;

import com.isport.brandapp.Home.bean.http.WristbandHrHeart;
import com.isport.brandapp.device.bracelet.bean.StateBean;

import java.util.ArrayList;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface Device24HrView extends BaseView {

    public void success24HrSwitch(boolean isOpen);

    public void successState(StateBean stateBean);

}
