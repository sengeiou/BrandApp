package com.isport.brandapp.device.watch.view;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface DeviceBackLightTimeAndScrenLeveView extends BaseView {

    void successGetBackLightScreen(int valueBackLightTime,int screenLeve);


    void successSaveValue();

}
