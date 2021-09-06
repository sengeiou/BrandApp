package com.isport.brandapp.device.bracelet.view;

import com.isport.blelibrary.db.table.w811w814.FaceWatchMode;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface DeviceGoalStepView extends BaseView {

    void successGetGoalStep(int Step);

    void successSaveGoalStep(int step);

}
