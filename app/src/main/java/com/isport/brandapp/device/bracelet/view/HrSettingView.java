package com.isport.brandapp.device.bracelet.view;


import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_24H_hr_SettingModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_WearModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_w311_hrModel;
import com.isport.brandapp.device.bracelet.bean.StateBean;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface HrSettingView extends BaseView {

    public void successHrSettingItem(Bracelet_w311_hrModel bracelet_w311_wearModel);

    public void successSave(boolean isSave);

    public void success24HrSettingState(Bracelet_W311_24H_hr_SettingModel bracelet_w311_24H_hr_settingModel);


}
