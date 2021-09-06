package com.isport.brandapp.device.bracelet.view;


import com.isport.blelibrary.db.table.DeviceInformationTable;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_DeviceInfoModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_DisplayModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_WearModel;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface WearView extends BaseView {

    public void successWearItem(Bracelet_W311_WearModel bracelet_w311_wearModel);

    public void successWearItem();

    public void getDeviceInfo(DeviceInformationTable deviceInfoModel);
    public void getDeviceInfo(Bracelet_W311_DeviceInfoModel deviceInfoModel);

}
