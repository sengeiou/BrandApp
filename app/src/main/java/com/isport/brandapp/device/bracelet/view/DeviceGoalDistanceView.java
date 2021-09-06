package com.isport.brandapp.device.bracelet.view;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface DeviceGoalDistanceView extends BaseView {

    void successGetGoalDistance(int distance);

    void successSaveGoalDistance(int distance);

}
